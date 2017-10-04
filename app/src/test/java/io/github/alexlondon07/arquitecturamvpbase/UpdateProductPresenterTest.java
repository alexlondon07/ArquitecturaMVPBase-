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
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.model.ProductResponse;
import io.github.alexlondon07.arquitecturamvpbase.presenter.DetailProductPresenter;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductUpdatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.repository.RepositoryError;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.IUpdateProductView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexlondon07 on 10/4/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class UpdateProductPresenterTest {

    @Mock
    IValidateInternet validateInternet;


    @Mock
    IProductRepository iProductRepository;

    ProductUpdatePresenter productUpdatePresenter;


    @Mock
    IUpdateProductView iUpdateProductView;

    @InjectMocks
    Product product;


    @Before
    public void setUp() throws Exception{
        /*Aqui se hace la relacion entre el activity y el presentado*/
        productUpdatePresenter = Mockito.spy(new ProductUpdatePresenter(iProductRepository));
        productUpdatePresenter.inject(iUpdateProductView, validateInternet);
    }

    @Test
    public void methodUpdateProductShouldCallMethodUpdateProductInRepositoryTrue() throws RepositoryError {
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
    public void methodUpdateProductShouldCallMethodUpdateProductInRepositoryFalse() throws RepositoryError {
        String id = "gjhjg3jh4g35";
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        Product product = new Product();

        ProductResponse productResponde = new ProductResponse();
        productResponde.setStatus(false);

        when(iProductRepository.updateProduct(id, product)).thenReturn(productResponde);
        productUpdatePresenter.updateProductService(id, product);
        verify(productUpdatePresenter).updateProductService(id, product);
        verify(iUpdateProductView).showAlertDialogError(R.string.errorUpdate);
        verify(iUpdateProductView, never()).showToast(R.string.okUpdate);
    }
}
