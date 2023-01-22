package com.pppfkp.javabank;

import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.DTOs.AccountDTO;
import com.pppfkp.javabank.Data.DTOs.TransactionDTO;
import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.AccountType;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.AccountTypeRepository;
import com.pppfkp.javabank.Repositories.TransactionRepository;
import com.pppfkp.javabank.Repositories.UserRepository;

import com.pppfkp.javabank.Services.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AuthorizationScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 900);
        stage.setTitle("Hello!");
        stage.setMaximized(true);
        //stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository(HibernateConnectUtility.getSessionFactory());
        AccountRepository accountRepository = new AccountRepository(HibernateConnectUtility.getSessionFactory());
        AccountTypeRepository accountTypeRepository = new AccountTypeRepository(HibernateConnectUtility.getSessionFactory());
        SignInService signInService = new SignInService(userRepository);
        //AccountMoneyService accountMoneyService = new AccountMoneyService(accountRepository);
        UserAccountManagementService userAccountManagementService = new UserAccountManagementService(userRepository, accountRepository);
        TransactionRepository transactionRepository = new TransactionRepository(HibernateConnectUtility.getSessionFactory());
        AccountManagementService accountManagementService = new AccountManagementService(accountRepository, accountTypeRepository);
        launch();

        AccountMoneyService accountMoneyService = new AccountMoneyService(new AccountRepository(HibernateConnectUtility.getSessionFactory()));
        accountMoneyService.WithdrawMoneyFromAccount(new BigDecimal(1000), "81200112344175603185079988");



        HibernateConnectUtility.CloseConnection();
        System.exit(0);
    }
}