package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.presenter.CustomerPresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.CustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.adapter.CustomerAdapter;

public class CustomerActivity extends BaseActivity<CustomerPresenter> implements ICustomerView {

    private ListView customerList;
    private CustomerAdapter customerAdapter;
    private FloatingActionButton btnNewCustomer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setPresenter(new CustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());
        createProgresDialog();
        getPresenter().getCustomerPresenter();
        customerList = (ListView) findViewById(R.id.customer_list_view);

        btnNewCustomer = (FloatingActionButton) findViewById(R.id.activity_customer_fab_launch_create_customer);
        btnNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this, CustomerCreateActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showCustomerList(final ArrayList<Customer> customerArrayList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(customerArrayList);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getCustomerPresenter();
    }


    @Override
    public void showAlertDialogInternet(final int title, int message) {
        showAlertError(title, message);
    }

    @Override
    public void showAlertError(int title, int message) {
        showAlertDialog(title, message);
    }

    @Override
    public void showAlertError(int title, String message) {

    }

    public void showAlertDialog(final int title, final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(title, message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().getCustomerPresenter();
                    }
                }, R.string.option_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }

    public void callAdapter(final ArrayList<Customer> customerArrayList){
        customerAdapter = new CustomerAdapter(this, R.id.customer_list_view, customerArrayList);
        customerList.setAdapter(customerAdapter);
        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CustomerActivity.this, MapsActivity.class);
                intent.putExtra(Constants.ITEM_CUSTOMER, customerArrayList.get(position));
                startActivity(intent);
            }
        });
    }

}
