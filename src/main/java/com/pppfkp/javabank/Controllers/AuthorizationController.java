package com.pppfkp.javabank.Controllers;

import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Repositories.UserRepository;
import com.pppfkp.javabank.Services.SignInService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable {
    private SignInService signInService;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    @FXML
    private VBox validationErrorsBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CurrentUserAppState.Clear();
        signInService = new SignInService(new UserRepository(HibernateConnectUtility.getSessionFactory()));
    }
    private boolean validateLogin(String login) {
        return true;
    };
    private boolean validatePassword(String password) {
        return true;
    }

    @FXML
    public void signIn(ActionEvent e) {
        validationErrorsBox.getChildren().clear();
        String login = loginField.getText();
        String password = passwordField.getText();

        if (!validateLogin(login) || !validatePassword(password)) return;

        if (signInService.SignIn(login, password)) {

        } else {
            for (String error : signInService.getErrors()) {
                validationErrorsBox.getChildren().add(new Label(error));

            }

        }
    }

}