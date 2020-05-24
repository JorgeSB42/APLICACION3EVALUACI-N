module com.mycompany.mavenproject1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires java.xml.bind;
    opens com.mycompany.mavenproject1.model to java.xml.bind;
    opens com.mycompany.mavenproject1.controller to javafx.fxml;
    exports com.mycompany.mavenproject1;
}