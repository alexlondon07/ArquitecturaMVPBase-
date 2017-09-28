package io.github.alexlondon07.arquitecturamvpbase.views.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
    }

    public void setDataItem(){
        nameValue.setText(product.getName());
        descriptionValue.setText(product.getDescription());
        priceValue.setText(product.getPrice());
        quantityValue.setText(product.getQuantity());
    }

    public void loadView(){
        //TextView
        nameValue = (TextView)  findViewById(R.id.product_detail_name_value);
        descriptionValue = (TextView)  findViewById(R.id.product_detail_description_value);
        quantityValue = (TextView)  findViewById(R.id.product_detail_quantity_value);
        priceValue = (TextView)  findViewById(R.id.product_detail_price_value);

        //Button delete
        btnDeleteProduct = (Button) findViewById(R.id.activity_create_product_button_delete);
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().deleteProduct(product.getId());
            }
        });
    }

    @Override
    public void showAlertDialog(int validate_internet) {
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        dialog1.setTitle(R.string.important);
        dialog1.setMessage(validate_internet);
        dialog1.setCancelable(false);
        dialog1.setNegativeButton(R.string.option_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog1, int id) {
                cancel();
            }
        });
        dialog1.show();
    }

    @Override
    public void showToast(int msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailsActivity.this, R.string.okDelete, Toast.LENGTH_SHORT).show();
                DetailsActivity.this.finish();
            }
        });
    }

    @Override
    public void showToast(final String msg) {


    }

    @Override
    public void showAlertDialogError(int error) {
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        dialog1.setTitle(R.string.error);
        dialog1.setMessage(error);
        dialog1.setCancelable(false);
        dialog1.setNegativeButton(R.string.option_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog1, int id) {
                cancel();
            }
        });
        dialog1.show();
    }

    public void cancel (){
        finish();
    }
}
