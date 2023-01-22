package com.pppfkp.javabank.Data.DTOs;

import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.Transaction;
import com.pppfkp.javabank.Repositories.AccountRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDTO implements IMapableTo<Transaction> {
    private AccountRepository accountRepository;
    private Instant transactionDate;

    private Account recipientAccount;

    private Account senderAccount;

    private BigDecimal ammount;

    private String title;

    public TransactionDTO(Account recipientAccount, Account senderAccount, BigDecimal ammount, String title) {
        accountRepository = new AccountRepository(HibernateConnectUtility.getSessionFactory());
        this.transactionDate = null;
        this.recipientAccount = recipientAccount;
        this.senderAccount = senderAccount;
        this.ammount = ammount;
        this.title = title;
    }

    public TransactionDTO(String recipientAccountId, String senderAccountId, BigDecimal ammount, String title) {
        accountRepository = new AccountRepository(HibernateConnectUtility.getSessionFactory());
        this.transactionDate = null;
        this.recipientAccount = accountRepository.GetAccountById(recipientAccountId);
        this.senderAccount = accountRepository.GetAccountById(senderAccountId);
        this.ammount = ammount;
        this.title = title;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Account getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(Account recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Transaction MapToEntityTypeNewRecord() {
        if (!ValidateAll().isEmpty()) return null;
        Transaction transaction = new Transaction();
        transaction.setAmmount(this.ammount);
        transaction.setTitle(this.title);
        transaction.setRecipientAccount(this.recipientAccount);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTitle(this.title);
        transaction.setSenderAccount(this.senderAccount);
        transaction.setRecipientAccountPostTransactionBalance(recipientAccount.getBalance().add(this.ammount));
        transaction.setSenderAccountPostTransactionBalance(senderAccount.getBalance().subtract(this.ammount));
        //transaction.setId(UUID.randomUUID());
        return transaction;
    }

    @Override
    public Transaction MapToEntityTypeUpdateRecord(Transaction recordToUpdate) {
        return null;
    }

    @Override
    public List<String> ValidateUpdatable() {
        List<String> errorList = new ArrayList<String>();
        return errorList;
    }

    @Override
    public List<String> ValidateAll() {
        List<String> errorList = new ArrayList<String>();
        if (recipientAccount.getId() == senderAccount.getId()) {
            errorList.add("Konta adresata i odbiorcy nie mogą być takie same!");
        }
        if (senderAccount.getBalance().compareTo(ammount) < 0) {
            errorList.add("Nie masz wystarczających środków na koncie.");
        }
        return errorList;
    }
}
