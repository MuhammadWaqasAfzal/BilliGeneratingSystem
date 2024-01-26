package org.visualobjectsoftware.intellijaassignment.bill.electricity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;

import java.io.IOException;
import java.time.LocalDate;

public class ElectricBillDescriptionDialogBox {
    @FXML private VBox root;
    @FXML private RadioButton gasRadioButton;
    @FXML private RadioButton electricityRadioButton;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Label errorLabel;

    @FXML
    private TextField dayOpeningReadTextField;
    @FXML
    private TextField dayClosingReadTextField;

    @FXML
    private TextField nightOpeningReadTextField;
    @FXML
    private TextField nightClosingReadTextField;

    @FXML private Button generateButton;

    private Stage dialogStage;
    private CustomerModel customer;
    private boolean isGasBill = true;
    private LocalDate startDate;
    private LocalDate endDate;

    private ElectricityBillController controller;



    @FXML
    private void initialize() {

        startDatePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now().minusDays(1)));
            }
        });

        // Set the day cell factory for the end date picker
        endDatePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now()));
            }
        });

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void generateButtonClicked() {

       startDate = startDatePicker.getValue();
        endDate = endDatePicker.getValue();

        System.out.println(customer.getName());
        if(checkValidations()){
            ElectricityBillController.customer = this.customer;
            ElectricityBillController.dayOpeningRead = dayOpeningReadTextField.getText();
            ElectricityBillController.dayClosingRead = dayClosingReadTextField.getText();
            ElectricityBillController.nightOpeningRead = nightOpeningReadTextField.getText();
            ElectricityBillController.nightClosingRead = nightClosingReadTextField.getText();
            ElectricityBillController.startDate = startDate;
            ElectricityBillController.endDate = endDate;
            generateBill();

        }


    }

    private void generateBill() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/visualobjectsoftware/intellijaassignment/ElectricityBill.fxml"));
            Parent root = loader.load();

            controller = loader.getController();

            Stage newStage = new Stage();
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(dialogStage); // Set the current stage as the owner
            newStage.setMinWidth(800);
            newStage.setMaxWidth(800);

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            controller.setDialogStage(newStage);

            dialogStage.close();
            newStage.showAndWait(); // This will block until the new dialog is closed
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }


    private void updateErrorLabel(String message , Boolean visible) {
        errorLabel.setVisible(visible);
        errorLabel.setManaged(visible);
        errorLabel.setText(message);
    }
    private boolean checkValidations() {
        if (startDate==null){
            updateErrorLabel("Start date must be selected.",true);
            return false;
        }
        if(endDate == null){
            updateErrorLabel("End date must be selected.",true);
            return false;
        }
        if(dayOpeningReadTextField==null){
            updateErrorLabel("Day Opening Read value is required for generating bill.",true);
        }
        if(dayClosingReadTextField==null){
            updateErrorLabel("Day Closing Read value is required for generating bill.",true);
        }
        if(nightOpeningReadTextField==null){
            updateErrorLabel("Night Opening Read value is required for generating bill.",true);
        }
        if(nightClosingReadTextField==null){
            updateErrorLabel("Night Closing Read value is required for generating bill.",true);
        }
        updateErrorLabel("",false);


        return true;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;

    }

}

