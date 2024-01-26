package org.visualobjectsoftware.intellijaassignment.selectCustomer;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.Main;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.bill.electricity.ElectricBillDescriptionDialogBox;
import org.visualobjectsoftware.intellijaassignment.bill.gas.GasBillDescriptionDialogBox;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;

public class SelectCustomerController {

    @FXML
    private Button generateBillButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<CustomerModel> customerTableView;

    @FXML
    private TableColumn<CustomerModel, Boolean> selectColumn;

    @FXML
    private TableColumn<CustomerModel, String> nameColumn;

    @FXML
    private TableColumn<CustomerModel, String> emailColumn;

    @FXML
    private TableColumn<CustomerModel, String> addressColumn;

    @FXML
    private TableColumn<CustomerModel, String> postcodeColumn;

    @FXML
    private TableColumn<CustomerModel, String> electricMeterNoColumn;

    @FXML
    private TableColumn<CustomerModel, String> gasMeterNoColumn;

    @FXML
    private TableColumn<CustomerModel, String> tariffColumn;

    private ObservableList<CustomerModel> customers;

    @FXML
    private void initialize() {
        initializeTableColumns();
        loadData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));


    }

    private void filterData(String searchText) {
        ObservableList<CustomerModel> filteredList = CustomerFilter.filterCustomers(customers, searchText);
        customerTableView.setItems(filteredList);
    }


    private int lastSelectedIndex = -1;
    private void initializeTableColumns() {

        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(new Callback<TableColumn<CustomerModel, Boolean>, TableCell<CustomerModel, Boolean>>() {
            @Override
            public TableCell<CustomerModel, Boolean> call(TableColumn<CustomerModel, Boolean> param) {
                CheckBoxTableCell<CustomerModel, Boolean> cell = new CheckBoxTableCell<>();

                // Add an event handler to handle mouse clicks on the checkbox
                cell.setOnMouseClicked(event -> {
                    if (!cell.isEmpty()) {
                        int selectedIndex = cell.getIndex();

                        if (lastSelectedIndex != -1) {
                            CustomerModel lastSelectedCustomer = customerTableView.getItems().get(lastSelectedIndex);
                            lastSelectedCustomer.setSelected(false);
                        }

                        if(lastSelectedIndex!=selectedIndex) {

                            // Access the associated item and update its selected property
                            CustomerModel rowData = customerTableView.getItems().get(cell.getIndex());
                            rowData.setSelected(!rowData.isSelected());
                            // Update the last selected index
                            lastSelectedIndex = selectedIndex;
                            handleCheckboxClick(rowData);

                        }
                        else {
                            lastSelectedIndex = -1;
                        }
                            boolean flag = false;
                            for (CustomerModel customer : customers) {
                                if (customer.isSelected()) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                generateBillButton.setVisible(true);
                            } else {
                                generateBillButton.setVisible(false);
                            }

                        customerTableView.refresh();


                    }
                   
                });

                return cell;
            }
        });

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        postcodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostCode()));
        electricMeterNoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getElectricMeterNo()));
        gasMeterNoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGasMeterNo()));
        if(Constants.GENERATE_ELECTRIC_BILL)
            tariffColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getElectricTariff()));
        else
            tariffColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGasTariff()));


    }

    private void loadData() {

        //customers = Utils.getCustomersFromFile();
        List<CustomerModel> customerList = DataUtils.getCustomersFromFile(Constants.CUSTOMER_FILE_PATH);
        customers = FXCollections.observableArrayList(customerList);

        for ( CustomerModel c: customers){
            c.selected = new SimpleBooleanProperty(false);
        }
        customerTableView.setItems(customers);
    }

    @FXML
    private void handleBackButtonClick() {
        Main.setScene("DashboardPage.fxml",600,600,600,600);
    }

    @FXML
    private void generateBillButtonClicked() {
        // You can access selected customers using the 'customers' list
        for (CustomerModel customer : customers) {
            if (customer.isSelected()) {
                //System.out.println("Selected Customer: " + customer.getName());
            }
        }

        if(Constants.GENERATE_ELECTRIC_BILL) {
            try {

                FXMLLoader loader = new FXMLLoader();
                URL fxmlURL = getClass().getResource("/org/visualobjectsoftware/intellijaassignment/GetElectricBillDescriptionDialogBox.fxml");
                Parent root = null;
                if (fxmlURL != null) {
                    root = loader.load(fxmlURL.openStream());
                } else {
                    System.err.println("FXML file not found");
                }
                Stage dialogStage = new Stage();

                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(((Node) customerTableView).getScene().getWindow());
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                ElectricBillDescriptionDialogBox billDescriptionController = loader.getController();
                billDescriptionController.setDialogStage(dialogStage);
                billDescriptionController.setCustomer(customers.get(lastSelectedIndex));


                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {

                FXMLLoader loader = new FXMLLoader();
                URL fxmlURL = getClass().getResource("/org/visualobjectsoftware/intellijaassignment/GetGasBillDescriptionDialogBox.fxml");
                Parent root = null;
                if (fxmlURL != null) {
                    root = loader.load(fxmlURL.openStream());
                } else {
                    System.err.println("FXML file not found");
                }
                Stage dialogStage = new Stage();

                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(((Node) customerTableView).getScene().getWindow());
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                GasBillDescriptionDialogBox billDescriptionController = loader.getController();
                billDescriptionController.setDialogStage(dialogStage);
                billDescriptionController.setCustomer(customers.get(lastSelectedIndex));


                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleCheckboxClick(CustomerModel rowData) {
    }

}
