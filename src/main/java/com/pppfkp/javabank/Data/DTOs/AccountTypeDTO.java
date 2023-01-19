package com.pppfkp.javabank.Data.DTOs;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public class AccountTypeDTO {
    private String accountName;

    private Short transferComission;

    private Short maxAllowedAge;

    private BigDecimal maxTransferLimit;

    public AccountTypeDTO(String accountName, Short transferComission, Short maxAllowedAge, BigDecimal maxTransferLimit) {
        this.accountName = accountName;
        this.transferComission = transferComission;
        this.maxAllowedAge = maxAllowedAge;
        this.maxTransferLimit = maxTransferLimit;
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
}
