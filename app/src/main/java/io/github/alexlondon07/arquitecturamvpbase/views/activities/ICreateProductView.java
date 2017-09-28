package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import io.github.alexlondon07.arquitecturamvpbase.model.Product;
import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 9/19/17.
 */

public interface ICreateProductView extends IBaseView {

    void responseCreateProduct(boolean isCreated);

    void showAlertDialog(int msg);

    void showToast(int msg);

}
