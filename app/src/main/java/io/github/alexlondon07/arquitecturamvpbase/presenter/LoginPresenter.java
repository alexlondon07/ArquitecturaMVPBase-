package io.github.alexlondon07.arquitecturamvpbase.presenter;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.User;
import io.github.alexlondon07.arquitecturamvpbase.repository.ILoginRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.activities.ILoginView;
import retrofit.RetrofitError;

/**
 * Created by alexlondon07 on 10/14/17.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {

    ILoginRepository loginRepository;

    public LoginPresenter(ILoginRepository LoginRepository) {
        this.loginRepository = LoginRepository;
    }


    public void loginPresenter(String email, String password){
        if(getValidateInternet().isConnected()){
            createThreadLogin(email, password);
        }else{
            getView().showAlertDialogInternet(R.string.error, R.string.validate_internet);
        }
    }

    private void createThreadLogin(final String email, final String password) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loginServices(email, password);
            }
        });
        thread.start();
    }

    private void loginServices(String email, String password) {
        try {
            User login = loginRepository.login(email, password);
            getView().showProfile(login);
        }catch (RetrofitError retrofitError){
            getView().showAlertError(R.string.error, retrofitError.getMessage());
        }
    }

}
