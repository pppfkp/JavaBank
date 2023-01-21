package com.pppfkp.javabank.Repositories;

import com.pppfkp.javabank.Data.DTOs.TransactionDTO;
import com.pppfkp.javabank.Data.Models.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionRepository {
    private SessionFactory sessionFactory;
    private GenericRepository<Transaction, TransactionDTO, String> baseRepository;

    public TransactionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.baseRepository = new GenericRepository<>(sessionFactory, Transaction.class);
    }

    public Transaction GetTransactionById(String id) {
            return baseRepository.GetSingleRecordById(id);
    }
    public List<Transaction> GetAllTransactions() {
        return  baseRepository.GetAllRecords();
    }
    public List<Transaction> GetALlTransactionsFromUser(Integer userId) {
        return baseRepository.GetAllRecordsByFieldValue("senderAccount.user.id", String.valueOf(userId));

    }
    public List<Transaction> GetALlTransactionsToUser(Integer userId) {
        return baseRepository.GetAllRecordsByFieldValue("recipientAccount.user.id", String.valueOf(userId));

    }
    public List<Transaction> GetAllTransactionsWithUserInvolved(Integer userId) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(GetALlTransactionsFromUser(userId));
        transactions.addAll(GetALlTransactionsToUser(userId));
        return transactions;
    }
    public Transaction CreateTransaction(TransactionDTO dto) {
        if (!dto.ValidateAll().isEmpty()) return null;
        String id = baseRepository.CreateRecord(dto);
        return GetTransactionById(id);
    }
}
