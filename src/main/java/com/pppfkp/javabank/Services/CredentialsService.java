package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class CredentialsService {
    private UserRepository userRepository;
    private List<String> errors;

    public CredentialsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        errors = new ArrayList<String>();
    }

    public boolean ChangePassword(User user, String oldPassword, String newPassword) {
        if (user == null || !userRepository.AuthenticateUser(user.getUserLogin(), oldPassword)) return false;
        UserDTO dto = new UserDTO(user);
        dto.setPassword(newPassword);
        errors = dto.ValidatePassword();
        if (!errors.isEmpty()) return false;
        String passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        user.setPasswordHash(passwordHash);
        userRepository.UpdateUser(user);
        return userRepository.UpdateUser(dto, user.getId());
    }

    public boolean ChangeLogin(User user, String newLogin) {
        if (user == null) return false;
        UserDTO dto = new UserDTO(user);
        dto.setUserLogin(newLogin);
        dto.MapToEntityTypeUpdateRecord(user);
        errors = dto.ValidateLogin();
        if (!errors.isEmpty()) return false;
        return userRepository.UpdateUser(dto, user.getId());
    }

    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public  void clearErrors() {
        setErrors(new ArrayList<>());
    }
}
