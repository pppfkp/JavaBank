package com.pppfkp.javabank.Data.DTOs;

import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.User;
import org.mindrot.jbcrypt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDTO implements IMapableTo<User> {
    private String userLogin;
    private String password;
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
    private String cityOfBirth;

    public UserDTO(String userLogin, String firstName, String lastName, String email, Boolean usesPhoneTransfer, String phoneNumber, String pesel, LocalDate birthdate, Boolean allowsMoneyRequests, String cityOfBirth, String addressCity, String addressPostalCode, String addressStreet, String addressNumber, String addressFlatNumber, Account defaultAccount, String password) {
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
        this.password = password;
        this.cityOfBirth = cityOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
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

    @Override
    public User MapToEntityTypeNewRecord() {
        if(!ValidateAll().isEmpty()) return null;
        User user = new User();
        SetUpdatableFields(user);
        user.setBirthdate(this.birthdate);
        user.setCityOfBirth(this.cityOfBirth);
        user.setPesel(this.getPesel());

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        user.setPasswordHash(passwordHash);
        return user;
    }

    @Override
    public User MapToEntityTypeUpdateRecord(User userToUpdate) {
        SetUpdatableFields(userToUpdate);
        return userToUpdate;
    }

    @Override
    public List<String> ValidateUpdatable() {
        List<String> errorList = new ArrayList<String>();
        return errorList;
    }

    @Override
    public List<String> ValidateAll() {
        List<String> errorList = new ArrayList<String>();
        return errorList;
    }

    private void SetUpdatableFields(User user) {
        user.setAddressCity(this.addressCity);
        user.setAddressNumber(this.addressNumber);
        user.setBirthdate(this.birthdate);
        user.setUserLogin(this.userLogin);
        user.setEmail(this.email);
        user.setAddressFlatNumber(this.addressFlatNumber);
        user.setAddressPostalCode(this.addressPostalCode);
        user.setDefaultAccount(this.defaultAccount);
        user.setAddressStreet(this.addressStreet);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setUsesPhoneTransfer(this.usesPhoneTransfer);
        user.setPhoneNumber(this.phoneNumber);
        user.setAllowsMoneyRequests(this.allowsMoneyRequests);
    }
}
