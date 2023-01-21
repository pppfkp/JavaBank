package com.pppfkp.javabank;

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
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        */
    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository(HibernateConnectUtility.getSessionFactory());
        AccountRepository accountRepository = new AccountRepository(HibernateConnectUtility.getSessionFactory());
        AccountTypeRepository accountTypeRepository = new AccountTypeRepository(HibernateConnectUtility.getSessionFactory());
        SignInService signInService = new SignInService(userRepository);
        AccountMoneyService accountMoneyService = new AccountMoneyService(accountRepository);
        UserAccountManagementService userAccountManagementService = new UserAccountManagementService(userRepository, accountRepository);
        TransactionRepository transactionRepository = new TransactionRepository(HibernateConnectUtility.getSessionFactory());
        AccountManagementService accountManagementService = new AccountManagementService(accountRepository, accountTypeRepository);
        //launch();
        /*
        String connectionUrl = "jdbc:sqlserver://pppfkp-university-java.database.windows.net:1433;database=JAVA_BANK;user=pppfkp@pppfkp-university-java;password=Password2137;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT TOP 10 * FROM USERS";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        */
        /*
        //SELECT test
        Session session = HibernateConnectUtility.getSessionFactory().openSession();
        String SELECT = "FROM User";
        Query q = session.createQuery(SELECT, User.class);
        List<User> resultList = (List<User>) q.list();
        for (var user:
             resultList) {
            System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getPasswordHash());
        }
        session.close();
        */
        /*
        //CREATE test
        UserDTO newUser = new UserDTO("pppfkp1", "daniel", "wojtowicz", "kuba@gej.pl", true, "111111111", "01262201499", LocalDate.of(2001,10,5), true, "Słotowa", "Rzeszow", "33162", "Lukasiewicza", "2", "115", null, "21376969");

        Integer createdUserId = userRepository.CreateUser(newUser);
        System.out.println(createdUserId);
         */
        //System.out.println(userRepository.GetUserById(100));
        /*
        for (var user : userRepository.GetAllUsers()) {
            System.out.println(user.getFirstName());
        }
        */
        /*
        //UPDATE TEST
        UserDTO updatedUser = new UserDTO("pppfkp1", "daniel", "gejowicz", "kuba2137@gej.pl", true, "111111111", "01262201499", LocalDate.of(2001,10,5), true, "Słotowa", "Rzeszow", "33162", "Lukasiewicza", "2", "115", null, "21376969");

        System.out.println(userRepository.UpdateUser(updatedUser, 10));
        */
        /*
        DELETE test

        System.out.println(userRepository.DeleteUser(10));
        */

        //AccountDTO newAccount = new AccountDTO(new BigDecimal(1000), accountTypeRepository.GetAccountTypeById(2), userRepository.GetUserById(23));
       /*
        AccountDTO updateAccount = new AccountDTO(new BigDecimal(2400), accountTypeRepository.GetAccountTypeById(7), userRepository.GetUserById(23));

        System.out.println(signInService.SignIn("pppfkp1", "21376969"));

        accountRepository.UpdateAccount(updateAccount, "81200112344175603185079988");
        */

        /*
        List<Account> accounts = accountRepository.GetAllAccountsByUserId(23);
        for (var account:
             accounts) {
            System.out.println(account.getId() + " " + account.getAccountType().getAccountName() + " " + account.getUser().getFirstName());
        }
        System.out.println(userRepository.GetUserById(23).getSoftDeleted());
        userRepository.SoftDeleteUser(23);
        System.out.println(userRepository.GetUserById(23).getSoftDeleted());

        accountRepository.ChangeBalance(new BigDecimal(69000), "81200112344175603185079988");
        */
        //System.out.println(accountMoneyService.DepositMoneyOnAccount(new BigDecimal(150), "84200112347950437514944958"));
        //System.out.println(accountMoneyService.WithdrawMoneyFromAccount(new BigDecimal(1), "84200112347950437514944958"));
        //userAccountManagementService.CloseUserAccount(23);
        //UserDTO newUser1 = new UserDTO("pppfkp", "filip", "lepa", "filip@spoko.pl", true, "668431600", "01262201439", LocalDate.of(2001,6,22), true, "Tarnów", "Wola Lubecka", "33162", "Lukasiewicza", "71", "1", null, "Password");
        //SignUpService signUpService = new SignUpService(userRepository, accountRepository);
        //signUpService.SignUp(newUser1);
        System.out.println(signInService.SignIn("pppfkp", "Password"));
        //AccountDTO newAccount = new AccountDTO(new BigDecimal(2000), accountTypeRepository.GetAccountTypeById(2), userRepository.GetUserById(25));
        //accountManagementService.OpenAccount(newAccount);
        //TransactionDTO transactionDTO = new()
        //transactionRepository.CreateTransaction();
        TransactionService transactionService = new TransactionService(transactionRepository,accountRepository,accountMoneyService);
        TransactionDTO transactionDTO = new TransactionDTO(accountRepository.GetAccountById("57200112342056355024193255") ,
        accountRepository.GetAccountById("81200112344175603185079988"), new BigDecimal(1000), "to dla cb fikip");
        System.out.println(transactionService.MakeTransaction(transactionDTO).getAmmount());
        HibernateConnectUtility.CloseConnection();

        System.exit(0);
    }
}