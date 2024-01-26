package org.visualobjectsoftware.intellijaassignment.customers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.utility.ConfirmBox;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.Main;
import org.visualobjectsoftware.intellijaassignment.utility.Toast;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.visualobjectsoftware.intellijaassignment.Main.primaryStage;


public class CustomerController {

    @FXML
    private TableView<CustomerModel> customerTableView;


    @FXML
    private TableColumn<CustomerModel, String> nameColumn;

    @FXML
    private TableColumn<CustomerModel, String> emailColumn;

    @FXML
    private TableColumn<CustomerModel, String> addressColumn;

    @FXML
    private TableColumn<CustomerModel, String> postCodeColumn;

    @FXML
    private TableColumn<CustomerModel, String> electricMeterNoColumn;

    @FXML
    private TableColumn<CustomerModel, String> gasMeterNoColumn;

    @FXML
    private TableColumn<CustomerModel, String> tariffColumn;
    @FXML
    private TableColumn<CustomerModel, String> gasTariffColumn;

    @FXML
    private TableColumn<CustomerModel, Button> editColumn;
    
    @FXML
    private TableColumn<CustomerModel, Button> deleteColumn;

    private List<CustomerModel> customerList;

    @FXML
    private void initialize() {
        setupTableColumns();
        loadCustomers();
        customerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void openEditDialog(CustomerModel selectedTariff, int i) {
        try {


            FXMLLoader loader = new FXMLLoader();
            URL fxmlURL = getClass().getResource("/org/visualobjectsoftware/intellijaassignment/EditCustomerDialog.fxml");
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

            EditCustomerDialogController editCustomerDialogController = loader.getController();
            editCustomerDialogController.setDialogStage(dialogStage);
            editCustomerDialogController.setCustomerController(this); // Pass the reference to TariffController
            editCustomerDialogController.setSelectedCustomer(selectedTariff);
            
           

            editCustomerDialogController.setCustomerList(customerList);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        electricMeterNoColumn.setCellValueFactory(new PropertyValueFactory<>("electricMeterNo"));
        gasMeterNoColumn.setCellValueFactory(new PropertyValueFactory<>("gasMeterNo"));
        tariffColumn.setCellValueFactory(new PropertyValueFactory<>("electricTariff"));
        gasTariffColumn.setCellValueFactory(new PropertyValueFactory<>("gasTariff"));

        String style = "-fx-alignment: CENTER;";

		editColumn.setStyle(style);
		deleteColumn.setStyle(style);

        editColumn.setCellFactory(col -> {
            return new TableCell<>() {

                private  Button editButton = new Button();
                {

                    Image editImage = new Image(getClass().getResourceAsStream("/org/visualobjectsoftware/intellijaassignment/edit.png"));
                    ImageView editImageView = new ImageView(editImage);
                    editImageView.setFitHeight(20); // Adjust size as needed
                    editImageView.setFitWidth(20);

                    editButton.setGraphic(editImageView);
                    editButton.getStyleClass().add("general-button");

                    //  editButton.setMaxHeight(30);
                   // editButton.setMaxWidth(60);




                    editButton.setOnAction(event -> {
                        CustomerModel selectedTariff = getTableView().getItems().get(getIndex());
                        if (selectedTariff != null) {
                            openEditDialog(selectedTariff,getIndex());
                        } else {
                            System.out.println("No Customer Selected");
                        }
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setGraphic(editButton);
                    } else {
                        setGraphic(null);
                    }
                }
            };
        });
        
        deleteColumn.setCellFactory(col -> {
            return new TableCell<>() {
                private final Button deleteButton = new Button();
                {
                    Image deleteImage = new Image(getClass().getResourceAsStream("/org/visualobjectsoftware/intellijaassignment/trash.png"));
                    ImageView deleteImageView = new ImageView(deleteImage);
                    deleteImageView.setFitHeight(20); // Adjust size as needed
                    deleteImageView.setFitWidth(20);

                    deleteButton.setGraphic(deleteImageView);

                	deleteButton.getStyleClass().add("general-imageView-delete");
//                	deleteButton.setMaxHeight(30);
//                	deleteButton.setMaxWidth(80);
                	deleteButton.setOnAction(event -> {
                		if(ConfirmBox.display("Are you sure you want to delete this customer?",false,null)){
	                        CustomerModel selectedTariff = getTableView().getItems().get(getIndex());
	                        if (selectedTariff != null) {
	                        	deleteCustomer(getIndex());
	                            System.out.println("Delete");
                                Toast.show(primaryStage, "Customer deleted successfully.");

                            } else {
	                            System.out.println("No Tariff Deleted");
	                        }
                		}
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setGraphic(deleteButton);
                    } else {
                        setGraphic(null);
                    }
                }
            };
        });
        
        
    }
    
    private void deleteCustomer(int index) {
    	customerList.remove(index);
        DataUtils.saveCustomer(customerList,Constants.CUSTOMER_FILE_PATH);
        loadCustomers();
    	
    }

    void loadCustomers() {
            List<CustomerModel> customers = DataUtils.getCustomersFromFile(Constants.CUSTOMER_FILE_PATH);
            if(customers!=null) {
                customerTableView.getItems().setAll(customers);
            }
            customerList = customers;
    }
    
    @FXML
    private void addButtonClicked(){
    	
        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomerDialog.fxml"));
//            Parent root = loader.load();

            FXMLLoader loader = new FXMLLoader();
            URL fxmlURL = getClass().getResource("/org/visualobjectsoftware/intellijaassignment/AddCustomerDialog.fxml");
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

            AddCustomerDialogController addDialogController = loader.getController();
            addDialogController.setDialogStage(dialogStage);
            addDialogController.setCustomerController(this); // Pass the reference to TariffController
            
            addDialogController.setCustomerList(customerList);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    @FXML
    private void handleBackButtonClick() {
    	Main.setScene("DashboardPage.fxml",600,600,600,600);

    }
}
