package io.github.alexlondon07.arquitecturamvpbase.dao;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.model.Product;

/**
 * Created by alexlondon07 on 9/30/17.
 */

public interface IProductDao {

    public ArrayList<Product> fetchAllProducts();
    public Boolean createProduct(Product product);
    public Boolean deleteProduct(String id);
    public Boolean updateProduct(String id , Product product);
}
