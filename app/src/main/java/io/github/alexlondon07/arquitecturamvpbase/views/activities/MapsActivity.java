package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.model.Location;
import io.github.alexlondon07.arquitecturamvpbase.model.PhoneList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private  String TAG = "MapsActivity.class";

    private TextView name;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(checkPlayServices()){
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            customer = (Customer) getIntent().getSerializableExtra(Constants.ITEM_CUSTOMER);
        }else{
            //TODO
        }
    }

    //Validate google services
    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS){
            if(googleAPI.isUserResolvableError(result)){
                googleAPI.getErrorDialog(this, result, 9000).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        createMarkers();
        changeStateControls();

    }

    private void changeStateControls() {
        UiSettings uisettings = mMap.getUiSettings();
        uisettings.setZoomControlsEnabled(true);
    }

    private void createMarkers() {
        ArrayList<LatLng> points = new ArrayList<>();
        for (PhoneList phoneList :customer.getPhoneList()){
            Location location  = phoneList.getLocation();
            LatLng point = new LatLng(location.getCoordinates()[0], location.getCoordinates()[1]);
            mMap.addMarker(new MarkerOptions().position(point).title(location.getType()).icon(bitmapDescriptorFromVector(this, R.drawable.ic_location_on_black_24dp)));
            points.add(point);
        }
        calculateRouteCustomer(points);
    }

    RoutingListener routingListener = new RoutingListener() {
        @Override
        public void onRoutingFailure(RouteException e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onRoutingStart() {
            Log.i(TAG, "onRoutingStart");
        }

        @Override
        public void onRoutingSuccess(ArrayList<Route> routes, int shortestRouteIndex) {

            ArrayList polyLines = new ArrayList<>();
            for(int i = 0; i < routes.size(); i++){
                PolylineOptions polyLineOptions = new PolylineOptions();
                polyLineOptions.color(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                polyLineOptions.width(10);
                polyLineOptions.addAll(routes.get(i).getPoints());

                Polyline polyLine = mMap.addPolyline(polyLineOptions);
                polyLines.add(polyLine);

                int distance = routes.get(i).getDistanceValue();
                int duration = routes.get(i).getDurationValue();

                //Toast.makeText(MapsActivity.this, " Distance: " +distance+  " and duration " + duration, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onRoutingCancelled() {
            Log.e(TAG, "onRoutingCancelled");
        }
    };

    private void calculateRouteCustomer(ArrayList<LatLng> points) {
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(points)
                .key(getString(R.string.google_maps_key))
                .optimize(true)
                .withListener(routingListener)
                .build();
        routing.execute();

        centerRoutes(points);
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorId);
        vectorDrawable.setBounds(0,0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void centerRoutes(ArrayList<LatLng> points){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(LatLng latLng: points){
            builder.include(latLng);
        }

        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mMap.animateCamera(cameraUpdate);
    }

}
