package com.pppfkp.javabank.Data.Connection;

import org.hibernate.service.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

//Allows you to easily retrieve session factory, as it should be built only once in the program
public class HibernateConnectUtility {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void CloseConnection() {
        sessionFactory.close();
    }
}
