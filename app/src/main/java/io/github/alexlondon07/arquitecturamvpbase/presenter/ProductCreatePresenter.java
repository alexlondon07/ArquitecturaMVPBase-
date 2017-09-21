package io.github.alexlondon07.arquitecturamvpbase.presenter;
import android.util.Log;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateProductView;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 9/19/17.
 */

public class ProductCreatePresenter extends BasePresenter<ICreateProductView> {
    private ProductRepository productRepository;

    public ProductCreatePresenter() {
        productRepository = new ProductRepository();
    }

    public void createThreadProduct(final Product product) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                createNewProductService(product);
            }
        });
        thread.start();
    }

    public void createNewProductService(Product product){
        try {
            Product responseProductCreated = productRepository.saveProduct(product);
            getView().responseCreateProduct(true);
        }catch (Exception e){
            e.printStackTrace();
            getView().showMessage("ERROR:"+e.getMessage());
            getView().responseCreateProduct(false);
        }
    }

}
