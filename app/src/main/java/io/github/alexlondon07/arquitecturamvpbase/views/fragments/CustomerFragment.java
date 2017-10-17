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
import android.widget.ListView;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.presenter.CustomerPresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.CustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.CustomerActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.CustomerCreateActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICustomerView;
import io.github.alexlondon07.arquitecturamvpbase.views.adapter.CustomerAdapter;

/**
 * Created by alexlondon07 on 10/14/17.
 */

public class CustomerFragment extends BaseFragment<CustomerPresenter> implements ICustomerView {

    private ListView customerList;
    private CustomerAdapter customerAdapter;
    private FloatingActionButton btnNewCustomer;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_customer, container, false);

        getBaseActivity().createProgresDialog();
        getBaseActivity().showProgress(R.string.loading_message);
        setPresenter(new CustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());

        customerList = view.findViewById(R.id.customer_list_view);
        customerList.setNestedScrollingEnabled(true);

        getPresenter().getCustomerPresenter();

        btnNewCustomer =  view.findViewById(R.id.activity_customer_fab_launch_create_customer);
        btnNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomerCreateActivity.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout =  view.findViewById(R.id.activity_customer_swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getCustomerPresenter();
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //swipeRefreshLayout.setRefreshing(true);
        getPresenter().getCustomerPresenter();
    }

    @Override
    public void showCustomerList(final ArrayList<Customer> customerArrayList) {
        getBaseActivity().hidePorgress();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
               callAdapter(customerArrayList);
            }
        });
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                getBaseActivity().getShowAlertDialog().showAlertDialog(title, message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().getCustomerPresenter();
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

    public void callAdapter(final ArrayList<Customer> customerArrayList){
        customerAdapter = new CustomerAdapter(getActivity(), R.id.customer_list_view, customerArrayList);
        customerList.setAdapter(customerAdapter);
    }
}
