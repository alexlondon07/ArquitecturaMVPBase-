package io.github.alexlondon07.arquitecturamvpbase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.alexlondon07.arquitecturamvpbase.helper.IValidateInternet;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.presenter.ProductCreatePresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.IProductRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ICreateProductView;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexlondon07 on 10/3/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateProductPresenterTest {
    @Mock
    IValidateInternet validateInternet;

    @Mock
    IProductRepository productRepository;

    @Mock
    ICreateProductView createProductView;

    ProductCreatePresenter createProductPresenter;

    @InjectMocks
    Product product;


    @Before
    public void setUp() throws Exception{
        createProductPresenter = Mockito.spy(new ProductCreatePresenter(productRepository));
        createProductPresenter.inject(createProductView, validateInternet);
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
