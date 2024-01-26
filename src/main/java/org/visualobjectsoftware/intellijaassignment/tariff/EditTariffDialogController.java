package org.visualobjectsoftware.intellijaassignment.tariff;

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


public class EditTariffDialogController {

    private TariffController tariffController;

    @FXML
    private Label errorText;
    
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

    private Stage dialogStage;
    private TariffModel selectedTariff;
    private List<TariffModel> tariffList;

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
            selectedTariff.setName(nameTextField.getText());
            selectedTariff.setDayRate(dayRateTextField.getText());
            selectedTariff.setNightRate(nightRateTextField.getText());
           	selectedTariff.setVat(vatTextField.getText());
            selectedTariff.setStandingCharge(standingChargeTextField.getText());

            // Handle checkbox states
            selectedTariff.setType(getSelectedTypes());
            selectedTariff.setPaymentMethod(getPaymentMethodType());

            // Update the tariff list
            tariffList.set(tariffList.indexOf(selectedTariff), selectedTariff);
       
            // Save the updated list to the file (you need to implement this method)
            DataUtils.saveTariffListToFile(tariffList,Constants.TARIFF_FILE_PATH);
            
            tariffController.loadTariffs();

            

            dialogStage.close();
            Toast.show(primaryStage, "Tariff edited successfully.");

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

    private boolean isInputValid() {
        // You can add validation logic here if needed
    	 if (nameTextField.getText().isBlank())
    	 {
    		 errorText.setText("Tariff name is required");
    		 return false;
    	 }
    	 if(dayRateTextField.getText().isBlank()) {
    		 errorText.setText("Tariff day rate is required");
    		 return false;
    	 }
         if(nightRateTextField.getText().isBlank()) {
    		 errorText.setText("Tariff night rate is required");
    		 return false;
    	 }
    	 if(vatTextField.getText().isBlank()) {
    		 errorText.setText("Vat tax value is required");
    		 return false;
    	 }
         if(standingChargeTextField.getText().isBlank()) {
    		 errorText.setText("Standing Charge tax value is required");
    		 return false;
    	 }

    	 if (!(electricityCheckBox.isSelected() || gasCheckBox.isSelected()) ) {
    		 errorText.setText("Tariff type is required");
    		 return false;
         }
    	 
    	 if (!(directDebitCheckBox.isSelected() || standingOrderCheckBox.isSelected() || cashCheckBox.isSelected()) ) {
    		 errorText.setText("Payment Method is required");
    		 return false;
         }
         

        // You can add validation logic here if needed
        return true;
        
    }



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSelectedTariff(TariffModel selectedTariff) {
        this.selectedTariff = selectedTariff;
        updateFields();
    }

    
    public void setTariffList(List<TariffModel> tariffList) {
       // this.tariffList = FXCollections.observableArrayList(tariffList);
        this.tariffList = new ArrayList<>(tariffList);

    }


    private void updateFields() {
        if (selectedTariff != null) {
            nameTextField.setText(selectedTariff.getName());
            dayRateTextField.setText(String.valueOf(selectedTariff.getDayRate()));
            nightRateTextField.setText(String.valueOf(selectedTariff.getNightRate()));
            vatTextField.setText(String.valueOf(selectedTariff.getVat()));
            standingChargeTextField.setText(String.valueOf(selectedTariff.getStandingCharge()));

            String[] types = selectedTariff.getType().split(" ");
            electricityCheckBox.setSelected(false);
            gasCheckBox.setSelected(false);
            for (String type : types) {
                switch (type.toUpperCase()) {
                    case "ELECTRICITY":
                        electricityCheckBox.setSelected(true);
                        break;
                    case "GAS":
                        gasCheckBox.setSelected(true);
                        break;
                }
            }
            

            String payment = selectedTariff.getPaymentMethod();
            directDebitCheckBox.setSelected(false);
            standingOrderCheckBox.setSelected(false);
            cashCheckBox.setSelected(false);
            System.out.println(payment);

           
            switch (payment.toUpperCase()) {
	            case "DIRECT DEBIT":
	            	directDebitCheckBox.setSelected(true);
	                break;
	            case "STANDING ORDER":
	            	standingOrderCheckBox.setSelected(true);
	                break;
	            case "CASH":
	            	cashCheckBox.setSelected(true);
	                break;
            }
            
        }
    }
    
    public void setTariffController(TariffController tariffController) {

        this.tariffController = tariffController;

    }
    
    @FXML
    private void onElectricityCheck(){
    	electricityCheckBox.setSelected(true);
    	gasCheckBox.setSelected(false);
    }
    
    @FXML
    private void onGasCheck(){
    	electricityCheckBox.setSelected(false);
    	gasCheckBox.setSelected(true);
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
    
}
