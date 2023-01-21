package com.pppfkp.javabank.Data.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT_TYPES")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountTypeId", nullable = false)
    private Integer id;

    @Column(name = "AccountName", nullable = false)
    private String accountName;

    @Column(name = "TransferComission", columnDefinition = "tinyint")
    private Short transferComission;

    @Column(name = "MaxAllowedAge", columnDefinition = "tinyint")
    private Short maxAllowedAge;

    @Column(name = "MaxTransferLimit", precision = 9, scale = 2)
    private BigDecimal maxTransferLimit;

    @OneToMany(mappedBy = "accountType")
    private Set<Account> accounts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Short getTransferComission() {
        return transferComission;
    }

    public void setTransferComission(Short transferComission) {
        this.transferComission = transferComission;
    }

    public Short getMaxAllowedAge() {
        return maxAllowedAge;
    }

    public void setMaxAllowedAge(Short maxAllowedAge) {
        this.maxAllowedAge = maxAllowedAge;
    }

    public BigDecimal getMaxTransferLimit() {
        return maxTransferLimit;
    }

    public void setMaxTransferLimit(BigDecimal maxTransferLimit) {
        this.maxTransferLimit = maxTransferLimit;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

}