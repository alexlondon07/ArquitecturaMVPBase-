package io.github.alexlondon07.arquitecturamvpbase.service;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.model.ProductResponse;
import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public interface IServices {
    @GET("/products")
    ArrayList<Product> getProductList();

    @POST("/products")
    Product saveProduct(@Body Product product);

    @DELETE("/products/{id}")
    ProductResponse deleteProduct(@Path("id") String id);

    @PUT("/products/{id}")
    ProductResponse updateProduct(@Path("id") String id, @Body Product product);


    @GET("/customers")
    ArrayList<Customer> getCustomerList();

    @POST("/customers")
    Customer saveCustomer(@Body Customer customer);

}
