package com.pppfkp.javabank.Data.DTOs;

import com.pppfkp.javabank.Data.Models.Account;

import java.time.LocalDate;

public class UserDTO {
    private String userLogin;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean usesPhoneTransfer;
    private String phoneNumber;
    private String pesel;
    private LocalDate birthdate;
    private Boolean allowsMoneyRequests;
    private String addressCity;
    private String addressPostalCode;
    private String addressStreet;
    private String addressNumber;
    private String addressFlatNumber;
    private Account defaultAccount;

    public UserDTO(String userLogin, String firstName, String lastName, String email, Boolean usesPhoneTransfer, String phoneNumber, String pesel, LocalDate birthdate, Boolean allowsMoneyRequests, String addressCity, String addressPostalCode, String addressStreet, String addressNumber, String addressFlatNumber, Account defaultAccount) {
        this.userLogin = userLogin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.usesPhoneTransfer = usesPhoneTransfer;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.birthdate = birthdate;
        this.allowsMoneyRequests = allowsMoneyRequests;
        this.addressCity = addressCity;
        this.addressPostalCode = addressPostalCode;
        this.addressStreet = addressStreet;
        this.addressNumber = addressNumber;
        this.addressFlatNumber = addressFlatNumber;
        this.defaultAccount = defaultAccount;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getUsesPhoneTransfer() {
        return usesPhoneTransfer;
    }

    public void setUsesPhoneTransfer(Boolean usesPhoneTransfer) {
        this.usesPhoneTransfer = usesPhoneTransfer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Boolean getAllowsMoneyRequests() {
        return allowsMoneyRequests;
    }

    public void setAllowsMoneyRequests(Boolean allowsMoneyRequests) {
        this.allowsMoneyRequests = allowsMoneyRequests;
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
}
