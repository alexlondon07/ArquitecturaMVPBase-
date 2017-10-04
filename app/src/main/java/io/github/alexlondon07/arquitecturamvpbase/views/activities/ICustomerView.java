package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.model.Customer;
import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 10/3/17.
 */

public interface ICustomerView extends IBaseView {

    void showCustomerList(ArrayList<Customer> customerArrayList);

    void showAlertDialogInternet(int title, int message);

    void showAlertError(int title, int message);

    void showAlertError(int title, String message);
}
