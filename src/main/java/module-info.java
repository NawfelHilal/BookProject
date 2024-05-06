module com.example.bookproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.controlsfx.controls;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.example.bookproject to javafx.fxml;
    exports com.example.bookproject;
    opens com.example.bookproject.Model to org.hibernate.orm.core;
    exports com.example.bookproject.Model;
    exports org.hibernate.mydialect to org.hibernate.orm.core;

}