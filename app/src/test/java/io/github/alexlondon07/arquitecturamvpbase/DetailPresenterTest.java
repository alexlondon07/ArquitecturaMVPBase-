package io.github.alexlondon07.arquitecturamvpbase;

import org.junit.Assert;
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
import io.github.alexlondon07.arquitecturamvpbase.model.ProductResponse;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.CustomerCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.presenter.DetailProductPresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.ICustomerRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateProductView;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICustomerView;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IDetailProductView;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IUpdateProductView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexlondon07 on 9/23/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    @Mock
    IValidateInternet validateInternet;

    @Mock
    IProductRepository iProductRepository;


    @Mock
    ICustomerRepository iCustomerRepository;


    @Mock
    IDetailProductView iDetailProductView;

    @Mock
    ICreateProductView iCreateProductView;

    @Mock
    ICustomerView iCustomerView;

    DetailProductPresenter detailProductPresenter;


    CustomerCreatePresenter customerCreatePresenter;

    @Mock
    IUpdateProductView iUpdateProductView;

    @InjectMocks
    Product product;

    @InjectMocks
    Customer customer;

    @Before
    public void setUp() throws Exception{
        /*Aqui se hace la relacion entre el activity y el presentado*/
        detailProductPresenter = Mockito.spy(new DetailProductPresenter(iProductRepository));
        detailProductPresenter.inject(iDetailProductView, validateInternet);
    }

    @Test
    public void methodDeleteProductWithConnectionShouldCallMethodCreateThreadDeleteProduct(){
        String id = "gjhjg3jh4g35";
        when(validateInternet.isConnected()).thenReturn(true);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductPresenter).deleteThreadDeleteProduct(id);
    }

    //BAD TEST
    @Test
    public void methodDeleteProductWithoutConnectionShouldShowAlertDialog(){
        String id = "gjhjg3jh4g35";
        when(validateInternet.isConnected()).thenReturn(false);
        detailProductPresenter.deleteProduct(id);
        verify(iDetailProductView).showAlertDialog(R.string.validate_internet);
        verify(detailProductPresenter, never()).deleteThreadDeleteProduct(id);
    }

    //BAD TEST
    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepository() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(true);
        String id = "gjhjg3jh4g35";
        when(iProductRepository.deleteProduct(id)).thenReturn(productResponse);
        detailProductPresenter.deleteThreadDeleteProduct(id);
        Assert.assertTrue(productResponse.isStatus());
        verify(iDetailProductView).showToast(R.string.okDelete);
        verify(iDetailProductView, never()).showAlertDialogError(R.string.errorDelete);
    }

    //BAD TEST
    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepositoryFalse() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(false);
        String id = "gjhjg3jh4g35";
        when(iProductRepository.deleteProduct(id)).thenReturn(productResponse);
        detailProductPresenter.deleteThreadDeleteProduct(id);
        Assert.assertFalse(productResponse.isStatus());
        verify(iDetailProductView).showAlertDialogError(R.string.errorDelete);
        verify(iDetailProductView , never()).showToast(R.string.okDelete);
    }

    @Test
    public void methodDeleteProductShouldShowAlertWhenRepositoryReturnError() throws RepositoryError{
        String id = "gjhjg3jh4g35";
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        when(iProductRepository.deleteProduct(id)).thenThrow(repositoryError);
        detailProductPresenter.deleteProductService(id);
        verify(iDetailProductView).showToast(repositoryError.getMessage());
        verify(iDetailProductView, never()).showToast(R.string.okDelete);
        verify(iDetailProductView, never()).showToast(R.string.errorDelete);
    }

    /*@Test
    public void methodCreateCustomerShouldCall() throws RepositoryError{
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        Customer customer = new Customer();
        when(validateInternet.isConnected()).thenReturn(true);
        CustomerCreatePresenter.createNewCustomer(customer);
        verify(customerCreatePresenter).createThreadCreateCustomer(customer);
    }


    public Customer getCustomersList(){
        Customer customer = new Customer();

        customer.setName("Alexander");
        customer.setSurname("Londoño Espejo");

        //PhoneList
        ArrayList<PhoneList> phoneLists = new ArrayList<>();

        PhoneList phoneList = new PhoneList();
        phoneList.setDescripcion("Phone mobile");
        phoneList.setNumber("3122195522");

        //Location
        Location location = new Location();
        location.setType("Point");
        Double coordinates[] = {-75.0003,42.002};
        location.setCoordinates(coordinates);

        customer.setPhoneList(phoneLists);
        return customer;
    }*/
}
