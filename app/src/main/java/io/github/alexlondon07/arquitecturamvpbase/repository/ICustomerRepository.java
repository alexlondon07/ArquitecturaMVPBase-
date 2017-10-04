package io.github.alexlondon07.arquitecturamvpbase.repository;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.model.Customer;


/**
 * Created by alexlondon07 on 10/3/17.
 */

public interface ICustomerRepository {

    ArrayList<Customer> getCustomersList() throws RepositoryError;

    Customer saveCustomer(Customer customer) throws RepositoryError;
}
