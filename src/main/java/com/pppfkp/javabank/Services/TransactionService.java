package com.pppfkp.javabank.Services;

import com.pppfkp.javabank.Data.DTOs.TransactionDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.Transaction;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private AccountMoneyService accountMoneyService;
    private List<String> errors;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountMoneyService accountMoneyService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountMoneyService = accountMoneyService;
        this.errors = new ArrayList<>();
    }

    public Transaction MakeTransaction(TransactionDTO transactionDTO) {
        Account sender = accountRepository.GetAccountById(transactionDTO.getSenderAccount().getId());
        Account recipient = accountRepository.GetAccountById(transactionDTO.getRecipientAccount().getId());

        if (sender == null || sender.getSoftDeleted()) {
            errors.add("Konto wykonawcy przelewu nie istnieje!");
            return null;
        }
        if (recipient == null ||  recipient.getSoftDeleted()) {
            errors.add("Konto odbiorcy przelewu nie istnieje");
            return null;
        }
        accountMoneyService.WithdrawMoneyFromAccount(transactionDTO.getAmmount(), sender.getId());
        accountMoneyService.DepositMoneyOnAccount(transactionDTO.getAmmount(), recipient.getId());
        errors = transactionDTO.ValidateAll();
        if (!errors.isEmpty()) return null;
        return transactionRepository.CreateTransaction(transactionDTO);
    }
}
