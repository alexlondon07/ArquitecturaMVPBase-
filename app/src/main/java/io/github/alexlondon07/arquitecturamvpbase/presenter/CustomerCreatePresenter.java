package io.github.alexlondon07.arquitecturamvpbase.presenter;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.model.CustomerResponse;
import io.github.alexlondon07.arquitecturamvpbase.repository.ICustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateCustomerView;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 10/3/17.
 */

public class CustomerCreatePresenter extends BasePresenter<ICreateCustomerView> {

    private ICustomerRepository iCustomerRepository;

    public CustomerCreatePresenter(ICustomerRepository iCustomerRepository) {
        this.iCustomerRepository = iCustomerRepository;
    }


    public void createNewCustomerPresenter(Customer customer) {
        if (getValidateInternet().isConnected()){
            createThreadCreateCustomer(customer);
        }else{
            getView().showAlertDialog(R.string.validate_internet);
        }
    }

    public void createThreadCreateCustomer(final Customer customer) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                createNewCustomer(customer);
            }
        });
        thread.start();
    }

    public void createNewCustomer(final Customer customer) {
        /*try{
            CustomerResponse isCreated = iCustomerRepository.saveCustomer(customer);
            if(isCreated.isStatus()){
                getView().showToast(R.string.okDelete);
            }else{
                getView().showAlertDialog(R.string.errorCreate);
            }
        }catch (RepositoryError repositoryError){
            getView().showAlertError(R.string.error, repositoryError.getMessage());
        }finally {
            getView().hidePorgress();
        }*/
    }
}
