package com.pppfkp.javabank.Data.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USERS", indexes = {
        @Index(name = "UQ__USERS__85FB4E385AFC2100", columnList = "PhoneNumber", unique = true),
        @Index(name = "UQ__USERS__7F8E8D5EB428BDA7", columnList = "UserLogin", unique = true),
        @Index(name = "UQ__USERS__48A5F717DA707A81", columnList = "Pesel", unique = true),
        @Index(name = "UQ__USERS__A9D10534C8D6C4BC", columnList = "Email", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false)
    private Integer id;

    @Column(name = "FirstName", nullable = false, length = 150)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 150)
    private String lastName;

    @Column(name = "UserLogin", nullable = false, length = 50)
    private String userLogin;

    @Column(name = "PasswordHash", nullable = false)
    private byte[] passwordHash;

    @Column(name = "Salt", nullable = false)
    private UUID salt;

    @Column(name = "PhoneNumber", nullable = false, length = 9)
    private String phoneNumber;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Pesel", nullable = false, length = 11)
    private String pesel;

    @Column(name = "Birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "UsesPhoneTransfer")
    private Boolean usesPhoneTransfer;

    @Column(name = "AllowsMoneyRequests")
    private Boolean allowsMoneyRequests;

    @Column(name = "CityOfBirth", nullable = false)
    private String cityOfBirth;

    @Column(name = "AddressCity", nullable = false)
    private String addressCity;

    @Column(name = "AddressPostalCode", nullable = false, length = 5)
    private String addressPostalCode;

    @Column(name = "AddressStreet")
    private String addressStreet;

    @Column(name = "AddressNumber", nullable = false, length = 20)
    private String addressNumber;

    @Column(name = "AddressFlatNumber", length = 20)
    private String addressFlatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DefaultAccountId")
    private Account defaultAccount;

    @OneToMany(mappedBy = "requestRecipientUser")
    private Set<MoneyRequest> moneyRequestsReceived = new LinkedHashSet<>();

    @OneToMany(mappedBy = "requestSenderUser")
    private Set<MoneyRequest> moneyRequestsSent = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UUID getSalt() {
        return salt;
    }

    public void setSalt(UUID salt) {
        this.salt = salt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getUsesPhoneTransfer() {
        return usesPhoneTransfer;
    }

    public void setUsesPhoneTransfer(Boolean usesPhoneTransfer) {
        this.usesPhoneTransfer = usesPhoneTransfer;
    }

    public Boolean getAllowsMoneyRequests() {
        return allowsMoneyRequests;
    }

    public void setAllowsMoneyRequests(Boolean allowsMoneyRequests) {
        this.allowsMoneyRequests = allowsMoneyRequests;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressFlatNumber() {
        return addressFlatNumber;
    }

    public void setAddressFlatNumber(String addressFlatNumber) {
        this.addressFlatNumber = addressFlatNumber;
    }

    public Account getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(Account defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public Set<MoneyRequest> getMoneyRequestsReceived() {
        return moneyRequestsReceived;
    }

    public void setMoneyRequestsReceived(Set<MoneyRequest> moneyRequestsReceived) {
        this.moneyRequestsReceived = moneyRequestsReceived;
    }

    public Set<MoneyRequest> getMoneyRequestsSent() {
        return moneyRequestsSent;
    }

    public void setMoneyRequestsSent(Set<MoneyRequest> moneyRequestsSent) {
        this.moneyRequestsSent = moneyRequestsSent;
    }

}