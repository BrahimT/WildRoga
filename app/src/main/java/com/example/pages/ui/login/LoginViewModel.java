package com.example.pages.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;
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

    //add username/email to the return
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

//    TODO Can probably be removed - Matt
//    public void loginDataChanged(String email, char[] password) {
//
//        int passwordMessage = isPasswordValid(password);
//
//        if (!isEmailNameValid(email)) {
//            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
//        } else if (passwordMessage != R.string.valid_password) {
//            loginFormState.setValue(new LoginFormState(null, passwordMessage));
//        } else {
//            loginFormState.setValue(new LoginFormState(true));
//        }
//    }

    public void registrationDataChanged(String name, String email, char[] password, char[] passwordConfirm) {
        int passwordMessage = isPasswordValid(password);
        int confirmMessage = isPasswordConfirmed(password, passwordConfirm);

        if (!isNameValid(name)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_name, null, null, null));
        } else if (!isEmailNameValid(email)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_email, null, null));
        } else if (passwordMessage != R.string.valid_password) {
            loginFormState.setValue(new LoginFormState(null, null, passwordMessage, null));
        } else if (confirmMessage != R.string.valid_confirm_password) {
            loginFormState.setValue(new LoginFormState(null, null, null, confirmMessage));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isNameValid(String name) {
        return name != null;
    }

    // TODO does this need changing? A placeholder username validation check
    private boolean isEmailNameValid(String username) {
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
        if(password == null || PasswordUtilities.trim(password).length < 9) return R.string.invalid_password_length;

        if(!passwordHasCapitalLetter(password)) return R.string.invalid_password_capital;

        if(!passwordHasNumber(password)) return R.string.invalid_password_number;

        return R.string.valid_password;
    }

    private int isPasswordConfirmed(char[] password, char[] confirmPassword) {
        if (!passwordMatches(password, confirmPassword)) return R.string.invalid_password_confirm;

        return R.string.valid_confirm_password;
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

    // Not a proper way to check, shouldn't convert char array back to string due to security purposes, this is simply for testing
    // TODO write checking method without converting to string
    private boolean passwordMatches(char[] password, char[] confirmPassword) {
        String p1 = PasswordUtilities.charArrayToString(password);
        String p2 = PasswordUtilities.charArrayToString(confirmPassword);

        if (p1.matches(p2)) {
            return true;
        }

        return false;
    }

}