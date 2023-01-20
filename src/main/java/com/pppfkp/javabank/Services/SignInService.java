package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class SignInService {
    private UserRepository userRepository;
    private static User currentUser;

    public SignInService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean SignIn(String login, String password) {
        boolean authenticationResult =  userRepository.AuthenticateUser(login, password);
        if (authenticationResult) {
            currentUser = userRepository.getUserByLogin(login);
            return true;
        } else {
            return  false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
