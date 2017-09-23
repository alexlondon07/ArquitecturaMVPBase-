package io.github.alexlondon07.arquitecturamvpbase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.alexlondon07.arquitecturamvpbase.helper.IValidateInternet;
import io.github.alexlondon07.arquitecturamvpbase.model.DeleteResponse;
import io.github.alexlondon07.arquitecturamvpbase.presenter.DetailProductPresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IDetailProductView;

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
    IDetailProductView iDetailProductView;

    DetailProductPresenter detailProductPresenter;

    @Before
    public void setUp() throws Exception{
        /*Aqui se hace la relacion entre el activiy y el presentadod*/
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

    @Test
    public void methodDeleteProductWithoutConnectionShouldShowAlertDialog(){
        String id = "gjhjg3jh4g35";
        when(validateInternet.isConnected()).thenReturn(false);
        detailProductPresenter.deleteProduct(id);
        verify(iDetailProductView).showAlertDialog(R.string.validate_internet);
        verify(detailProductPresenter, never()).deleteThreadDeleteProduct(id);
    }

    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepository(){
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setStatus(true);
        String id = "gjhjg3jh4g35";
        when(iProductRepository.deleteProduct(id)).thenReturn(deleteResponse);
        detailProductPresenter.deleteThreadDeleteProduct(id);
        Assert.assertTrue(deleteResponse.isStatus());
        verify(iDetailProductView).showToast(R.string.okDelete);
        verify(iDetailProductView, never()).showAlertDialogError(R.string.errorDelete);
    }

    @Test
    public void methodDeleteProductShouldCallMethodDeleteProductInRepositoryFalse(){
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setStatus(false);
        String id = "gjhjg3jh4g35";
        when(iProductRepository.deleteProduct(id)).thenReturn(deleteResponse);
        detailProductPresenter.deleteThreadDeleteProduct(id);
        Assert.assertFalse(deleteResponse.isStatus());
        verify(iDetailProductView).showAlertDialogError(R.string.errorDelete);
        verify(iDetailProductView , never()).showToast(R.string.okDelete);
    }

}
