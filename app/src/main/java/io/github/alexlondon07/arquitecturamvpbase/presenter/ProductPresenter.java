package io.github.alexlondon07.arquitecturamvpbase.presenter;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
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

    public void getPoductsPresenter() {

        if(getValidateInternet().isConnected()){

            createThreadProduct();

        }else{
            //TODO: implementación alert
        }

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

    public void getProductList(){
        try {

            ArrayList<Product> productArrayList = productRepository.getProductList();
            getView().showProductList(productArrayList);
        }catch (RetrofitError retrofitError){
            //TODO mostrar Alerta
        }finally {
            getView().hidePorgress();
        }

    }
}
