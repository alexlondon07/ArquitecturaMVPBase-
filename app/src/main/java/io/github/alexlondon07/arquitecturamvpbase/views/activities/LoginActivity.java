package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.model.User;
import io.github.alexlondon07.arquitecturamvpbase.presenter.LoginPresenter;
import io.github.alexlondon07.arquitecturamvpbase.repository.LoginRepositoryRepository;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, TextWatcher {

    private EditText email, password;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPresenter(new LoginPresenter(new LoginRepositoryRepository()));
        getPresenter().inject(this, getValidateInternet());
        init();
    }

    private void login (String email, String password){
        getPresenter().loginPresenter(email, password);
    }

    private void init() {
        email = (EditText) findViewById(R.id.login_ediText_email);
        email.addTextChangedListener(this);

        password = (EditText) findViewById(R.id.login_ediText_password);
        password.addTextChangedListener(this);

        loginButton = (Button) findViewById(R.id.btn_login);
        disableButton(loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public void showProfile(final User login) {
        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAlertDialogInternet(int title, int validate_internet) {
        showAlertDialog(title, validate_internet);
    }

    @Override
    public void showAlertError(int title, int message) {
        showAlertDialog(title, message);
    }

    @Override
    public void showAlertError(int title, String message) {
    }

    public void showAlertDialog(final int title, final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(title, message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, R.string.option_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }

    public void validateFields(){
        if( email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            disableButton(loginButton);
        }else{
            enableButton(loginButton);
        }
    }

    public void enableButton(Button button){
        button.setEnabled(true);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));

    }

    public void disableButton(Button button){
        button.setEnabled(false);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        validateFields();
    }
}
