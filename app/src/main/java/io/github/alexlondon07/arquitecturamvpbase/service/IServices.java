package io.github.alexlondon07.arquitecturamvpbase.service;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import retrofit.http.GET;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public interface IServices {
    @GET("/product")
    ArrayList<Product> getProductList();
}
