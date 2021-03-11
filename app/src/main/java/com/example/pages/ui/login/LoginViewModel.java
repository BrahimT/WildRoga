package com.example.pages.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Patterns;

import com.example.pages.loginData.LoginRepository;
import com.example.pages.loginData.Result;
import com.example.model.LoggedInUser;
import com.example.myapplication.R;
import com.example.tools.PasswordUtilities;
import com.example.tools.SessionManager;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, char[] password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, char[] password) {

        int passwordMessage = isPasswordValid(password);

        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (passwordMessage != R.string.valid_password) {
            loginFormState.setValue(new LoginFormState(null, passwordMessage));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private int isPasswordValid(char[] password) {
        if(password == null || PasswordUtilities.trim(password).length < 9) return R.string.invalid_password_number;

        if(!passwordHasCapitalLetter(password)) return R.string.invalid_password_capital;

        if(!passwordHasNumber(password)) return R.string.invalid_password_number;

        return R.string.valid_password;
    }

    private boolean passwordHasNumber(char[] password){
        for(char c: password){
            if (Character.isDigit(c)) return true;
        }

        return false;
    }

    private boolean passwordHasCapitalLetter(char[] password){
        for(char c: password){
            if (Character.isUpperCase(c)) return true;
        }

        return false;
    }
}