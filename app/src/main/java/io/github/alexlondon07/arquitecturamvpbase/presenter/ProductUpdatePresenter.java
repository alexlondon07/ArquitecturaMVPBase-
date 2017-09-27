package io.github.alexlondon07.arquitecturamvpbase.presenter;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.model.ProductResponse;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IUpdateProductView;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 9/26/17.
 */

public class ProductUpdatePresenter extends BasePresenter<IUpdateProductView>{

    private IProductRepository productRepository;

    public ProductUpdatePresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateProduct(String id, Product product) {
        if(getValidateInternet().isConnected()){
            createThreadUpdateProduct(id, product);
        }else {
            getView().showAlertDialog(R.string.validate_internet);
        }

    }

    public void createThreadUpdateProduct(final String id, final Product product) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                updateProductService(id, product);
            } catch( RetrofitError retrofitError ){
                getView().showToast(retrofitError.getMessage());
            } finally {
                getView().hidePorgress();
            }
            }
        });
        thread.start();
    }

    public void updateProductService(String id, Product product) {
        try{
            ProductResponse updateResponse = productRepository.updateProduct(id, product);
            if(updateResponse.isStatus()){
                getView().showToast(R.string.okUpdate);
            }else{
                getView().showAlertDialogError(R.string.errorUpdate);
            }
        } catch (RepositoryError repositoryError) {
            getView().showToast(repositoryError.getMessage());
        }
    }
}
