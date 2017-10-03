package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductUpdatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;

public class ProductUpdateActivity extends BaseActivity<ProductUpdatePresenter> implements IUpdateProductView, TextWatcher {

    private EditText name, description, quantity, price;
    private Button btn_update;
    private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);
        setPresenter(new ProductUpdatePresenter(new ProductRepository()));
        getPresenter().inject(this, getValidateInternet());
        initialize();
    }

    private void initialize() {
        product = new Product();

        //EditText
        name = (EditText) findViewById(R.id.activity_update_product_name);
        name.addTextChangedListener(this);

        description = (EditText) findViewById(R.id.activity_update_product_description);
        description.addTextChangedListener(this);

        quantity = (EditText) findViewById(R.id.activity_update_product_quantity);
        quantity.addTextChangedListener(this);

        price = (EditText) findViewById(R.id.activity_update_product_price);
        price.addTextChangedListener(this);

        //Button update
        btn_update = (Button) findViewById(R.id.activity_product_update_button_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataItem();
                updateProduct(product);
            }
        });

        getDataItem();
    }

    private void setDataItem() {
        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        product.setQuantity(quantity.getText().toString());
        product.setPrice(price.getText().toString());
    }

    public void validateFields(){
        if( name.getText().toString().isEmpty() ||
                price.getText().toString().isEmpty() ||
                quantity.getText().toString().isEmpty()  ||
                description.getText().toString().isEmpty()){
            disableButton(btn_update);
        }else{
            enableButton(btn_update);
        }
    }


    public void enableButton(Button button){
        button.setEnabled(true);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    public void disableButton(Button button){
        button.setEnabled(false);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
    }

    public void getDataItem(){
        product = (Product) getIntent().getSerializableExtra(Constants.ITEM_PRODUCT);

        name.setHint(product.getName());
        description.setHint(product.getDescription());
        quantity.setHint(product.getPrice());
        price.setHint(product.getPrice());

        name.setText(product.getName());
        description.setText(product.getDescription());
        quantity.setText(product.getQuantity());
        price.setText(product.getPrice());
    }

    public void updateProduct(Product product){
        getPresenter().updateProductPresenter(product.getId(),product);
    }

    public void closeActivity(){
        ProductUpdateActivity.this.finish();
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
    public void responseUpdateProduct(final boolean isUpdated) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //hidePorgress();
                if(isUpdated)
                    showToast(R.string.okUpdate);
                else
                    showToast(R.string.errorUpdate);

                ProductUpdateActivity.this.finish();
            }
        });
    }

    @Override
    public void showAlertDialog(final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(R.string.error, message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, R.string.option_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
    }

    @Override
    public void showToast(int msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProductUpdateActivity.this, R.string.okUpdate, Toast.LENGTH_LONG).show();
                closeActivity();
            }
        });
    }

    @Override
    public void showToast(final String msg) {
        showToast(msg);
        closeActivity();
    }

    @Override
    public void showAlertDialogError(int error) {

    }
}
