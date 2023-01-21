package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.DTOs.AccountDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.AccountType;
import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.AccountTypeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountManagementService {
    private AccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;
    private List<String> errors;

    public AccountManagementService(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository) {
        this.accountRepository = accountRepository;
        this.errors = new ArrayList<String>();
        this.accountTypeRepository = accountTypeRepository;
    }

    public Account OpenAccount(AccountDTO dto) {
        User currentUser = SignInService.getCurrentUser();
        if (currentUser == null) {
            errors.add("Nie jesteś zalogowany!");
            return null;
        }
        errors = dto.ValidateAll();
        if (!errors.isEmpty()) return null;
        String newAccountId = accountRepository.CreateAccount(dto);
        if (newAccountId == null) {
            errors.add("Nie udało się utworzyć konta! Bład serwera.");
            return null;
        }
        Account newAccount = accountRepository.GetAccountById(newAccountId);
        if (newAccount == null) {
            errors.add("Nie udało się utworzyć konta! Bład serwera.");
            return null;
        }
        return newAccount;
    }

    public boolean CloseAccount(String accountId) {
        Account accountToClose = accountRepository.GetAccountById(accountId);
        if (accountToClose == null) {
            errors.add("Takie konto nie istnieje!");
            return false;
        }
        return accountRepository.SoftDeleteAccount(accountId);
    }

    public boolean ChangeAccountType(String accountId, Integer newAccountTypeId) {
        Account account = accountRepository.GetAccountById(accountId);
        if (account == null) {
            errors.add("Podane konto nie istnieje!");
            return false;
        }
        AccountType accountType = accountTypeRepository.GetAccountTypeById(newAccountTypeId);
        if (accountType == null) {
            errors.add("Podany typ konta nie istnieje!");
            return false;
        }
        AccountDTO dto = new AccountDTO(account);
        errors = dto.ValidateAll();
        if (!errors.isEmpty()) {
            return false;
        }
        return accountRepository.UpdateAccount(dto, accountId);
    }

    public boolean SetLimit(String accountId, BigDecimal newLimit) {
        Account account = accountRepository.GetAccountById(accountId);
        if (account == null) {
            errors.add("Podane konto nie istnieje!");
            return false;
        }
        AccountType accountType = account.getAccountType();
        if (newLimit.compareTo(accountType.getMaxTransferLimit()) > 0) {
            errors.add("Limit za wysoki, dopuszczalny limit dla twojego typu konta wynosi " + accountType.getMaxTransferLimit().toString());
            return false;
        }
        AccountDTO dto = new AccountDTO(account);
        dto.setTransferLimit(newLimit);
        errors = dto.ValidateAll();
        if (!errors.isEmpty()) return false;
        return accountRepository.UpdateAccount(dto, accountId);
    }
}
