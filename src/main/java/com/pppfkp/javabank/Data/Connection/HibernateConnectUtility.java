package com.pppfkp.javabank.Data.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Allows you to easily retrieve session factory, as it should be built only once in the program
public class HibernateConnectUtility {
    private static SessionFactory session = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSession() {
        return session;
    }
}
