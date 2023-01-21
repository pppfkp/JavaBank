package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserAccountManagementService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private List<String> errors;

    public UserAccountManagementService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.errors = new ArrayList<>();
    }

    public boolean CloseUserAccount(Integer id)
    {
        User userToClose = userRepository.GetUserById(id);
        if (userToClose == null) {
            errors.add("Taki użytkownik nie istnieje!");
            return false;
        }//make sure that the accounts are deleted as well
        if (userRepository.SoftDeleteUser(id)) {
            List<Account> accountsToDelete = accountRepository.GetAllAccountsByUserId(id);
            if (accountsToDelete == null) return true;
            for (var account : accountsToDelete) {
                closeAccount(account.getId());
            }
            return true;
        } else {
            return false;
        }
    }
    public boolean UpdateUserData(UserDTO dto, Integer id) {
        User user = userRepository.GetUserById(id);
        if (user == null) {
            errors.add("Taki użytkownik nie istnieje");
            return false;
        }
        if (!userRepository.UpdateUser(dto, id)) {
            errors.add("Błąd serwera");
            return false;
        }
        return true;
    }
    private boolean closeAccount(String accountId) {
        Account accountToClose = accountRepository.GetAccountById(accountId);
        if (accountToClose == null) {
            return false;
        }
        return accountRepository.SoftDeleteAccount(accountId);
    }
}
