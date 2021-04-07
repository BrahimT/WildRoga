package com.example.pages.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
//    @Nullable
//    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer passwordConfirmError;
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer emailError;
    private boolean isDataValid;

//    LoginFormState(@Nullable Integer emailError, @Nullable Integer passwordError) {
//        this.emailError = emailError;
//        this.passwordError = passwordError;
//        this.isDataValid = false;
//    }

    //Testing some things, may not work in which case delete below code
    // TODO error messaging and checking for registration page using the same structure as login
    LoginFormState(@Nullable Integer nameError, @Nullable Integer emailError, @Nullable Integer passwordError, @Nullable Integer passwordConfirmError) {
        this.nameError = nameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.passwordConfirmError = passwordConfirmError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.nameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.passwordConfirmError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getNameError() { return nameError; }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getPasswordConfirmError() { return passwordConfirmError; }

    boolean isDataValid() {
        return isDataValid;
    }
}