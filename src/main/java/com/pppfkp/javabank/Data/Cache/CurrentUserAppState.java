package com.pppfkp.javabank.Data.Cache;

import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.Transaction;
import com.pppfkp.javabank.Data.Models.User;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.TransactionRepository;
import com.pppfkp.javabank.Services.SignInService;

import java.util.ArrayList;
import java.util.List;

public class CurrentUserAppState {
    private static AccountRepository accountRepository;
    private static TransactionRepository transactionRepository;
    private static User user;
    private static List<Account> accounts;
    private static List<Transaction> transactionsFrom;
    private static List<Transaction> transactionsTo;

    public static void Refresh() {
        user = SignInService.getCurrentUser();
        if (user == null) return;
        RefreshAccounts();
        RefreshTransactions();
    }
    public static void Clear() {
        user = null;
        accounts = new ArrayList<>();
        transactionsTo = new ArrayList<>();
        transactionsFrom = new ArrayList<>();
    }
    public static void RefreshAccounts() {
        if (user == null) return;
        accountRepository = new AccountRepository(HibernateConnectUtility.getSessionFactory());
        accounts = accountRepository.GetAllAccountsByUserId(user.getId());
        accountRepository = null;
    }

    public static void RefreshTransactions() {
        if (user == null) return;
        RefreshTransactionsTo();
        RefreshTransactionsFrom();
    }

    public static void RefreshTransactionsTo() {
        if (user == null) return;
        transactionRepository = new TransactionRepository(HibernateConnectUtility.getSessionFactory());
        transactionsTo = transactionRepository.GetALlTransactionsToUser(user.getId());
        transactionRepository = null;
    }

    public static void RefreshTransactionsFrom() {
        if (user == null) return;
        transactionRepository = new TransactionRepository(HibernateConnectUtility.getSessionFactory());
        transactionsFrom = transactionRepository.GetALlTransactionsFromUser(user.getId());
        transactionRepository = null;
    }

    public static User getUser() {
        return user;
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionsTo);
        transactions.addAll(transactionsFrom);
        return transactions;
    }

    public static List<Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    public static List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }
}
