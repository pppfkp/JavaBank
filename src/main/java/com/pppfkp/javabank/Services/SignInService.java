package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class SignInService {
    private UserRepository userRepository;
    private List<String> errors;
    private static User currentUser;

    public SignInService(UserRepository userRepository) {
        this.userRepository = userRepository;
        errors = new ArrayList<>();
    }

    public boolean SignIn(String login, String password) {
        SignOut();
        errors.clear();
        boolean authenticationResult =  userRepository.AuthenticateUser(login, password);
        if (authenticationResult) {
            currentUser = userRepository.getUserByLogin(login);
            CurrentUserAppState.Refresh();
            return true;
        } else {
            errors.add("Logowanie nie powiodło się! Błędne hasło lub login.");
            return  false;
        }
    }

    public void SignOut() {
        errors.clear();
        currentUser = null;
        CurrentUserAppState.Clear();
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public List<String> getErrors() {
        return errors;
    }
}
