package io.github.alexlondon07.arquitecturamvpbase.presenter;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.repository.ICustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateCustomerView;

/**
 * Created by alexlondon07 on 10/3/17.
 */

public class CustomerCreatePresenter extends BasePresenter<ICreateCustomerView> {

    private ICustomerRepository iCustomerRepository;

    public CustomerCreatePresenter(ICustomerRepository iCustomerRepository) {
        this.iCustomerRepository = iCustomerRepository;
    }


    public void createNewCustomerPresenter(Customer customer) {
        if (getValidateInternet().isConnected()) {
            createThreadCreateCustomer(customer);
        } else {
            getView().showAlertDialog(R.string.validate_internet);
        }
    }

    public void createThreadCreateCustomer(final Customer customer) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                createNewCustomerService(customer);
            }
        });
        thread.start();
    }

    public void createNewCustomerService(final Customer customer) {
        try{
            iCustomerRepository.saveCustomer(customer);
            getView().responseCreateCustomer(true);
        } catch (RepositoryError repositoryError) {
            getView().showAlertError(R.string.error, repositoryError.getMessage());
            getView().responseCreateCustomer(false);
        }
    }
}

