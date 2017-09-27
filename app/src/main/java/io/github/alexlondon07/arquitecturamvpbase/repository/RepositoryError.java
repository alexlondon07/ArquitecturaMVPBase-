package io.github.alexlondon07.arquitecturamvpbase.repository;

/**
 * Created by alexlondon07 on 9/26/17.
 */

public class RepositoryError extends Exception {
    private int idError;

    public RepositoryError(String message) {
        super(message);
    }

    public int getIdError() {
        return idError;
    }

    public void setIdError(int idError) {
        this.idError = idError;
    }
}
