package io.github.alexlondon07.arquitecturamvpbase.presenter;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.ProductResponse;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IDetailProductView;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 9/23/17.
 */

public class DetailProductPresenter extends BasePresenter<IDetailProductView> {

    private IProductRepository productRepository;

    public DetailProductPresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(String id) {
        if(getValidateInternet().isConnected()){
            deleteThreadDeleteProduct(id);
        }else {
            getView().showAlertDialog(R.string.validate_internet);
        }
    }


    public void deleteThreadDeleteProduct(final String id) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deleteProductService(id);
                } catch( RetrofitError retrofitError ){
                    retrofitError.printStackTrace();
                } finally {
                    getView().hidePorgress();
                }
            }
        });
        thread.start();
    }

    public void deleteProductService (String id){
        try{
            ProductResponse productResponse = productRepository.deleteProduct(id);
            if(productResponse.isStatus()){
                getView().showToast(R.string.okDelete);
            }else{
                getView().showAlertDialogError(R.string.errorDelete);
            }
        } catch (RepositoryError repositoryError) {
            getView().showToast(repositoryError.getMessage());
        }
    }
}
