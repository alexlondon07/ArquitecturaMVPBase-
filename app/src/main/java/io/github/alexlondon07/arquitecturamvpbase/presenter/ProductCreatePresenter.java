package io.github.alexlondon07.arquitecturamvpbase.presenter;

import java.util.UUID;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.helper.Database;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.ProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateProductView;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 9/19/17.
 */

public class ProductCreatePresenter extends BasePresenter<ICreateProductView> {

    private IProductRepository productRepository;

    public ProductCreatePresenter(IProductRepository productRepository) {
        this.productRepository =  productRepository;
    }


    public void createNewProduct(String name, String description, String price, String quantity) {
        Product product = new Product();
        //product.setId(UUID.randomUUID().toString());
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        if (getValidateInternet().isConnected()){
            createThreadProduct(product);
        }else{
            //getView().showAlertDialog(R.string.validate_internet);
            product.setSync("0");
            createThreadProductLocal(product);
        }
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

    public void createThreadProductLocal(final Product product) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                createNewProductLocal(product);
            }
        });
        thread.start();
    }

    private void createNewProductLocal(Product product){
        try{
            boolean isCreated = Database.productDao.createProduct(product);
            getView().responseCreateProduct(isCreated);
        }catch (Exception ex){
            getView().responseCreateProduct(false);
        }
    }

    private void createNewProductService(Product product){
        try{
            productRepository.saveProduct(product);
            getView().responseCreateProduct(true);
        }catch (RetrofitError retrofitError){
            getView().responseCreateProduct(false);
        }
    }

}
