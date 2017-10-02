package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import java.util.ArrayList;

import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public interface IProductView extends IBaseView{

    void showProductList(ArrayList<Product> productArrayList);

    void showAlertDialogInternet(int error, int validate_internet);

    void showAlertError(int error, int error2);

    void showAlertError(int error, String error2);
}
