package com.example.tools;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.pages.ui.login.LoginActivity;

import java.io.IOException;

public class AccountHelperMethods {

    //Interactions with accountManager are asynchronous, so use java.concurrent for calls
    private AccountManager accountManager;

    private final String ACCOUNT_TYPE = "WildRoga";
    private final String AUTH_TOKEN_TYPE = "WildRoga";

    public AccountHelperMethods(Context context){
        accountManager = AccountManager.get(context);
    }

    public void addAccount(Context context){
        AccountManagerFuture<Bundle> amf = accountManager.addAccount(this.ACCOUNT_TYPE, this.AUTH_TOKEN_TYPE, null, this.createOptionsBundle(context), null, null, null);
    }

    public Bundle createOptionsBundle(Context context){
        //https://stackoverflow.com/questions/50628796/accountmanager-when-to-set-result consulted

        Intent intent = new Intent(context, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);

        return bundle;
    }
    public void authenticate(Account account){
//TODO move this code
//        If we use this line, we have to include disclosure/privacy policy to users (overall I think it's worth it, I feel it's more secure and will perform better)
//        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
//
//        if (accounts.length == 0 ){
//            //what to do when no accounts present?
//        }
//
//        if (accounts.length > 1){
//            //provide dialog for user to select account
//        }

          AccountManagerFuture<Bundle> accountManagerFuture = this.accountManager.getAuthToken(account, "Login", null, new LoginActivity(), (result) -> {
              try {
                  Bundle bundle = result.getResult();
              } catch (AuthenticatorException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              } catch (OperationCanceledException e) {
                  e.printStackTrace();
              }
          }, null);
    }

    /*
        Stripe test cards: https://stripe.com/docs/testing#cards
     */
}
