package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 10/3/17.
 */

public interface ICreateCustomerView extends IBaseView {

    void responseCreateCustomer(boolean isCreated);

    void showAlertDialog(int msg);

    void showToast(int msg);

    void showAlertError(int title, int message);

    void showAlertError(int title, String message);
}
