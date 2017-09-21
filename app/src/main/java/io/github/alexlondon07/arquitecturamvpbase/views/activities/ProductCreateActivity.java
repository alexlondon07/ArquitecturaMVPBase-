package io.github.alexlondon07.arquitecturamvpbase.views.activities;
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
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;

public class ProductCreateActivity extends BaseActivity<ProductCreatePresenter> implements ICreateProductView, TextWatcher {

    private TextInputEditText name, description, quantity, price;
    private Button btn_create;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        setPresenter(new ProductCreatePresenter());
        getPresenter().inject(this, getValidateInternet());
        createProgresDialog();
        initialize();
        loadEvents();
    }

    public void initialize(){
        btn_create = (Button) findViewById(R.id.activity_create_product_button_create);
        disableButton(btn_create);

        name = (TextInputEditText)findViewById(R.id.activity_create_product_name);
        name.addTextChangedListener(this);

        description = (TextInputEditText) findViewById(R.id.activity_create_product_description);
        description.addTextChangedListener(this);

        quantity = (TextInputEditText)  findViewById(R.id.activity_create_product_quantity);
        quantity.addTextChangedListener(this);

        price = (TextInputEditText)  findViewById(R.id.activity_create_product_price);
        price.addTextChangedListener(this);
        createProgresDialog();
    }

    public void loadEvents(){
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product();
                product.setName(name.getText().toString());
                product.setDescription(description.getText().toString());
                product.setQuantity(quantity.getText().toString());
                product.setPrice(price.getText().toString());
                getPresenter().createThreadProduct(product);
            }
        });
    }

    public void validateFields(){
        if( name.getText().toString().isEmpty() ||
                price.getText().toString().isEmpty() ||
                quantity.getText().toString().isEmpty()  ||
                description.getText().toString().isEmpty()){
            disableButton(btn_create);
        }else{
            enableButton(btn_create);
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

    @Override
    public void responseCreateProduct(final boolean isCreated) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        try {
            if(isCreated){
                Toast.makeText(ProductCreateActivity.this, getResources().getString(R.string.okCreateProduct), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(ProductCreateActivity.this, getResources().getString(R.string.errorCreateProduct), Toast.LENGTH_SHORT).show();
            }
            ProductCreateActivity.this.finish();
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
            }
    });
        
    }

}
