package io.github.alexlondon07.arquitecturamvpbase.views.activities;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;

public class ProductCreateActivity extends BaseActivity<ProductCreatePresenter> implements ICreateProductView, TextWatcher {

    private TextInputEditText name, description, quantity, price, sync;
    private Button btn_create;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        setPresenter(new ProductCreatePresenter(new ProductRepository()));
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
                setDataItem();
            }
        });
    }

    private void setDataItem() {
        Product product = new Product();
        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        product.setQuantity(quantity.getText().toString());
        product.setPrice(price.getText().toString());

        getPresenter().createNewProduct(name.getText().toString(), description.getText().toString(), quantity.getText().toString(),price.getText().toString());
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
                hidePorgress();
                if(isCreated)
                    showToast(R.string.okCreate);
                else
                    showToast(R.string.errorCreate);

                ProductCreateActivity.this.finish();
            }
        });
    }

    @Override
    public void showAlertDialog(int message) {
        getShowAlertDialog().showAlertDialog(R.string.error, message, false, R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, R.string.option_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeActivity();
            }
        });
    }

    @Override
    public void showToast(final int msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProductCreateActivity.this, msg, Toast.LENGTH_SHORT).show();
                closeActivity();
            }
        });
    }

    private void closeActivity() {
        ProductCreateActivity.this.finish();
    }
}
