package io.github.alexlondon07.arquitecturamvpbase.repository;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.model.DeleteResponse;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;

/**
 * Created by alexlondon07 on 9/23/17.
 */

public interface IProductRepository {

    ArrayList<Product> getProductList();

    Product saveProduct(Product product);

    DeleteResponse deleteProduct(String id) throws RepositoryError;
}
