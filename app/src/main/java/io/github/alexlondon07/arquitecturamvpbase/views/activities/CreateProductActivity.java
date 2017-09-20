package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;

public class CreateProductActivity extends BaseActivity<ProductCreatePresenter> implements ICreateProductView, TextWatcher {

    private TextInputEditText name;
    private TextInputEditText description;
    private TextInputEditText quantity;
    private TextInputEditText price;
    private Button btn_create;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        setPresenter(new ProductCreatePresenter());
        getPresenter().inject(this, getValidateInternet());

        /*Obtemos los valores de los Inputs*/
        name = (TextInputEditText)findViewById(R.id.activity_create_product_name);
        name.addTextChangedListener(this);

        description = (TextInputEditText) findViewById(R.id.activity_create_product_description);
        description.addTextChangedListener(this);

        quantity = (TextInputEditText)  findViewById(R.id.activity_create_product_quantity);
        quantity.addTextChangedListener(this);

        price = (TextInputEditText)  findViewById(R.id.activity_create_product_price);
        price.addTextChangedListener(this);

        btn_create = (Button) findViewById(R.id.activity_create_product_button_create);
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        validateField();
    }

    public void validateField(){
        if( name.getText().toString().length() == 0 || price.getText().toString().length() == 0 || quantity.getText().toString().length() == 0 || description.getText().toString().length() == 0){
            btn_create.setEnabled(false);
            btn_create.setBackgroundColor(Color.DKGRAY);
        }else{
            btn_create.setEnabled(true);
            btn_create.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public void responseCreateProduct(Product product) {

    }
}
