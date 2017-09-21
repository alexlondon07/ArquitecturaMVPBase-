package io.github.alexlondon07.arquitecturamvpbase.views.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductPresenter;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.adapter.ProductAdapter;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class ProductActivity extends BaseActivity<ProductPresenter> implements IProductView{

    private ListView productList;
    private ProductAdapter productAdapter;
    private FloatingActionButton btnNewProduct;

    @Override
    public void showProductList(final ArrayList<Product> productArrayList) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               callAdapter(productArrayList);
           }
       });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setPresenter(new ProductPresenter());
        getPresenter().inject(this, getValidateInternet());
        createProgresDialog();
        getPresenter().validateInternetProduct();
        productList = (ListView) findViewById(R.id.product_list_view);

        btnNewProduct =  (FloatingActionButton) findViewById(R.id.activity_product_fab_launch_createproduct);
        btnNewProduct.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ProductCreateActivity.class);
                startActivity(intent);
            }
        });
    }



    public void callAdapter(final ArrayList<Product> productArrayList){
        productAdapter = new ProductAdapter(this, R.id.product_list_view, productArrayList);
        productList.setAdapter(productAdapter);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT, productArrayList.get(position));
                startActivity(intent);
            }
        });

    }


}
