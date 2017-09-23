package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.DetailProductPresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class DetailsActivity extends BaseActivity<DetailProductPresenter> implements IDetailProductView {
    private TextView nameValue;
    private TextView descriptionValue;
    private TextView quantityValue;
    private TextView priceValue;
    private Product product;
    private Button btnDeleteProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setPresenter(new DetailProductPresenter(new ProductRepository()));
        getPresenter().inject(this, getValidateInternet());
        createProgresDialog();
        loadView();
        product = (Product) getIntent().getSerializableExtra(Constants.ITEM_PRODUCT);
        setDataItem();
        loadEvents();
    }

    public void setDataItem(){
        nameValue.setText(product.getName());
        descriptionValue.setText(product.getDescription());
        priceValue.setText(product.getPrice());
        quantityValue.setText(product.getQuantity());
        btnDeleteProduct = (Button) findViewById(R.id.activity_create_product_button_delete);
    }


    public void loadEvents(){
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().deleteProduct(product.getId());
            }
        });
    }

    public void loadView(){
        nameValue = (TextView)  findViewById(R.id.product_detail_name_value);
        descriptionValue = (TextView)  findViewById(R.id.product_detail_description_value);
        quantityValue = (TextView)  findViewById(R.id.product_detail_quantity_value);
        priceValue = (TextView)  findViewById(R.id.product_detail_price_value);
    }


    @Override
    public void showAlertDialog(int validate_internet) {
        
    }

    @Override
    public void showToast(final int okCreateProduct) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailsActivity.this, R.string.okDelete, Toast.LENGTH_LONG).show();
                DetailsActivity.this.finish();
            }
        });
    }


    @Override
    public void showAlertDialogError(int okDeleteProduct) {

    }
}
