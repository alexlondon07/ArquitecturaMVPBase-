package io.github.alexlondon07.arquitecturamvpbase.repository;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.helper.ServicesFactory;
import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.service.IServices;

/**
 * Created by alexlondon07 on 10/3/17.
 */

public class CustomerRepository implements ICustomerRepository {

    private IServices services;

    public CustomerRepository() {
        ServicesFactory servicesfactory = new ServicesFactory();
        services = (IServices) servicesfactory.getInstance(IServices.class);
    }

    @Override
    public ArrayList<Customer> getCustomersList() throws RepositoryError {
        ArrayList<Customer> customers  = services.getCustomerList();
        return  customers;
    }

    @Override
    public Customer saveCustomer(Customer customer) throws RepositoryError {
        return null;
    }
}
