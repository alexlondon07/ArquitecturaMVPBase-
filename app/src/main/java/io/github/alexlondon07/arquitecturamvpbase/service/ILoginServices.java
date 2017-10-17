package io.github.alexlondon07.arquitecturamvpbase.service;

import io.github.alexlondon07.arquitecturamvpbase.model.User;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alexlondon07 on 10/16/17.
 */

public interface ILoginServices {

    @GET("/user/auth")
    User login(@Query("email") String email, @Query("password") String password);
}
