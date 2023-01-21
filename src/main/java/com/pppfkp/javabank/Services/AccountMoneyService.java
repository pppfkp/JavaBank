package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.DTOs.AccountDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountMoneyService {
    private AccountRepository accountRepository;
    private List<String> errors;



    public AccountMoneyService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        errors = new ArrayList<>();
    }

    public BigDecimal WithdrawMoneyFromAccount(BigDecimal ammountToWithdraw, String accountId) {
        //TODO use ChangeBalanceFunction from account repository

        Account account = accountRepository.GetAccountById(accountId);
        if (account == null) {
            errors.add("Podane konto nie istnieje!");
            return null;
        }
        if (ammountToWithdraw.compareTo(new BigDecimal(0)) < 0) {
            errors.add("Kwota musi byc wieksza od zera!");
            return null;
        }
        BigDecimal currentAmmount = account.getBalance();
        if (ammountToWithdraw.compareTo(currentAmmount) > 0) {
            errors.add("Nie posiadasz wystarczających środków na koncie!");
            return null;
        }
        AccountDTO dto = new AccountDTO(account);
        BigDecimal newBalance = currentAmmount.subtract(ammountToWithdraw);
        dto.setBalance(newBalance);
        errors = dto.ValidateAll();
        if (!errors.isEmpty()) {
            return null;
        }
        if (accountRepository.UpdateAccount(dto, accountId)) {
            return newBalance;
        } else {
            errors.add("Błąd serwera!");
            return  null;
        }
    }
    public BigDecimal DepositMoneyOnAccount(BigDecimal ammountToDeposit, String accountId) {
        Account account = accountRepository.GetAccountById(accountId);
        if (account == null) {
            errors.add("Podane konto nie istnieje!");
            return null;
        }
        if (ammountToDeposit.compareTo(new BigDecimal(0)) < 0) {
            errors.add("Kwota musi byc wieksza od zera!");
            return null;
        }
        BigDecimal currentAmmount = account.getBalance();
        AccountDTO dto = new AccountDTO(account);
        BigDecimal newBalance = currentAmmount.add(ammountToDeposit);
        dto.setBalance(newBalance);
        errors = dto.ValidateAll();
        if (!errors.isEmpty()) {
            return null;
        }
        if (accountRepository.UpdateAccount(dto, accountId)) {
            return newBalance;
        } else {
            errors.add("Błąd serwera!");
            return  null;
        }
    }

    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
