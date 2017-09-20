package io.github.alexlondon07.arquitecturamvpbase.presenter;
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

    public void validateInternetProduct(Product product) {
        if(getValidateInternet().isConnected()){
            createThreadProduct(product);
        }else{
            //TODO: implementación alert
        }
    }

    private void createThreadProduct(final Product product) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                createProduct(product);
            }
        });
        thread.start();
    }

    public void createProduct(Product product){
        try {
            Product products = productRepository.saveProduct(product);
            getView().responseCreateProduct(product);
        }catch (RetrofitError retrofitError){
            //TODO mostrar Alerta
        }
    }

}
