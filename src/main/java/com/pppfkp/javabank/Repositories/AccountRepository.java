package com.pppfkp.javabank.Repositories;

import com.pppfkp.javabank.Data.DTOs.AccountDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class AccountRepository {
    private SessionFactory sessionFactory;
    private GenericRepository<Account, AccountDTO, String> baseRepository;

    public AccountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.baseRepository = new GenericRepository<>(sessionFactory, Account.class);
    }

    public String CreateAccount(AccountDTO dto) {
        Account newAccount = dto.MapToEntityTypeNewRecord();
        System.out.println(newAccount.getId());
        System.out.println(newAccount.getUser().getUserLogin());
        System.out.println(newAccount.getTransferLimit());
        System.out.println(newAccount.getAccountType().getAccountName());
        if(newAccount == null || checkIfDuplicate(newAccount)) {
            return  null;
        }
        return baseRepository.CreateRecord(newAccount);
    }
    public boolean UpdateAccount(AccountDTO dto, String oldAccountId) {
        return baseRepository.UpdateRecord(dto, oldAccountId);
    }
    public List<Account> GetAllAccounts() {
        return baseRepository.GetAllRecords();
    }
    public Account GetAccountById(String id) {
        return baseRepository.GetSingleRecordById(id);
    }
    public List<Account> GetAllAccountsByUserId(Integer userId) {
        return baseRepository.GetAllRecordsByFieldValue("user.Id", String.valueOf(userId));
    }
    public boolean SoftDeleteAccount(String id) {
        Account accountToSoftDelete = GetAccountById(id);
        if (accountToSoftDelete == null) return false;
        Session session = baseRepository.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            accountToSoftDelete.setSoftDeleted(true);
            session.update(accountToSoftDelete);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return  true;
    }
    public boolean ChangeBalance(BigDecimal newBalance, String id) {
        if (newBalance.intValue() < 0) return false;
        Account accountToChangeBalance = GetAccountById(id);

        Session session = baseRepository.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            accountToChangeBalance.setBalance(newBalance);
            session.update(accountToChangeBalance);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return  true;
    }
    private boolean checkIfDuplicate(Account newAccount) {
        if(baseRepository.GetSingleRecordById(newAccount.getId()) == null) {
            return false;
        }
        return true;
    }
}
