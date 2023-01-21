module com.pppfkp.javabank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;
    requires jbcrypt;


    opens com.pppfkp.javabank to javafx.fxml;
    opens com.pppfkp.javabank.Data.Models;
    exports com.pppfkp.javabank;
}