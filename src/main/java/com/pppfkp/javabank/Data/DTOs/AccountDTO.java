package com.pppfkp.javabank.Data.DTOs;

import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.AccountType;
import com.pppfkp.javabank.Data.Models.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountDTO implements IMapableTo<Account> {
    private BigDecimal transferLimit;
    private BigDecimal balance;
    private AccountType accountType;
    private User user;

    public AccountDTO(BigDecimal transferLimit, AccountType accountType, User user) {
        this.transferLimit = transferLimit;
        this.accountType = accountType;
        this.user = user;
    }

    public AccountDTO(Account account) {
        this.accountType = account.getAccountType();
        this.transferLimit = account.getTransferLimit();
        this.user = account.getUser();
        this.balance = account.getBalance();
    }


    public BigDecimal getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(BigDecimal transferLimit) {
        this.transferLimit = transferLimit;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public Account MapToEntityTypeNewRecord() {
        if(!ValidateAll().isEmpty()) return null;
        Account account = new Account();
        setUpdatableFields(account);
        account.setId(generateAccountNumber());
        account.setUser(this.user);
        account.setBalance(new BigDecimal(0));
        account.setSoftDeleted(false);
        return account;
    }

    @Override
    public Account MapToEntityTypeUpdateRecord(Account recordToUpdate) {
        if (!ValidateUpdatable().isEmpty()) return  null;
        setUpdatableFields(recordToUpdate);
        return recordToUpdate;
    }

    @Override
    public List<String> ValidateUpdatable() {
        List<String> errorList = new ArrayList<String>();
        return errorList;
    }

    @Override
    public List<String> ValidateAll() {
        List<String> errorList = new ArrayList<String>();
        return errorList;
    }
    private void setUpdatableFields(Account account) {
        account.setTransferLimit(this.transferLimit);
        account.setAccountType(this.accountType);
        account.setBalance(this.balance);
    }
   private String generateAccountNumber() {
        Random rand = new Random();

        long x = (long)(rand.nextDouble()*10000000000000000L);

        String s = String.valueOf(sumOfDigits(x)) + "20011234" + String.format("%016d", x);
       System.out.println(s);
        return s;
   }
   private int sumOfDigits(long number) {
       long sum = 0;
       while (number > 0) {
           long lastDigit = number % 10;
           sum += lastDigit;
           number /= 10;
       }
       return Math.toIntExact(sum % 99);
   }
}
