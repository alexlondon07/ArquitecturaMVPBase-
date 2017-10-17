package io.github.alexlondon07.arquitecturamvpbase.repository;

import io.github.alexlondon07.arquitecturamvpbase.model.User;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 10/14/17.
 */

public interface ILoginRepository {

    public User login(String email, String password) throws RetrofitError;
}
