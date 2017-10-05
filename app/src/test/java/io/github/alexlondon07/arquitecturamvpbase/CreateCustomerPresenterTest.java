package io.github.alexlondon07.arquitecturamvpbase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.helper.IValidateInternet;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.presenter.CustomerCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.ICustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateCustomerView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexlondon07 on 10/4/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomerPresenterTest {


    @Mock
    IValidateInternet validateInternet;

    @Mock
    ICustomerRepository iCustomerRepository;


    @Mock
    ICreateCustomerView iCreateCustomerView;

    @InjectMocks
    Customer customer;

    @Mock
    CustomerCreatePresenter customerCreatePresenter;

    @Before
    public void setUp() throws Exception{
        customerCreatePresenter = Mockito.spy(new CustomerCreatePresenter(iCustomerRepository));
        customerCreatePresenter.inject(iCreateCustomerView, validateInternet);

    }

    @Test
    public void methodCreateNewCustomerPresenterWithoutConnectionShouldShowAlertDialog(){
        Customer customer = new Customer();
        when(validateInternet.isConnected()).thenReturn(false);
        customerCreatePresenter.createNewCustomerPresenter(customer);
        verify(customerCreatePresenter).createNewCustomerPresenter(customer);
        verify(iCreateCustomerView).showAlertDialog(R.string.validate_internet);
        verify(customerCreatePresenter, never()).createThreadCreateCustomer(customer);
    }

    @Test
    public void methodCreateNewCustomerPresenterWithConnectionShouldCallCreateThreadCreateCustomer(){
        Customer customer = new Customer();
        when(validateInternet.isConnected()).thenReturn(true);
        customerCreatePresenter.createNewCustomerPresenter(customer);
        verify(customerCreatePresenter).createNewCustomerPresenter(customer);
        verify(customerCreatePresenter).createThreadCreateCustomer(customer);
        verify(iCreateCustomerView , never()).showAlertDialog(R.string.validate_internet);
    }


    @Test
    public void methodGetCustomerWithConnectionShouldShowAlertError() throws RepositoryError {
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        when(iCustomerRepository.saveCustomer(customer)).thenThrow(repositoryError);
        customerCreatePresenter.createNewCustomerService(customer);
        verify(iCreateCustomerView).showAlertError(R.string.error, repositoryError.getMessage());
    }

    @Test
    public void methodGetCustomerWithConnectionShouldCallCreateNewCustomerService() throws RepositoryError {
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        customerCreatePresenter.createNewCustomerService(customer);
        verify(customerCreatePresenter).createNewCustomerService(customer);
        verify(iCreateCustomerView, never()).showAlertError(R.string.error, repositoryError.getMessage());
    }

}
