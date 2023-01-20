package com.pppfkp.javabank;

import com.pppfkp.javabank.Data.Connection.HibernateConnectUtility;
import com.pppfkp.javabank.Data.DTOs.UserDTO;
import com.pppfkp.javabank.Services.Repositories.UserRepository;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

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
        //CREATE test
        UserDTO newUser = new UserDTO("pppfkp1", "daniel", "wojtowicz", "kuba@gej.pl", true, "111111111", "01262201499", LocalDate.of(2001,10,5), true, "Słotowa", "Rzeszow", "33162", "Lukasiewicza", "2", "115", null, "21376969");

        Integer createdUserId = userRepository.CreateUser(newUser);
        System.out.println(createdUserId);
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
        System.out.println(userRepository.AuthenticateUser("pppfkp1", "21376969"));






        HibernateConnectUtility.CloseConnection();
        System.exit(0);
    }
}