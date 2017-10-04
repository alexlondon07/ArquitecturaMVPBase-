package io.github.alexlondon07.arquitecturamvpbase.presenter;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.repository.ICustomerRepository;
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
        if (getValidateInternet().isConnected()){
            createThreadCreateCustomer(customer);
        }else{
            getView().showAlertDialog(R.string.validate_internet);
        }
    }

    public static void createThreadCreateCustomer(Customer customer) {

    }

    public static void createNewCustomer(Customer customer) {

    }
}
