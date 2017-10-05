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
import io.github.alexlondon07.arquitecturamvpbase.presenter.CustomerPresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.ICustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICustomerView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexlondon07 on 10/4/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CustomerPresenterTest {

    @Mock
    IValidateInternet validateInternet;

    @Mock
    ICustomerRepository iCustomerRepository;

    @Mock
    ICustomerView iCustomerView;

    @InjectMocks
    Customer customer;

    @Mock
    CustomerPresenter customerPresenter;

    @Before
    public void setUp() throws Exception{
        customerPresenter = Mockito.spy(new CustomerPresenter(iCustomerRepository));
        customerPresenter.inject(iCustomerView, validateInternet);
    }


    @Test
    public void methodGetCustomerWithoutConnectionShouldShowAlertDialogInternet(){
        when(validateInternet.isConnected()).thenReturn(false);
        customerPresenter.getCustomerPresenter();
        verify(customerPresenter).getCustomerPresenter();
        verify(iCustomerView).showAlertDialogInternet(R.string.error, R.string.validate_internet);
        verify(customerPresenter, never()).createThreadCustomer();
    }

    @Test
    public void methodGetCustomerWithConnectionNotShouldShowAlertDialogInternet(){
        when(validateInternet.isConnected()).thenReturn(true);
        customerPresenter.getCustomerPresenter();
        verify(customerPresenter).getCustomerPresenter();
        verify(customerPresenter).createThreadCustomer();
        verify(iCustomerView,never()).showAlertDialogInternet(R.string.error, R.string.validate_internet);
    }

    @Test
    public void methodGetCustomerWithConnectionShouldShowAlertError() throws RepositoryError {
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        when(iCustomerRepository.getCustomersList()).thenThrow(repositoryError);
        customerPresenter.getCustomerListService();
        verify(iCustomerView).showAlertError(R.string.error, repositoryError.getMessage());
    }

    @Test
    public void methodGetCustomerListServiceShouldGetCustomersList() throws RepositoryError {
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        customerPresenter.getCustomerListService();
        verify(iCustomerRepository).getCustomersList();
        verify(iCustomerView,never()).showAlertError(R.string.error, repositoryError.getMessage());
    }


}
