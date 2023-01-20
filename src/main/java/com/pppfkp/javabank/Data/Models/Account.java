package com.pppfkp.javabank.Data.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @Column(name = "AccountId", nullable = false, length = 26)
    private String id;

    @Column(name = "TransferLimit", precision = 9, scale = 2)
    private BigDecimal transferLimit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "AccountTypeId", nullable = false)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(name = "SoftDeleted")
    private Boolean softDeleted;

    @OneToMany(mappedBy = "defaultAccount")
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipientAccount")
    private Set<Transaction> transactionsTo = new LinkedHashSet<>();

    @OneToMany(mappedBy = "senderAccount")
    private Set<Transaction> transactionsFrom = new LinkedHashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(Boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(Set<Transaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }

    public Set<Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(Set<Transaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

}