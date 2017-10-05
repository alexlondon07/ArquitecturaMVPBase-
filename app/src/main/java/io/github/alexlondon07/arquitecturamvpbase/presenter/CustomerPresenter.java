package io.github.alexlondon07.arquitecturamvpbase.presenter;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.repository.CustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.ICustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICustomerView;

/**
 * Created by alexlondon07 on 10/3/17.
 */

public class CustomerPresenter extends BasePresenter<ICustomerView> {

    private ICustomerRepository customerRepository;

    public CustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void getCustomerPresenter() {
        if(getValidateInternet().isConnected()){
            createThreadCustomer();
        }else{
            getView().showAlertDialogInternet(R.string.error, R.string.validate_internet);
        }

    }

    public void createThreadCustomer() {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getCustomerListService();
            }
        });
        thread.start();
    }

    public void getCustomerListService() {
        try {
            ArrayList<Customer> customerArrayList = customerRepository.getCustomersList();
            getView().showCustomerList(customerArrayList);
        }catch (RepositoryError repositoryError){
            getView().showAlertError(R.string.error, repositoryError.getMessage());
        }finally {
            getView().hidePorgress();
        }
    }

}
