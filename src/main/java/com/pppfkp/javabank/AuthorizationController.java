package com.pppfkp.javabank;

import com.pppfkp.javabank.App;
import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Repositories.UserRepository;
import com.pppfkp.javabank.Services.SignInService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private Button signUpButton;

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
    public void signIn(ActionEvent e) throws IOException {
        validationErrorsBox.getChildren().clear();
        String login = loginField.getText();
        String password = passwordField.getText();

        if (!validateLogin(login) || !validatePassword(password)) return;

        if (signInService.SignIn(login, password)) {
            switchToMainScreen();
        } else {
            for (String error : signInService.getErrors()) {
                validationErrorsBox.getChildren().add(new Label(error));
            }
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Zalogowano!");
        a.show();
    }

    @FXML
    public void switchToMainScreen() throws IOException {
        switchScreen("MainScreen");
    }

    @FXML
    public void switchToSignUpScreen() throws IOException {
        switchScreen("SignUpScreen");
    }

    @FXML
    public void switchScreen(String screenName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(screenName + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage window = (Stage) signInButton.getScene().getWindow();
        window.setScene(scene);
        window.sizeToScene();
        window.setMaximized(false);
        window.setMaximized(true);
    }
}