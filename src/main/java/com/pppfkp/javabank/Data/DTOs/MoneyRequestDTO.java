package com.pppfkp.javabank.Data.DTOs;
import com.pppfkp.javabank.Data.Models.*;

import java.math.BigDecimal;
import java.time.Instant;

public class MoneyRequestDTO {

    private User requestSenderUser;

    private User requestRecipientUser;

    private BigDecimal ammount;

    public MoneyRequestDTO(User requestSenderUser, User requestRecipientUser, BigDecimal ammount) {
        this.requestSenderUser = requestSenderUser;
        this.requestRecipientUser = requestRecipientUser;
        this.ammount = ammount;
    }

    public User getRequestSenderUser() {
        return requestSenderUser;
    }

    public void setRequestSenderUser(User requestSenderUser) {
        this.requestSenderUser = requestSenderUser;
    }

    public User getRequestRecipientUser() {
        return requestRecipientUser;
    }

    public void setRequestRecipientUser(User requestRecipientUser) {
        this.requestRecipientUser = requestRecipientUser;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }
}
