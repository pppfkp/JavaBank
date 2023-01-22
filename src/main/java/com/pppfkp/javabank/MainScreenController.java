package com.pppfkp.javabank;

import com.pppfkp.javabank.App;
import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.Models.Account;
import com.pppfkp.javabank.Data.Models.Transaction;
import com.pppfkp.javabank.Repositories.UserRepository;
import com.pppfkp.javabank.Services.SignInService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private SignInService signInService;
    private Account defaultAccount;
    @FXML
    private Label userNameLabel;
    @FXML
    private Button signOutButton;
    @FXML
    private TableView<Transaction> transactionTableView;
    @FXML
    private Label defaultAccountLabel;
    @FXML
    private Label defaultAccountBalanceLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CurrentUserAppState.Refresh();
        signInService = new SignInService(new UserRepository(HibernateConnectUtility.getSessionFactory()));
        userNameLabel.setText("Witaj " + CurrentUserAppState.getUser().getFirstName() + " " + CurrentUserAppState.getUser().getLastName());
        //transactions table
        ObservableList<Transaction> transactions = FXCollections.observableList(CurrentUserAppState.getTransactions());
        if (transactions.size() > 5) {
            transactions.removeAll(transactions.subList(5, transactions.size()-1));
        }


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


        transactionTableView.setItems(transactions);
        transactionTableView.getColumns().addAll(transactionId, accountFromName,accountFromNumber, accountToName, accountToNumber, transactionAmmount);

        defaultAccount = CurrentUserAppState.getUser().getDefaultAccount();
        if (defaultAccount == null) {
            defaultAccountLabel.setVisible(false);
            defaultAccountBalanceLabel.setVisible(false);
        }
        for (Account account : CurrentUserAppState.getAccounts()) {
            if (account.getId().equals(defaultAccount.getId())) {
                defaultAccount = account;
                break;
            }
        }
        if (defaultAccount != null) {
            defaultAccountLabel.setText("Konto " + defaultAccount.getAccountType().getAccountName() + " " + defaultAccount.getId());
            defaultAccountBalanceLabel.setText("Stan konta: " + defaultAccount.getBalance().toString() + " PLN");
        }

    }

    @FXML
    public void signOut(ActionEvent e) throws IOException {
        CurrentUserAppState.Clear();
        signInService.SignOut();
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Wylogowano!");
        a.show();
        switchToAuthorizationScreen();
    }

    @FXML
    public void switchToAccountDetailsScreen() throws IOException {
        switchScreen("ShowAccountDetailsScreen");
    }
    @FXML
    public void switchToTransactionMakingScreen() throws IOException {
        switchScreen("MakeTransactionScreen");
    }
    @FXML
    public void switchToAuthorizationScreen() throws IOException {
        switchScreen("AuthorizationScreen");
    }

    @FXML
    public void switchScreen(String screenName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(screenName + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage window = (Stage) signOutButton.getScene().getWindow();
        window.setScene(scene);
        window.sizeToScene();
        window.setMaximized(false);
        window.setMaximized(true);
    }

}
