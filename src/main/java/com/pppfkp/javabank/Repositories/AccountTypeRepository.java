package com.pppfkp.javabank.Repositories;

import com.pppfkp.javabank.Data.Models.AccountType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class AccountTypeRepository {
   private SessionFactory sessionFactory;

    public AccountTypeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AccountType GetAccountTypeById(Integer id) {
        Session session = sessionFactory.openSession();
        AccountType accountType = session.get(AccountType.class, id);
        session.close();
        return accountType;
    }
    public List<AccountType> GetAccountTypes() {
        Session session = sessionFactory.openSession();;
        List<AccountType> accountTypes = new ArrayList<>();
        accountTypes = (List<AccountType>) session.createQuery("from " + AccountType.class.getSimpleName()).list();
        session.close();
        return accountTypes;
    }
}
