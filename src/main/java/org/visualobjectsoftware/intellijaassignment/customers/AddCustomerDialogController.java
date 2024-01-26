package org.visualobjectsoftware.intellijaassignment.customers;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.utility.Toast;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.tariff.TariffModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.visualobjectsoftware.intellijaassignment.Main.primaryStage;

public class AddCustomerDialogController {

	private CustomerController customerController;

	@FXML
	private Label errorLabel;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField addressTextField;

	@FXML
	private TextField postCodeTextField;

	@FXML
	private TextField electricMeterNoTextField;
	
	@FXML
	private TextField gasMeterNoTextField;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	private Stage dialogStage;
	
	
	private CustomerModel newCustomer;
	private List<CustomerModel> customerList = new ArrayList<>();


	@FXML
	private ComboBox<String> tariffComboBox;
	@FXML
	private ComboBox<String> gasTariffComboBox;

	private ObservableList<String> tariffOptions;
	private ObservableList<String> gasTariffOptions;



	@FXML
	private void saveButtonClicked() {
		handleSave();
	}

	@FXML
	private void cancelButtonClicked() {
		handleCancel();
	}

	@FXML
	private void initialize() {
		// Initialize the tariff options (replace this with your actual tariff options list)

		List<TariffModel> tariffs = DataUtils.getTariffsFromFile(Constants.TARIFF_FILE_PATH);

		// Extract tariff names from the list of TariffModel objects
		tariffOptions = FXCollections.observableArrayList();
		gasTariffOptions = FXCollections.observableArrayList();
        assert tariffs != null;
        for (TariffModel tariff : tariffs) {
			if(Objects.equals(tariff.getType(), Constants.ELECTRICITY))
				tariffOptions.add(tariff.getName());
			else
				gasTariffOptions.add(tariff.getName());
		}


		// Populate the tariffComboBox with the tariff options
		tariffComboBox.setItems(tariffOptions);
		gasTariffComboBox.setItems(gasTariffOptions);
	}


	private void handleSave() {
		if (isInputValid() && isDataValid()) {
			newCustomer = new CustomerModel(nameTextField.getText(),emailTextField.getText(),
					addressTextField.getText(),postCodeTextField.getText(),electricMeterNoTextField.getText(),gasMeterNoTextField.getText(),tariffComboBox.getValue(),gasTariffComboBox.getValue());
			// Update the customer list
			customerList.add(newCustomer);

			// Save the updated list to the file (you need to implement this method)
			DataUtils.saveCustomer(new ArrayList<>(customerList),Constants.CUSTOMER_FILE_PATH);

			customerController.loadCustomers();

			dialogStage.close();

			Toast.show(primaryStage, "Customer created successfully.");
		}
	}



	private void handleCancel() {
		dialogStage.close();
	}


	private void updateErrorLabel(String message , Boolean visible) {
		errorLabel.setVisible(visible);
		errorLabel.setManaged(visible);
		errorLabel.setText(message);
	}

	private boolean isDataValid() {

		if(customerList!=null) {
			for (CustomerModel customerModel : customerList) {
				if (customerModel.getEmail().equals(emailTextField.getText())) {
					updateErrorLabel("Email already registered", true);
					return false;
				}
				if (customerModel.getElectricMeterNo().equals(electricMeterNoTextField.getText())) {
					updateErrorLabel("This electric meter is already registered", true);
					return false;
				}
				if (customerModel.getGasMeterNo().equals(gasMeterNoTextField.getText())) {
					updateErrorLabel("This gas meter is already registered", true);
					return false;
				}
			}
		}
		updateErrorLabel("",false);

		return true;
	}


	private boolean isInputValid() {
		// You can add validation logic here if needed
		if (nameTextField.getText().isBlank()) {
			updateErrorLabel("Name is required",true);
			return false;
		}
		if (emailTextField.getText().isBlank()) {
			updateErrorLabel("Email is required",true);
			return false;
		}
		if (addressTextField.getText().isBlank()) {
			updateErrorLabel("Address is required",true);
			return false;
		}
		if (postCodeTextField.getText().isBlank()) {
			updateErrorLabel("Post Code is required",true);
			return false;
		}
		if (electricMeterNoTextField.getText().isBlank()) {
			updateErrorLabel("Electric Meter No. is required",true);
			return false;
		}
		if (gasMeterNoTextField.getText().isBlank()) {
			updateErrorLabel("Gas Meter No. is required",true);
			return false;
		}
		updateErrorLabel("",false);
		return true;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	

	public void setCustomerList(List<CustomerModel> customerList) {
		if(customerList!=null)
			this.customerList = FXCollections.observableArrayList(customerList);
	}

	


	
	public void setCustomerController(CustomerController customerController) {
		this.customerController = customerController;
	}

	

}
