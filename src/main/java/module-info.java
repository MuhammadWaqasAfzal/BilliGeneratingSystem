module org.visualobjectsoftware.intellijaassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires org.testfx.junit5;
    requires org.testfx;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires java.mail;
    requires java.activation;


    opens org.visualobjectsoftware.intellijaassignment to javafx.fxml;
    exports org.visualobjectsoftware.intellijaassignment;

    exports org.visualobjectsoftware.intellijaassignment.dashboard;
    opens org.visualobjectsoftware.intellijaassignment.dashboard to javafx.fxml;

    exports org.visualobjectsoftware.intellijaassignment.customers;
    opens org.visualobjectsoftware.intellijaassignment.customers to javafx.fxml;

    exports org.visualobjectsoftware.intellijaassignment.tariff;
    opens org.visualobjectsoftware.intellijaassignment.tariff to javafx.fxml;

    exports org.visualobjectsoftware.intellijaassignment.selectCustomer;
    opens org.visualobjectsoftware.intellijaassignment.selectCustomer to javafx.fxml;

    exports org.visualobjectsoftware.intellijaassignment.bill.electricity;
    opens org.visualobjectsoftware.intellijaassignment.bill.electricity to javafx.fxml;

    exports org.visualobjectsoftware.intellijaassignment.bill.gas;
    opens org.visualobjectsoftware.intellijaassignment.bill.gas to javafx.fxml;

    opens org.visualobjectsoftware.intellijaassignment.bill.history to javafx.fxml;
    exports org.visualobjectsoftware.intellijaassignment.bill.history;

    opens org.visualobjectsoftware.intellijaassignment.analytics to javafx.fxml;
    exports org.visualobjectsoftware.intellijaassignment.analytics;


    exports org.visualobjectsoftware.intellijaassignment.login;
    opens org.visualobjectsoftware.intellijaassignment.login to javafx.fxml;


    exports org.visualobjectsoftware.intellijaassignment.settings;
    opens org.visualobjectsoftware.intellijaassignment.settings to javafx.fxml;
    exports org.visualobjectsoftware.intellijaassignment.utility;
    opens org.visualobjectsoftware.intellijaassignment.utility to javafx.fxml;


}