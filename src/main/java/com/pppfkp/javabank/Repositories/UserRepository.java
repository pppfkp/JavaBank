package com.pppfkp.javabank.Repositories;

import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;
import java.util.List;

public class UserRepository{
    private SessionFactory sessionFactory;
    private GenericRepository<User, UserDTO, Integer> baseRepository;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.baseRepository = new GenericRepository<>(sessionFactory, User.class);
    }
    //TODO add soft delete check
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
        User user = baseRepository.GetSingleRecordById(id);
        if (user.getSoftDeleted()) return null;
        return user;
    }
    public List<User> GetAllUsers()
    {
        return baseRepository.GetAllRecords();
    }
    public boolean UpdateUser(UserDTO dto, Integer oldUserId)
    {
        return baseRepository.UpdateRecord(dto, oldUserId);
    }
    public boolean UpdateUser(User user) {
        return baseRepository.UpdateRecord(user);
    }
    public boolean DeleteUser(Integer id) {
        return baseRepository.DeleteRecord(id);
    }
    public User getUserByLogin(String login) {
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
        if (!CheckIfExists(login)) {
            return false;
        }
        return BCrypt.checkpw(password, user.getPasswordHash());
    }
    public boolean SoftDeleteUser(Integer id) {
        User userToSoftDelete = GetUserById(id);
        if (userToSoftDelete == null) return false;
        Session session = baseRepository.getSessionFactory().openSession();
        try {
            session.beginTransaction();
           userToSoftDelete.setSoftDeleted(true);
            session.update(userToSoftDelete);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return  true;
    }
    public boolean CheckIfExists(String login) {
        User user = getUserByLogin(login);
        if (user == null || user.getSoftDeleted()) {
            return false;
        }
        return true;
    }
}
