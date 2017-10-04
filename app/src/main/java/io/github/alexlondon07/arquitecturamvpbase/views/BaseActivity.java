package io.github.alexlondon07.arquitecturamvpbase.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.alexlondon07.arquitecturamvpbase.helper.IValidateInternet;
import io.github.alexlondon07.arquitecturamvpbase.helper.ShowAlertDialog;
import io.github.alexlondon07.arquitecturamvpbase.helper.ValidateInternet;
import io.github.alexlondon07.arquitecturamvpbase.presenter.BasePresenter;

/**
 * Created by alexlondon07 on 9/16/17.
 */

public class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements  IBaseView{

    private IValidateInternet validateInternet;
    private ProgressDialog progressDialog;
    private T presenter;
    private ShowAlertDialog showAlertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateInternet = new ValidateInternet( BaseActivity.this );
        this.showAlertDialog =  new ShowAlertDialog(this);
    }


    @Override
    public void showProgress(int message) {
        progressDialog.setMessage(getResources().getString(message));
        progressDialog.show();
    }

    public void createProgresDialog (){
        this.progressDialog = new ProgressDialog(this);
    }

    @Override
    public void hidePorgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msj) {

    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    public IValidateInternet getValidateInternet() {
        return validateInternet;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public ShowAlertDialog getShowAlertDialog() {
        return showAlertDialog;
    }
}
