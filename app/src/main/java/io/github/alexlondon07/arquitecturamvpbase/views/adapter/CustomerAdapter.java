package io.github.alexlondon07.arquitecturamvpbase.views.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;

/**
 * Created by alexlondon07 on 10/3/17.
 */


public class CustomerAdapter extends ArrayAdapter<Customer> {

    private ArrayList<Customer> customerArrayList;
    private Activity context;
    private Customer customer;
    private TextView name, surname, phonelist;


    public CustomerAdapter(Activity context,  int resource, ArrayList<Customer> customerArrayList){
        super(context, resource, customerArrayList);
        this.context = context;
        this.customerArrayList = customerArrayList;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        this.customer = this.customerArrayList.get(position);
        loadView(convertView);
        name.setText(customer.getName());
        surname.setText(customer.getSurname());
        if(!customer.getPhoneList().isEmpty() || customer.getPhoneList().size() > 0) {
            phonelist.setText(customer.getPhoneList().get(0).getNumber().toString());
        }
        return convertView;
    }

    public void loadView(View convertView){
        name = (TextView) convertView.findViewById(R.id.item_name_customer);
        surname = (TextView) convertView.findViewById(R.id.item_surname_customer);
        phonelist = (TextView) convertView.findViewById(R.id.item_phonelist_customer);
    }
}
