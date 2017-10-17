package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import io.github.alexlondon07.arquitecturamvpbase.model.User;
import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 10/16/17.
 */

public interface ILoginView extends IBaseView {

    void showProfile(User login);

    void showAlertDialogInternet(int error, int validate_internet);

    void showAlertError(int title, int message);

    void showAlertError(int title, String message);
}
