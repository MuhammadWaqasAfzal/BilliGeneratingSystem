package org.visualobjectsoftware.intellijaassignment.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.visualobjectsoftware.intellijaassignment.settings.ConstantsModel;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.utility.Toast;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static org.visualobjectsoftware.intellijaassignment.Main.primaryStage;
import static org.visualobjectsoftware.intellijaassignment.Main.setScene;

public class LoginController  implements Initializable {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private ConstantsModel  constantsModel = DataUtils.getConstantFromFile(Constants.CONSTANTS_FILE_PATH);


    @FXML
    private void loginButtonAction(ActionEvent event) {
        // Implement your login logic here
        String email = emailField.getText();
        String password = passwordField.getText();

        // Check the email and password against system
        if (isValidUser(email, password)) {
            Toast.show(primaryStage, "Logged in successfully.");

            setScene("DashboardPage.fxml",600,600,600,600);

            System.out.println("Login successful");
        } else {
            // Failed login, show an error message or take appropriate action
            System.out.println("Login failed");
        }
    }

    private boolean isValidUser(String email, String password) {
        if(email.isEmpty()){
            updateErrorLabel("Email can not be empty.",true);
            return  false;
        } if(password.isEmpty()){
            updateErrorLabel("Password can not be empty.",true);
            return  false;
        } if(!email.equals(constantsModel.getUserName())){
            updateErrorLabel("Invalid Credential",true);
            return  false;
        } if(!password.equals(constantsModel.getPasssword())){
            updateErrorLabel("Invalid Credential",true);
            return  false;
        }
        updateErrorLabel("",false);

        return true;
    }

    private void updateErrorLabel(String message , Boolean visible) {
        errorLabel.setVisible(visible);
        errorLabel.setManaged(visible);
        errorLabel.setText(message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        constantsModel = DataUtils.getConstantFromFile(Constants.CONSTANTS_FILE_PATH);
    }
}