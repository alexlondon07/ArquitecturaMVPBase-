package io.github.alexlondon07.arquitecturamvpbase.repository;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.helper.ServicesFactory;
import io.github.alexlondon07.arquitecturamvpbase.model.DeleteResponse;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.service.IServices;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class ProductRepository implements IProductRepository {
    private IServices services;


    public ProductRepository() {
        ServicesFactory servicesfactory = new ServicesFactory();
        services = (IServices) servicesfactory.getInstance(IServices.class);
    }

    @Override
    public ArrayList<Product> getProductList() throws RetrofitError{
        ArrayList<Product> products = services.getProductList();
        return products;
    }

    @Override
    public Product saveProduct(Product product) throws RetrofitError{
        Product products = services.saveProduct(product);
        return products;
    }

    @Override
    public DeleteResponse deleteProduct(String id) {
        return services.deleteProduct(id);
    }
}
