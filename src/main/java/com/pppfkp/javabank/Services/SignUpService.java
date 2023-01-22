package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.DTOs.AccountDTO;
import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.AccountTypeRepository;
import com.pppfkp.javabank.Repositories.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SignUpService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;
    private List<String> errors;

    public SignUpService(UserRepository userRepository, AccountRepository accountRepository, AccountTypeRepository accountTypeRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository =accountTypeRepository;
        this.errors = new ArrayList<String>();
    }

    public User SignUp(UserDTO dto) {
        errors = dto.ValidateAll();
        if (!errors.isEmpty()) return null;
        Integer newUserId = userRepository.CreateUser(dto);
        if (newUserId == null) return null;
        User newUser = userRepository.GetUserById(newUserId);
        AccountDTO accountDTO = new AccountDTO(new BigDecimal(1000), accountTypeRepository.GetAccountTypeById(2), newUser);
        String newAccountId =  accountRepository.CreateAccount(accountDTO);
        if (newAccountId == null) {
            return null;
        }
        Account newAccount = accountRepository.GetAccountById(newAccountId);
        if (newAccount == null) return null;
        newUser.setDefaultAccount(newAccount);
        UserDTO userWithAccount = new UserDTO(newUser);
        userRepository.UpdateUser(userWithAccount, newUserId);
        return userRepository.GetUserById(newUserId);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
