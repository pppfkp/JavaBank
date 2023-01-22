package com.pppfkp.javabank;

import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.DTOs.AccountDTO;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.AccountType;
import com.pppfkp.javabank.Data.Models.Transaction;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.AccountTypeRepository;
import com.pppfkp.javabank.Services.AccountManagementService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAccountDetailsScreenController implements Initializable {
    private AccountManagementService accountManagementService;
    private AccountRepository accountRepository;
    private Account defaultAccount;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Label accountBalanceLabel;
    @FXML
    private ChoiceBox<Account> accountChoiceBox;
    @FXML
    private TableView<Transaction> transactionTableView;
    @FXML
    private Button goBackButton;
    @FXML
    private Button closeAccountButton;
    @FXML
    private Button newAccountButton;
    @FXML
    private ChoiceBox<AccountType> accountTypeChoiceBox;
    @FXML
    private Button setLimitButton;
    @FXML
    private TextField limitTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CurrentUserAppState.Refresh();
        accountRepository = new AccountRepository(HibernateConnectUtility.getSessionFactory());
        accountManagementService = new AccountManagementService(accountRepository, new AccountTypeRepository(HibernateConnectUtility.getSessionFactory()));
        closeAccountButton.setVisible(false);

        ObservableList<Transaction> transactions = FXCollections.observableList(CurrentUserAppState.getTransactions());

        TableColumn<Transaction, String> transactionId = new TableColumn<>("Id");
        transactionId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaction, String> accountFromName = new TableColumn<Transaction, String>("Nadawca");
        accountFromName.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getSenderAccount().getUser().getFirstName() + " " + f.getValue().getSenderAccount().getUser().getLastName()));

        TableColumn<Transaction, String> accountFromNumber = new TableColumn<Transaction, String>("Numer nadawcy");
        accountFromNumber.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getSenderAccount().getId()));

        TableColumn<Transaction, String> accountToNumber = new TableColumn<Transaction, String>("Numer odbiorcy");
        accountToNumber.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getRecipientAccount().getId()));

        TableColumn<Transaction, String> accountToName = new TableColumn<Transaction, String>("Odbiorca");
        accountToName.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getRecipientAccount().getUser().getFirstName() + " " + f.getValue().getRecipientAccount().getUser().getLastName()));

        TableColumn<Transaction, BigDecimal> transactionAmmount = new TableColumn<Transaction, BigDecimal>("Kwota");
        transactionAmmount.setCellValueFactory(new PropertyValueFactory<>("ammount"));

        transactions.removeAll(transactions);
        transactionTableView.setItems(transactions);
        transactionTableView.getColumns().addAll(transactionId, accountFromName,accountFromNumber, accountToName, accountToNumber, transactionAmmount);
        defaultAccount = CurrentUserAppState.getUser().getDefaultAccount();

        for (Account account : CurrentUserAppState.getAccounts()) {
            if (account.getId().equals(defaultAccount.getId())) {
                defaultAccount = account;
                break;
            }
        }

        if (defaultAccount != null) {
            accountNameLabel.setText("Konto " + defaultAccount.getAccountType().getAccountName() + " " + defaultAccount.getId());
            accountBalanceLabel.setText("Stan konta: " + defaultAccount.getBalance() + "PLN");
            limitTextField.setText(String.valueOf(defaultAccount.getTransferLimit()));
        }
        ObservableList<AccountType> accountTypes = FXCollections.observableList(CurrentUserAppState.getAccountTypes());
        accountTypeChoiceBox.setConverter(new ShowAccountDetailsScreenController.MyClassConverter2());
        accountTypeChoiceBox.setValue(CurrentUserAppState.getAccountTypes().get(1));
        accountTypeChoiceBox.setItems(accountTypes);

        ObservableList<Account> accounts = FXCollections.observableList(CurrentUserAppState.getAccounts().stream().filter(a -> (a.getSoftDeleted() == false)).toList());
        accountChoiceBox.setConverter(new ShowAccountDetailsScreenController.MyClassConverter());
        accountChoiceBox.setValue(CurrentUserAppState.getUser().getDefaultAccount());
        accountChoiceBox.setItems(accounts);
        accountChoiceBox.setOnAction((e) -> {
            defaultAccount = accountChoiceBox.getSelectionModel().getSelectedItem();
            limitTextField.setText(defaultAccount.getTransferLimit().toString());
            if (!defaultAccount.getId().equals(CurrentUserAppState.getUser().getDefaultAccount().getId())) {
                closeAccountButton.setVisible(true);
            } else {
                closeAccountButton.setVisible(false);
            }
            accountNameLabel.setText("Konto " + defaultAccount.getAccountType().getAccountName() + " " + defaultAccount.getId());
            accountBalanceLabel.setText("Stan konta: " + defaultAccount.getBalance() + "PLN");
            transactions.removeAll(transactions);
            for (var transaction:CurrentUserAppState.getTransactions()) {
                if (transaction.getRecipientAccount().getId().equals(defaultAccount.getId())) {
                    transactions.add(transaction);
                }
                if (transaction.getSenderAccount().getId().equals(defaultAccount.getId())) {
                    transactions.add(transaction);
                }
            }
        });
    }
    @FXML
    public void switchToMainScreen() throws IOException {
        switchScreen("MainScreen");
    }
    @FXML
    public void changeAccountLimit() {
        String newLimit = limitTextField.getText();

        if (!newLimit.matches("\\d+\\.\\d{2}")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Nieprawidłowy format limitu!");
            a.show();
            return;
        }
        BigDecimal newLimitMoney = new BigDecimal(newLimit);
        if (newLimitMoney.compareTo(defaultAccount.getAccountType().getMaxTransferLimit()) > 0) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Twój typ konta posiada ograniczenie limitu do " + defaultAccount.getAccountType().getMaxTransferLimit().toString() + " PLN!");
            a.show();
            return;
        }
        defaultAccount.setTransferLimit(newLimitMoney);
        AccountDTO dto = new AccountDTO(defaultAccount);
        accountRepository.UpdateAccount(dto, defaultAccount.getId());
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Twój limit został ustawiony na " + defaultAccount.getTransferLimit());
        a.show();

    }
    @FXML
    public void closeAccount() {
        accountManagementService.CloseAccount(defaultAccount.getId());
        CurrentUserAppState.Refresh();
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Zamknąłeś konto!");
        a.show();
    }
    @FXML
    public void OpenAccount() {
        AccountDTO dto = new AccountDTO(new BigDecimal(1000), accountTypeChoiceBox.getValue(), CurrentUserAppState.getUser());
        accountManagementService.OpenAccount(dto);
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Utworzyłeś konto!");
        a.show();
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

    class MyClassConverter2 extends StringConverter<AccountType> {
        List<AccountType> accountTypes = CurrentUserAppState.getAccountTypes();
        public AccountType fromString(String string) {
            // convert from a string to a myClass instance
            return accountTypes.stream().filter(at -> at.getAccountName().equals(string)).toList().get(0);
        }

        public String toString(AccountType accountType) {
            if (accountType != null) {
                return accountType.getAccountName();
            }
            return "null";
        }
    }

}
