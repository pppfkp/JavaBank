package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class SignUpService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private List<String> errors;

    public SignUpService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.errors = new ArrayList<String>();
    }

    public User SignUp(UserDTO dto) {
        errors = dto.ValidateAll();
        if (!errors.isEmpty()) return null;
        Integer newUserId = userRepository.CreateUser(dto);
        if (newUserId == null) return null;
        //TODO create default account
        return userRepository.GetUserById(newUserId);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
