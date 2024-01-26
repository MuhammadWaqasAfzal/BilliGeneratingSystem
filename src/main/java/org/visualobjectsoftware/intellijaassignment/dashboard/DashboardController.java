package org.visualobjectsoftware.intellijaassignment.dashboard;

import javafx.fxml.FXML;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.Main;

public class DashboardController {

	@FXML
	private void manageCustomerButtonClicked() {
		Main.setScene("/org/visualobjectsoftware/intellijaassignment/CustomerManagement.fxml",1400,800,1400,800);
	}

		

	@FXML
	private void generateElectricBillButtonClicked() {
		System.out.println("electric");
		Constants.GENERATE_ELECTRIC_BILL = true;
		Main.setScene("/org/visualobjectsoftware/intellijaassignment/SelectCustomer.fxml",1200,600,1100,600);
	}

	@FXML
	private void generateGasBillButtonClicked() {
		System.out.println("gas");
		Constants.GENERATE_ELECTRIC_BILL = false;

		Main.setScene("/org/visualobjectsoftware/intellijaassignment/SelectCustomer.fxml",1200,600,1100,600);
	}
	
	@FXML
	private void manageTerrifsButtonClicked() {
		System.out.println("teriff");
		Main.setScene("/org/visualobjectsoftware/intellijaassignment/TariffManagement.fxml",1000,600,950,600);
	}

	@FXML
	private void generateBillHistoryButtonClicked() {
		System.out.println("history");
		Main.setScene("/org/visualobjectsoftware/intellijaassignment/BillHistory.fxml",1000,600,950,600);
	}

	@FXML
	private void analyticsButtonClicked() {
		System.out.println("analytics");
		Main.setScene("/org/visualobjectsoftware/intellijaassignment/Analytics.fxml",1000,600,950,600);
	}


	@FXML
	private void settingsButtonClicked() {
		Main.setScene("/org/visualobjectsoftware/intellijaassignment/SettingsView.fxml",600,400,600,400);


		//Main.setScene("/org/visualobjectsoftware/intellijaassignment/SettingsView.fxml",1400,800,1400,800);
	}


}
