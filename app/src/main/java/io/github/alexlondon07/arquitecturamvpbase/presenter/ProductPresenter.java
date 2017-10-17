package io.github.alexlondon07.arquitecturamvpbase.presenter;

import android.util.Log;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.dao.ProductDao;
import io.github.alexlondon07.arquitecturamvpbase.helper.Database;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IProductView;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class ProductPresenter extends BasePresenter<IProductView> {

    private ProductRepository productRepository;

    public ProductPresenter() {
        productRepository = new ProductRepository();
    }

    public void getProductsPresenter() {
        if(getValidateInternet().isConnected()){
            createThreadProduct();
        }else{
            //getView().showAlertDialogInternet(R.string.error, R.string.validate_internet);
            createThreadProductLocal();
        }
    }

    private void createThreadProductLocal() {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getProductListLocal();
            }
        });
        thread.start();
    }

    private void createThreadProduct() {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getProductList();
            }
        });
        thread.start();
    }

    private void getProductListLocal() {
        try {
            ArrayList<Product> productArrayList = Database.productDao.fetchAllProducts();
            getView().showProductList(productArrayList);
        }catch (Exception ex) {
            Log.w("ErrorGetProductList", ex.getMessage());
            getView().showAlertError(R.string.error, ex.getMessage());
        }finally {
            getView().hidePorgress();
        }
    }

    public void getProductList(){
        try {
            ArrayList<Product> productArrayList = productRepository.getProductList();
            getView().showProductList(productArrayList);
        }catch (RetrofitError retrofitError){
            getView().showAlertError(R.string.error, R.string.validate_internet);
        }finally {
            getView().hidePorgress();
        }
    }
}
