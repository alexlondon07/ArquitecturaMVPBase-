package io.github.alexlondon07.arquitecturamvpbase.views.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductPresenter;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.DetailsActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IProductView;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ProductCreateActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ProductUpdateActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.adapter.ProductAdapter;

/**
 * Created by alexlondon07 on 10/14/17.
 */

public class ProductFragment extends BaseFragment<ProductPresenter> implements IProductView {

    private ListView productList;
    private ProductAdapter productAdapter;
    private FloatingActionButton btnNewProduct;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product, container, false);

        getBaseActivity().createProgresDialog();
        showProgress(R.string.loading_message);
        setPresenter(new ProductPresenter());
        getPresenter().inject(this, getValidateInternet());

        productList = view.findViewById(R.id.product_list_view);
        productList.setNestedScrollingEnabled(true);

        getPresenter().getProductsPresenter();

        btnNewProduct =  view.findViewById(R.id.activity_product_fab_launch_createproduct);
        btnNewProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductCreateActivity.class);
                startActivity(intent);
            }
        });

        swipeRefresh = view.findViewById(R.id.activity_product_swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                getPresenter().getProductsPresenter();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //swipeRefresh.setRefreshing(true);
        getPresenter().getProductsPresenter();
    }

    @Override
    public void showProductList(final ArrayList<Product> productArrayList) {
        getBaseActivity().hidePorgress();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                callAdapter(productArrayList);
            }
        });
    }

    @Override
    public void showAlertDialogInternet(final int title, int message) {
        showAlertDialog(title, message);
    }

    @Override
    public void showAlertError(int title, int message) {
        showAlertDialog(title, message);
    }

    private void showAlertDialog(final int title, final int message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                getBaseActivity().getShowAlertDialog().showAlertDialog(title, message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().getProductList();
                    }
                }, R.string.option_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });
            }
        });
    }

    private void callAdapter(final ArrayList<Product> productArrayList) {
        productAdapter = new ProductAdapter(getActivity(), R.id.product_list_view, productArrayList);
        productList.setAdapter(productAdapter);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT, productArrayList.get(position));
                startActivity(intent);
            }
        });

        //Method go to ProductUpdateActivity and Update Product
        productList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProductUpdateActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT,productArrayList.get(position));
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public void showAlertError(int error, String error2) {
    }
}
