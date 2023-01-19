package com.pppfkp.javabank.Data.DTOs;

import com.pppfkp.javabank.Data.Models.Account;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDTO {
    private Instant transactionDate;

    private Account recipientAccount;

    private Account senderAccount;

    private BigDecimal ammount;

    private String title;

    public TransactionDTO(Instant transactionDate, Account recipientAccount, Account senderAccount, BigDecimal ammount, String title) {
        this.transactionDate = transactionDate;
        this.recipientAccount = recipientAccount;
        this.senderAccount = senderAccount;
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
}
