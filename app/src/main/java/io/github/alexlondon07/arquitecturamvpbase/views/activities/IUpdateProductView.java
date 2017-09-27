package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 9/26/17.
 */

public interface IUpdateProductView  extends IBaseView{

    void responseUpdateProduct(boolean isUpdated);

    void showAlertDialog(int error);

    void showToast(int msg);

    void showToast(String msg);

    void showAlertDialogError(int error);
}
