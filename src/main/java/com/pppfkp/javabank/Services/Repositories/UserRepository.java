package com.pppfkp.javabank.Services.Repositories;

import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.User;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserRepository{
    private SessionFactory sessionFactory;
    private GenericRepository<User, UserDTO, Integer> baseRepository;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.baseRepository = new GenericRepository<>(sessionFactory, User.class);
    }

    public Integer CreateUser(UserDTO dto)
    {
        return baseRepository.CreateRecord(dto);
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







}
