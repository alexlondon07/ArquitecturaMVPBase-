package io.github.alexlondon07.arquitecturamvpbase.views.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class ProductAdapter extends ArrayAdapter<Product>{

    private ArrayList<Product> productArrayList;
    private Activity context;
    private  Product product;
    private TextView name, sync;


    public ProductAdapter(Activity context,  int resouce, ArrayList<Product> productArrayList){
        super(context, resouce, productArrayList);
        this.context = context;
        this.productArrayList = productArrayList;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        this.product = this.productArrayList.get(position);
        loadView(convertView);
        name.setText(product.getName());
        return convertView;
    }

    public void loadView(View convertView){
        name = (TextView) convertView.findViewById(R.id.item_name_product);
    }

}
