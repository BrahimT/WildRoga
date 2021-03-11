package com.example.pages.loginData;

import com.example.model.LoggedInUser;
import com.example.tools.PasswordUtilities;
import com.example.tools.SessionManager;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> createAccount(String username, char[] password){
        //TODO verify no user with name already exists in db
        //TODO write username and password hash to db
        //TODO call login function and return the Result from that method call
        return null;
    }

    public Result<LoggedInUser> login(String username, char[] password){
        try{
            if(username.contentEquals("test") && PasswordUtilities.verifyPassword(PasswordUtilities.hashPassword(new char[]{'T', 'e', 's', 't', 'i', 'n', 'g', '1', '2', '3'}, PasswordUtilities.getSalt())
                    ,password)){

                return new Result.Success<>(new LoggedInUser(java.util.UUID.randomUUID().toString(), username));
            }
        }catch(Exception e){
            return new Result.Error(new IOException("Error logging in", e));
        }


        return new Result.Error(new IOException("Error logging in"));
    }

//    public Result<LoggedInUser> login(String username, char[] password) {
//        //TODO verify user matches db record
//        //TODO create and return user object if match exists in db
//        try {
//
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
//            return new Result.Success<>(fakeUser);
//        } catch (Exception e) {
//            return new Result.Error(new IOException("Error logging in", e));
//        }
//    }

//    public Result<LoggedInUser> createUser(String username, byte[] passwordHash){
//
//    }

    public void logout() {
        // TODO: revoke authentication
    }
}