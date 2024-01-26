package org.visualobjectsoftware.intellijaassignment.tariff;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.utility.Toast;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;

import java.util.ArrayList;
import java.util.List;

import static org.visualobjectsoftware.intellijaassignment.Main.primaryStage;


public class AddTariffDialogController {

    private TariffController tariffController;

    @FXML
    private Label errorText;

    @FXML
    private Label dayLabel;

    @FXML
    private Label label;
    
    @FXML
    private CheckBox electricityCheckBox;

    @FXML
    private CheckBox gasCheckBox;
    
    @FXML
    private CheckBox directDebitCheckBox;
    
    @FXML
    private CheckBox standingOrderCheckBox;
    
    @FXML
    private CheckBox cashCheckBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField dayRateTextField;

    @FXML
    private TextField nightRateTextField;

    @FXML
    private TextField vatTextField;

    @FXML
    private TextField standingChargeTextField;



    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private TariffModel newTariff;
    
    private Stage dialogStage;
    
    private List<TariffModel> tariffList = new ArrayList<>();

    @FXML
    private void initialize() {

    }

    @FXML
    private void saveButtonClicked() {
        handleSave();
    }

    @FXML
    private void cancelButtonClicked() {
        handleCancel();
    }

    private void handleSave() {
        if (isInputValid()) {

            if(getSelectedTypes().equals(Constants.GAS)){
                nightRateTextField.setText("----");
            }
        	newTariff = new TariffModel(nameTextField.getText(),getSelectedTypes(),dayRateTextField.getText(),nightRateTextField.getText(),
        			vatTextField.getText(),standingChargeTextField.getText(), getPaymentMethodType());

          
            // Update the tariff list
            tariffList.add(newTariff);

            // Save the updated list to the file (you need to implement this method)
            DataUtils.saveTariffListToFile(new ArrayList<>(tariffList),Constants.TARIFF_FILE_PATH);

          //  Utils.saveTariffListToFile(tariffList);
            
            tariffController.loadTariffs();

            

            dialogStage.close();

            Toast.show(primaryStage, "Tariff added successfully.");

        }
    }

    private String getSelectedTypes() {
        StringBuilder types = new StringBuilder();
        if (electricityCheckBox.isSelected()) {
            types.append(Constants.ELECTRICITY);
        }
        if (gasCheckBox.isSelected()) {
            types.append(Constants.GAS);
        }
        return types.toString().trim();
    }
    
    private String getPaymentMethodType() {
        StringBuilder types = new StringBuilder();
        if (directDebitCheckBox.isSelected()) {
            types.append("Direct Debit");
        }
        if (standingOrderCheckBox.isSelected()) {
            types.append("Standing Order");
        }
        if (cashCheckBox.isSelected()) {
            types.append("Cash");
        }
        return types.toString().trim();
    }
    

    private void handleCancel() {
        dialogStage.close();
    }


    private void updateErrorLabel(String message , Boolean visible) {
        errorText.setVisible(visible);
        errorText.setManaged(visible);
        errorText.setText(message);
    }

    private boolean isInputValid() {
    	
    	 if (nameTextField.getText().isBlank())
    	 {
             updateErrorLabel("Tariff name is required.",true);
    		 return false;
    	 }
    	 if(dayRateTextField.getText().isBlank()) {
             updateErrorLabel("Tariff day rate is required.",true);
    		 return false;
    	 }
        if(nightRateTextField.getText().isBlank() && getSelectedTypes().equals(Constants.ELECTRICITY)) {
            updateErrorLabel("Tariff night rate is required.",true);
            return false;
        }
    	 if (!(electricityCheckBox.isSelected() || gasCheckBox.isSelected()) ) {
             updateErrorLabel("Tariff type is required.",true);
    		 return false;
         }
    	 if(vatTextField.getText().isBlank()) {
             updateErrorLabel("Vat tax value is required.",true);
    		 return false;
    	 }

        if(standingChargeTextField.getText().isBlank()) {
            updateErrorLabel("Standing charge value is required.",true);
            return false;
        }


        for (TariffModel tariffModel:tariffList){
            if(nameTextField.getText().equals(tariffModel.getName())){
                updateErrorLabel("This tariff already exists.",true);
                return false;
            }
        }

        updateErrorLabel("This tariff already exists.",false);
        // You can add validation logic here if needed
        return true;
    }
    
    @FXML
    private void onElectricityCheck(){
    	electricityCheckBox.setSelected(true);
    	gasCheckBox.setSelected(false);
        dayLabel.setText("Day Rate");


        nightRateTextField.setVisible(true);
        nightRateTextField.setManaged(true);
        label.setVisible(true);
        label.setManaged(true);
    }
    
    @FXML
    private void onGasCheck(){
    	electricityCheckBox.setSelected(false);
    	gasCheckBox.setSelected(true);
        dayLabel.setText("Rate");

        nightRateTextField.setVisible(false);
        nightRateTextField.setManaged(false);
        label.setVisible(false);
        label.setManaged(false);

    }

    
    @FXML
    private void ondirectDebitCheck(){
    	directDebitCheckBox.setSelected(true);
    	standingOrderCheckBox.setSelected(false);
    	cashCheckBox.setSelected(false);
    }
    
    @FXML
    private void onStandingOrderCheck(){
    	directDebitCheckBox.setSelected(false);
    	standingOrderCheckBox.setSelected(true);
    	cashCheckBox.setSelected(false);
    }
    
    @FXML
    private void onCashCheck(){
    	directDebitCheckBox.setSelected(false);
    	standingOrderCheckBox.setSelected(false);
    	cashCheckBox.setSelected(true);
    }



    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }



    
    public void setTariffList(List<TariffModel> tariffList) {
        if(tariffList!=null) {
            this.tariffList = FXCollections.observableArrayList(tariffList);
        }

    }


    
    public void setTariffController(TariffController tariffController) {
        this.tariffController = tariffController;
    }
    
 
}

