package org.visualobjectsoftware.intellijaassignment.settings;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import javafx.fxml.Initializable;
import org.visualobjectsoftware.intellijaassignment.Main;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.utility.Toast;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;

import java.util.ResourceBundle;

import static org.visualobjectsoftware.intellijaassignment.Main.primaryStage;


public class SettingsController implements Initializable{


    @FXML
    private TextField userEmailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField correctionFactorTextField;

    @FXML
    private TextField calorificValueTExtField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize or set default values here

        ConstantsModel constantsModel = DataUtils.getConstantFromFile(Constants.CONSTANTS_FILE_PATH);

        userEmailTextField.setText(constantsModel.getUserName());
        passwordTextField.setText((constantsModel.getPasssword()));
        correctionFactorTextField.setText((constantsModel.getCorrectionFactor()));
        calorificValueTExtField.setText(constantsModel.getCaloricFactor());
    }




    @FXML
    private void handleBackButtonClick() {
        Main.setScene("DashboardPage.fxml", 600, 600, 600, 600);
    }


    @FXML
    private void logoutButtonClicked() {
        Main.setScene("LoginView.fxml", 600, 600, 600, 600);
    }


    @FXML
    private void saveButtonClicked(){
        ConstantsModel constantsModel = new ConstantsModel(correctionFactorTextField.getText(),calorificValueTExtField.getText(),userEmailTextField.getText(),passwordTextField.getText());

        DataUtils.saveConstantsToFile(constantsModel,Constants.CONSTANTS_FILE_PATH);

        handleBackButtonClick();
        Toast.show(primaryStage, "Settings updated successfully");

    }
}
