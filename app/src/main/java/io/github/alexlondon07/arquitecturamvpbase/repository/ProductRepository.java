package io.github.alexlondon07.arquitecturamvpbase.repository;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.helper.ServicesFactory;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.service.IServices;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class ProductRepository {
    private IServices services;


    public ProductRepository() {
        ServicesFactory servicesfactory = new ServicesFactory();
        services = (IServices) servicesfactory.getInstance(IServices.class);
    }

    public ArrayList<Product> getProductList() throws RetrofitError{
        ArrayList<Product> products = services.getProductList();
        return products;
    }
}
