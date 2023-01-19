module com.pppfkp.javabank {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pppfkp.javabank to javafx.fxml;
    exports com.pppfkp.javabank;
}