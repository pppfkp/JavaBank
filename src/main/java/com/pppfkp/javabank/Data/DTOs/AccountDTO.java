package com.pppfkp.javabank.Data.DTOs;

import com.pppfkp.javabank.Data.Models.AccountType;

import java.math.BigDecimal;

public class AccountDTO {
    private BigDecimal transferLimit;
    private AccountType accountType;

    public AccountDTO(BigDecimal transferLimit, AccountType accountType) {
        this.transferLimit = transferLimit;
        this.accountType = accountType;
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
}
