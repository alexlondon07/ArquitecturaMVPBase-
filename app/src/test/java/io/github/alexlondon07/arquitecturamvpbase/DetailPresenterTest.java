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
import io.github.alexlondon07.arquitecturamvpbase.model.ProductResponse;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.DetailProductPresenter;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductUpdatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateProductView;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IDetailProductView;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IUpdateProductView;

import static org.mockito.Matchers.refEq;
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

    @Mock
    ICreateProductView iCreateProductView;

    DetailProductPresenter detailProductPresenter;

    ProductUpdatePresenter productUpdatePresenter;

    ProductCreatePresenter createProductPresenter;

    @Mock
    IUpdateProductView iUpdateProductView;

    @InjectMocks
    Product product;

    @Before
    public void setUp() throws Exception{
        /*Aqui se hace la relacion entre el activity y el presentado*/
        detailProductPresenter = Mockito.spy(new DetailProductPresenter(iProductRepository));
        detailProductPresenter.inject(iDetailProductView, validateInternet);

        productUpdatePresenter = Mockito.spy(new ProductUpdatePresenter(iProductRepository));
        productUpdatePresenter.inject(iUpdateProductView, validateInternet);

        createProductPresenter = Mockito.spy(new ProductCreatePresenter(iProductRepository));
        createProductPresenter.inject(iCreateProductView, validateInternet);
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

    @Test
    public void methodDeleteProductShouldCallMethodUpdateProductInRepositoryTrue() throws RepositoryError{
        String id = "gjhjg3jh4g35";
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        Product product = new Product();

        ProductResponse productResponde = new ProductResponse();
        productResponde.setStatus(true);

        when(iProductRepository.updateProduct(id, product)).thenReturn(productResponde);
        productUpdatePresenter.updateProductService(id, product);
        verify(productUpdatePresenter).updateProductService(id, product);
        verify(iUpdateProductView).showToast(R.string.okUpdate);
        verify(iUpdateProductView, never()).showToast(R.string.errorUpdate);
    }

    @Test
    public void methodValidateInternetShouldCallMethodCreateThread(){
        product.setName("empanada");
        product.setDescription("empanada");
        product.setQuantity("empanada");
        product.setPrice("empanada");
        when(validateInternet.isConnected()).thenReturn(true);
        createProductPresenter.createNewProduct("empanada", "empanada", "empanada", "empanada");
        verify(createProductPresenter).createThreadProduct(refEq(product));
    }
}
