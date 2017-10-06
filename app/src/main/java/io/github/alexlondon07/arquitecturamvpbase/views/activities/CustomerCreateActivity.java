package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.model.Location;
import io.github.alexlondon07.arquitecturamvpbase.model.PhoneList;
import io.github.alexlondon07.arquitecturamvpbase.presenter.CustomerCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.CustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;

public class CustomerCreateActivity extends BaseActivity<CustomerCreatePresenter> implements ICreateCustomerView,TextWatcher {

    private TextInputEditText name, surname, phoneNumber, coordinateX, coordinateY, type;
    private Button btnNewCustomer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create);
        setPresenter(new CustomerCreatePresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());
        createProgresDialog();
        initialize();
        loadEvents();
    }


    private void initialize() {
        btnNewCustomer = (Button) findViewById(R.id.activity_create_customer_button_create);
        disableButton(btnNewCustomer);

        name = (TextInputEditText) findViewById(R.id.activity_create_customer_name);
        name.addTextChangedListener(this);

        surname = (TextInputEditText) findViewById(R.id.activity_create_customer_surname);
        surname.addTextChangedListener(this);

        phoneNumber = (TextInputEditText) findViewById(R.id.activity_create_customer_phone_number);
        phoneNumber.addTextChangedListener(this);

        coordinateX = (TextInputEditText) findViewById(R.id.activity_create_customer_coordinates_location_x);
        coordinateX.addTextChangedListener(this);

        coordinateY = (TextInputEditText) findViewById(R.id.activity_create_customer_coordinates_location_y);
        coordinateY.addTextChangedListener(this);

        createProgresDialog();
    }


    public void validateFields(){
        if( name.getText().toString().isEmpty() ||
                surname.getText().toString().isEmpty() ||
                phoneNumber.getText().toString().isEmpty()  ||
                coordinateX.getText().toString().isEmpty()  ||
                coordinateY.getText().toString().isEmpty()){
            disableButton(btnNewCustomer);
        }else{
            enableButton(btnNewCustomer);
        }
    }
    public void enableButton(Button button){
        button.setEnabled(true);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));

    }

    public void disableButton(Button button){
        button.setEnabled(false);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
    }

    private void loadEvents() {
        btnNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataItem();
            }
        });
    }

    private void setDataItem() {
        //Information Customer
        Customer customer = new Customer();

        customer.setName(name.getText().toString());
        customer.setSurname(surname.getText().toString());

        ArrayList<PhoneList> phoneLists = new ArrayList<PhoneList>();

        //Information the PhoneList
        PhoneList phoneList = new PhoneList();
        phoneList.setNumber(phoneNumber.getText().toString());

        //Information the Location
        Location location = new Location();
        location.setType("Point");
        Double coordinates [] ={
                Double.valueOf(coordinateX.getText().toString()),
                Double.valueOf(coordinateY.getText().toString())
        };
        location.setCoordinates(coordinates);
        phoneList.setLocation(location);

        phoneLists.add(phoneList);
        customer.setPhoneList(phoneLists);

        getPresenter().createNewCustomerPresenter(customer);
    }

    private void closeActivity() {
        CustomerCreateActivity.this.finish();
    }

    @Override
    public void responseCreateCustomer(final boolean isCreated) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hidePorgress();
                if(isCreated)
                    showToast(R.string.okCreate);
                else
                    showToast(R.string.errorCreate);

                closeActivity();
            }
        });
    }

    @Override
    public void showAlertDialog(final int message) {
        getShowAlertDialog().showAlertDialog(R.string.error, message, false, R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, R.string.option_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeActivity();
            }
        });
    }

    @Override
    public void showToast(final int msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CustomerCreateActivity.this, msg, Toast.LENGTH_SHORT).show();
                closeActivity();
            }
        });
    }

    @Override
    public void showAlertError(int title, int message) {

    }

    @Override
    public void showAlertError(int title, String message) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        validateFields();
    }
}
