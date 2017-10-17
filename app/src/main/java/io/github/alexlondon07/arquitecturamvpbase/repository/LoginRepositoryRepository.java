package io.github.alexlondon07.arquitecturamvpbase.repository;

import io.github.alexlondon07.arquitecturamvpbase.helper.ServicesFactory;
import io.github.alexlondon07.arquitecturamvpbase.model.User;
import io.github.alexlondon07.arquitecturamvpbase.service.ILoginServices;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 10/14/17.
 */

public class LoginRepositoryRepository implements ILoginRepository {

    ILoginServices service;

    public LoginRepositoryRepository(){
        ServicesFactory servicesFactory = new ServicesFactory();
        service = (ILoginServices) servicesFactory.getInstance(ILoginServices.class);
    }

    @Override
    public User login(String user, String password) throws RetrofitError {
        return service.login(user, password);
    }
}
