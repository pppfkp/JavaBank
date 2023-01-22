package com.pppfkp.javabank;

import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.DTOs.TransactionDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.TransactionRepository;
import com.pppfkp.javabank.Services.AccountMoneyService;
import com.pppfkp.javabank.Services.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MakeTransactionScreenController implements Initializable {
    private TransactionService transactionService;
    private Account currentAccount;
    @FXML
    private Button goBackButton;
    @FXML
    private ChoiceBox<Account> fromAccountChoice;
    @FXML
    private TextField toAccountTextField;
    @FXML
    private TextField transactionAmmountTextField;
    @FXML
    private TextField transactionTitleTextField;
    @FXML
    private Button makeTransactionButton;
    @FXML
    private VBox validationErrorsBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentAccount = CurrentUserAppState.getUser().getDefaultAccount();
        ObservableList<Account> accounts = FXCollections.observableList(CurrentUserAppState.getAccounts());
        fromAccountChoice.setConverter(new MakeTransactionScreenController.MyClassConverter());
        fromAccountChoice.setValue(CurrentUserAppState.getUser().getDefaultAccount());
        fromAccountChoice.setItems(accounts);
        transactionService = new TransactionService(new TransactionRepository(HibernateConnectUtility.getSessionFactory()), new AccountRepository(HibernateConnectUtility.getSessionFactory()), new AccountMoneyService(new AccountRepository(HibernateConnectUtility.getSessionFactory())));

    }
    @FXML
    public void MakeTransaction(ActionEvent e) {
        validationErrorsBox.getChildren().clear();
        String fromAccount = fromAccountChoice.getValue().getId();
        Account accountFrom = fromAccountChoice.getValue();
        String toAccount = toAccountTextField.getText();
        if (fromAccount.equals(toAccount)) {
            validationErrorsBox.getChildren().add(new Label("Numer odbiorcy i nadawcy są takie same"));
        }
        /*
        if (!(toAccount.chars().allMatch( Character::isDigit) && toAccount.length() == 26)) {
            validationErrorsBox.getChildren().add(new Label("Nieprawidlowy numer konta!"));
            return;
        } else {
            Long accountNumber = Long.parseLong(toAccount.substring(10,25));
            Long checkSum = Long.parseLong(toAccount.substring(0,1));
            if (sumOfDigits(accountNumber) != checkSum) {
                validationErrorsBox.getChildren().add(new Label("Nieprawidlowy numer konta!"));
                return;
            }
        }
         */
        String ammountStringPln = transactionAmmountTextField.getText();
        if (ammountStringPln.isEmpty()) {
            validationErrorsBox.getChildren().add(new Label("Nieprawidlowa kwota!"));
            return;
        }

        if (!ammountStringPln.matches("\\d+\\.\\d{2}")) {
            validationErrorsBox.getChildren().add(new Label("Nieprawidłowy format kwoty!"));
            return;
        }

        Locale polish = new Locale("pl", "PL");
        NumberFormat numberFormatPolish = NumberFormat.getCurrencyInstance(polish);

        String c = numberFormatPolish.format(new BigDecimal(ammountStringPln));
        BigDecimal bd;
        try {
            Number  d = numberFormatPolish.parse(c);
            bd = new BigDecimal(d.toString());
        } catch (Exception ex) {
            validationErrorsBox.getChildren().add(new Label("Nieprawidołowy format pieniężny!"));
            return;
        }
        String title = transactionTitleTextField.getText();

        TransactionDTO dto = new TransactionDTO(toAccount, fromAccount, bd, title);
        if (transactionService.MakeTransaction(dto) == null) {
            validationErrorsBox.getChildren().add(new Label("Nie udało się wykonać przelewu!"));
            return;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Transakcja wykonana!");
        a.show();
    }
    @FXML
    public void switchToMainScreen() throws IOException {
        switchScreen("MainScreen");
    }
    @FXML
    public void switchScreen(String screenName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(screenName + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage window = (Stage) goBackButton.getScene().getWindow();
        window.setScene(scene);
        window.sizeToScene();
        window.setMaximized(false);
        window.setMaximized(true);
    }
    private int sumOfDigits(long number) {
        long sum = 0;
        while (number > 0) {
            long lastDigit = number % 10;
            sum += lastDigit;
            number /= 10;
        }
        return Math.toIntExact(sum % 99);
    }
    class MyClassConverter extends StringConverter<Account> {
        AccountRepository accountRepository = new AccountRepository(HibernateConnectUtility.getSessionFactory());
        public Account fromString(String string) {
            // convert from a string to a myClass instance
            return accountRepository.GetAccountById(string);
        }

        public String toString(Account accountInstance) {
            if (accountInstance != null) {
                return accountInstance.getId();
            }
            return "null";
        }
    }
}
