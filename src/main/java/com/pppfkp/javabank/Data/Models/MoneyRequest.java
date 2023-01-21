package com.pppfkp.javabank.Data.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "MONEY_REQUESTS")
public class MoneyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestId", nullable = false)
    private UUID id;

    @Column(name = "RequestDate", nullable = false)
    private Instant requestDate;

    @Column(name = "WasProccessed")
    private Boolean wasProccessed;

    @Column(name = "WasAccepted")
    private Boolean wasAccepted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RequestSenderUserId", nullable = false)
    private User requestSenderUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RequestRecipientUserId", nullable = false)
    private User requestRecipientUser;

    @Column(name = "Ammount", nullable = false, precision = 9, scale = 2)
    private BigDecimal ammount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Boolean getWasProccessed() {
        return wasProccessed;
    }

    public void setWasProccessed(Boolean wasProccessed) {
        this.wasProccessed = wasProccessed;
    }

    public Boolean getWasAccepted() {
        return wasAccepted;
    }

    public void setWasAccepted(Boolean wasAccepted) {
        this.wasAccepted = wasAccepted;
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