package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 9/23/17.
 */

public interface IDetailProductView  extends IBaseView {

    void showAlertDialog(int error);

    void showToast(int msg);

    void showToast(String msg);

    void showAlertDialogError(int error);
}
