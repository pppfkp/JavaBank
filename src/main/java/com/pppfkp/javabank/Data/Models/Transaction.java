package com.pppfkp.javabank.Data.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "TransactionId", nullable = false)
    private String id;

    @Column(name = "TransactionDate", nullable = false)
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RecipientAccountId")
    private Account recipientAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SenderAccountId")
    private Account senderAccount;

    @Column(name = "SenderAccountPostTransactionBalance", nullable = false, precision = 9, scale = 2)
    private BigDecimal senderAccountPostTransactionBalance;

    @Column(name = "RecipientAccountPostTransactionBalance", nullable = false, precision = 9, scale = 2)
    private BigDecimal recipientAccountPostTransactionBalance;

    @Column(name = "Ammount", nullable = false, precision = 9, scale = 2)
    private BigDecimal ammount;

    @Column(name = "Title", nullable = false, length = 100)
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
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

    public BigDecimal getSenderAccountPostTransactionBalance() {
        return senderAccountPostTransactionBalance;
    }

    public void setSenderAccountPostTransactionBalance(BigDecimal senderAccountPostTransactionBalance) {
        this.senderAccountPostTransactionBalance = senderAccountPostTransactionBalance;
    }

    public BigDecimal getRecipientAccountPostTransactionBalance() {
        return recipientAccountPostTransactionBalance;
    }

    public void setRecipientAccountPostTransactionBalance(BigDecimal recipientAccountPostTransactionBalance) {
        this.recipientAccountPostTransactionBalance = recipientAccountPostTransactionBalance;
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