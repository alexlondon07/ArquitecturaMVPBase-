package io.github.alexlondon07.arquitecturamvpbase.views.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import io.github.alexlondon07.arquitecturamvpbase.helper.IValidateInternet;
import io.github.alexlondon07.arquitecturamvpbase.presenter.BasePresenter;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.IBaseView;

/**
 * Created by alexlondon07 on 10/14/17.
 */

public class BaseFragment<T extends BasePresenter> extends Fragment implements IBaseView {

    private BaseActivity baseActivity;
    private IValidateInternet validateInternet;
    private T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        validateInternet = baseActivity.getValidateInternet();
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public IValidateInternet getValidateInternet() {
        return validateInternet;
    }

    public void setValidateInternet(IValidateInternet validateInternet) {
        this.validateInternet = validateInternet;
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(int message) {
        //baseActivity.showProgress(message);
    }

    @Override
    public void hidePorgress() {
        //baseActivity.hidePorgress();
    }

    @Override
    public void showMessage(String msj) {
        //baseActivity.showMessage(msj);
    }
}
