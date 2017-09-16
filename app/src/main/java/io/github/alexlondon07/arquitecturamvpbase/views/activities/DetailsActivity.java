package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class DetailsActivity extends AppCompatActivity {
    private TextView nameValue;
    private TextView descriptionValue;
    private TextView quantityValue;
    private TextView priceValue;
    private Product product;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        loadView();
        product = (Product) getIntent().getSerializableExtra(Constants.ITEM_PRODUCT);
        setDataItem();
    }

    public void setDataItem(){
        nameValue.setText(product.getName());
        descriptionValue.setText(product.getDescription());
        priceValue.setText(product.getPrice());
        quantityValue.setText(product.getQuantity());
    }

    public void loadView(){
        nameValue = (TextView)  findViewById(R.id.product_detail_name_value);
        descriptionValue = (TextView)  findViewById(R.id.product_detail_description_value);
        quantityValue = (TextView)  findViewById(R.id.product_detail_quantity_value);
        priceValue = (TextView)  findViewById(R.id.product_detail_price_value);
    }


}
