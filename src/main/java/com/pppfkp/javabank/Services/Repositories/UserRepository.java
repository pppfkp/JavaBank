package com.pppfkp.javabank.Services.Repositories;

import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;
import java.util.List;

public class UserRepository{
    private SessionFactory sessionFactory;
    private GenericRepository<User, UserDTO, Integer> baseRepository;
    private static User currentUser;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.baseRepository = new GenericRepository<>(sessionFactory, User.class);
    }

    public Integer CreateUser(UserDTO dto)
    {
        User newUser = dto.MapToEntityTypeNewRecord();
        if (newUser == null || checkIfDuplicate(newUser)) {
            return null;
        }
        return baseRepository.CreateRecord(newUser);
    }
    public User GetUserById(Integer id)
    {
        return baseRepository.GetSingleRecordById(id);
    }
    public List<User> GetAllUsers()
    {
        return baseRepository.GetAllRecords();
    }
    public boolean UpdateUser(UserDTO dto, Integer oldUserId)
    {
        return baseRepository.UpdateRecord(dto, oldUserId);
    }
    public boolean DeleteUser(Integer id) {
        return baseRepository.DeleteRecord(id);
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    private User getUserByLogin(String login) {
        Session session = baseRepository.getSessionFactory().openSession();
        User user = session.byNaturalId(User.class)
                .using("userLogin", login)
                .load();
        session.close();
        return user;
    }
    private boolean checkIfDuplicate(User user) {
        Session session = baseRepository.getSessionFactory().openSession();
        List<String> userUniquePropertyNames = Arrays.asList("userLogin", "phoneNumber", "email", "pesel");
        List<String> userUniqueProperties = Arrays.asList(user.getUserLogin(), user.getPhoneNumber(), user.getEmail(), user.getPesel());

        for (int i = 0; i < userUniqueProperties.size(); ++i) {
            User check = baseRepository.GetSingleRecordByFieldValue(userUniquePropertyNames.get(i), userUniqueProperties.get(i));
            if (check != null) {
                return true;
            }
        }
        session.close();
        return false;
    }
    public boolean AuthenticateUser(String login, String password) {
        User user = getUserByLogin(login);
        if (user == null) {
            return false;
        }
        boolean authenticationResult =  BCrypt.checkpw(password, user.getPasswordHash());
        if (authenticationResult) {
            currentUser = user;
            return true;
        } else {
            return  false;
        }
    }
}
