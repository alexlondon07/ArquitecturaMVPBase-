package io.github.alexlondon07.arquitecturamvpbase.repository;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alexlondon07 on 9/26/17.
 */

public class MapperError {

    public static RepositoryError convertRetrofitErrorToRepositoryError(RetrofitError retrofitError) {
        RepositoryError repositoryError;

        repositoryError = validateTimeOutToGetRepositoryError(retrofitError);
        if (repositoryError != null) {
            return repositoryError;
        }

        repositoryError = validateTheBodyToGetRepositoryError(retrofitError);
        if (repositoryError != null) {
            return repositoryError;
        }

        return getDefaultError();
    }

    private static RepositoryError validateTheBodyToGetRepositoryError(RetrofitError retrofitError) {
        RepositoryError repositoryError = null;
        Response response = retrofitError.getResponse();
        if (response != null) {
            int errorId = response.getStatus();
            String mensaje = Constants.DEFAULT_ERROR;
            if (errorId == Constants.UNAUTHORIZED_ERROR_CODE || errorId == Constants.NOT_FOUND_ERROR_CODE) {
               /* try {
                    ErrorDTO errorDTO = (ErrorDTO) retrofitError.getBodyAs(ErrorDTO.class);
                    if (errorDTO != null) {
                        mensaje = errorDTO.getMessage();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }*/
            }
            repositoryError = new RepositoryError(mensaje);
            repositoryError.setIdError(errorId);
        }
        return repositoryError;
    }

    private static RepositoryError validateTimeOutToGetRepositoryError(RetrofitError retrofitError) {
        if (retrofitError.getCause() != null && retrofitError.getCause() instanceof SocketTimeoutException
                || retrofitError.getCause() instanceof InterruptedIOException) {
            RepositoryError repositoryError = new RepositoryError(Constants.REQUEST_TIMEOUT_ERROR_MESSAGE);
            repositoryError.setIdError(Constants.DEFAULT_ERROR_CODE);
            return repositoryError;
        }
        return null;
    }

    public static RepositoryError getDefaultError() {
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAULT_ERROR);
        repositoryError.setIdError(Constants.DEFAULT_ERROR_CODE);
        return repositoryError;
    }

}
