package com.pppfkp.javabank;

import com.pppfkp.javabank.Data.Cache.CurrentUserAppState;
import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Repositories.AccountRepository;
import com.pppfkp.javabank.Repositories.AccountTypeRepository;
import com.pppfkp.javabank.Repositories.UserRepository;
import com.pppfkp.javabank.Services.SignUpService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpScreenController implements Initializable {
    private SignUpService signUpService;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField peselField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private Button signUpButton;
    @FXML
    private VBox errorBox;
    @FXML
    private Button goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpService = new SignUpService(new UserRepository(HibernateConnectUtility.getSessionFactory()), new AccountRepository(HibernateConnectUtility.getSessionFactory()), new AccountTypeRepository(HibernateConnectUtility.getSessionFactory()));
        CurrentUserAppState.Refresh();
        //GET VALUES


        //VALIDATE

        //
    }
    @FXML
    public void signUp() {
        errorBox.getChildren().clear();
        //get fields
        String login = loginField.getText();
        String password = passwordField.getText();;
        String phoneNumber = phoneNumberField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String pesel = peselField.getText();
        String email = emailField.getText();
        LocalDate birthdate = birthdatePicker.getValue();

        //validate
            //login
        if (login.equals("")) {
            errorBox.getChildren().add(new Label("Pole login nie może być puste"));
            return;
        }
            //haslo
        if (password.equals("")) {
            errorBox.getChildren().add(new Label("Pole haslo nie może być puste"));
            return;
        }
            //numer telefonu
        if (!phoneNumber.matches("^\\d{9}$")) {
            errorBox.getChildren().add(new Label("pole numer telefonu nie jest poprawne!"));
            return;
        }
            //Imię
        if (!firstName.matches("^[A-Z][a-z]+$")) {
            errorBox.getChildren().add(new Label("Pole imie ma nieprawidłowy format!"));
            return;
        }
            //Nazwisko
        if (!lastName.matches("^[A-Z][a-z]+$")) {
            errorBox.getChildren().add(new Label("Pole nazwisko ma nieprawidłowy format!"));
            return;
        }
            //Pesel
        if (!pesel.matches("\\d{11}$")) {
            errorBox.getChildren().add(new Label("Pole pesel ma nieprawidłowy format!"));
            return;
        }
            //Email
        Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        if (!email.matches(EMAIL_REGEX.pattern())) {
            errorBox.getChildren().add(new Label("Pole email ma nieprawidłowy format!"));
            return;
        }
            //Birthdate
        if (birthdate == null  || birthdate.isBefore(LocalDate.of(1900,1,1)) || birthdate.isAfter(LocalDate.now())) {
            errorBox.getChildren().add(new Label("Niepoprawna data urodzenia!"));
            return;
        }
        UserDTO dto = new UserDTO(login, password,firstName,lastName,phoneNumber,pesel,email, birthdate);
        if (!dto.ValidateAll().isEmpty()) {
            errorBox.getChildren().add(new Label("Błąd rejestracji!"));
            return;
        }
        if (signUpService.SignUp(dto) == null) {
            errorBox.getChildren().add(new Label("Błąd rejestracji!"));
            return;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Zarejestrowano pomyślnie!");
        a.show();
    }
    @FXML
    public void switchToSignIn() throws IOException {
        switchScreen("AuthorizationScreen");
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

}
