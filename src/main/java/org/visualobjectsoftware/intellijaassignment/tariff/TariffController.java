package org.visualobjectsoftware.intellijaassignment.tariff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import org.visualobjectsoftware.intellijaassignment.utility.ConfirmBox;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.Main;
import org.visualobjectsoftware.intellijaassignment.utility.Toast;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;

import static org.visualobjectsoftware.intellijaassignment.Main.primaryStage;

public class TariffController {

    @FXML
    private TableView<TariffModel> tariffTableView;

    private static final String FILE_PATH = "tariff.txt";

    @FXML
    private TableColumn<TariffModel, String> nameColumn;

    @FXML
    private TableColumn<TariffModel, String> typeColumn;

    @FXML
    private TableColumn<TariffModel, Double> dayRateColumn;
    @FXML
    private TableColumn<TariffModel, Double> nightRateColumn;
    
    @FXML
    private TableColumn<TariffModel, Double> vatColumn;

    @FXML
    private TableColumn<TariffModel, Double> standingChargeColumn;



    @FXML
    private TableColumn<TariffModel, Double> paymentMethodColumn;

    
    @FXML
    private TableColumn<TariffModel, Button> editColumn;
    
    @FXML
    private TableColumn<TariffModel, Button> deleteColumn;

    private List<TariffModel> tariffList = new ArrayList<>();

    @FXML
    private void initialize() {
        createFileIfNotExists();
        setupTableColumns();
        loadTariffs();
        tariffTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void createFileIfNotExists() {
        try {
            Path filePath = Paths.get(FILE_PATH);
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openEditDialog(TariffModel selectedTariff, int i) {
        try {

            FXMLLoader loader = new FXMLLoader();
            URL fxmlURL = getClass().getResource("/org/visualobjectsoftware/intellijaassignment/EditTariffDialog.fxml");
            Parent root = null;
            if (fxmlURL != null) {
                root = loader.load(fxmlURL.openStream());
            } else {
                System.err.println("FXML file not found");
            }


            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) tariffTableView).getScene().getWindow());
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditTariffDialogController editDialogController = loader.getController();
            editDialogController.setDialogStage(dialogStage);
            editDialogController.setTariffController(this); // Pass the reference to TariffController
            editDialogController.setSelectedTariff(selectedTariff);
            
           

            editDialogController.setTariffList(tariffList);
//            editDialogController.setSelectedTariff(selectedTariff);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dayRateColumn.setCellValueFactory(new PropertyValueFactory<>("dayRate"));
        nightRateColumn.setCellValueFactory(new PropertyValueFactory<>("nightRate"));
        vatColumn.setCellValueFactory(new PropertyValueFactory<>("vat"));
        standingChargeColumn.setCellValueFactory(new PropertyValueFactory<>("standingCharge"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));


        String style = "-fx-alignment: CENTER;";

        String editButtonStyle = "-fx-alignment: CENTER; -fx-padding: 5px; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #000075; -fx-text-fill: white;";
		String deleteButtonStyle = " -fx-padding: 5px; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #A30000; -fx-text-fill: white;";
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

//                    editButton.setMaxHeight(30);
//                    editButton.setMaxWidth(60);
                    editButton.setOnAction(event -> {
                        TariffModel selectedTariff = getTableView().getItems().get(getIndex());
                        if (selectedTariff != null) {
                            openEditDialog(selectedTariff,getIndex());
                        } else {
                            System.out.println("No Tariff Selected");
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

//                    deleteButton.setMaxHeight(30);
//                	deleteButton.setMaxWidth(80);
                	deleteButton.setOnAction(event -> {
                        TariffModel selectedTariff = getTableView().getItems().get(getIndex());

                        ObservableList<String> tariffList = getAlternativeTariffList(selectedTariff);
                        ObservableList<CustomerModel> customersList = getCustomersOfTariff(selectedTariff);
                        Boolean showTariffDropDown = false;
//                        if(customersList.size()>0){
//                            showTariffDropDown = true;
//                        }
                		if(ConfirmBox.display("Are you sure you want to delete this tariff?",showTariffDropDown, (ObservableList<String>) tariffList)){
	                        if (selectedTariff != null ) {
                                if(Constants.SELECTED_TARIFF!=null)
                                    replaceTariff(customersList,selectedTariff);
	                        	deleteTariff(getIndex());
	                            System.out.println("Delete");
                                Toast.show(primaryStage, "Tariff deleted successfully.");

                                Constants.SELECTED_TARIFF = null;
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

    private void replaceTariff(ObservableList<CustomerModel> customersList, TariffModel selectedTariff) {
        customersList.forEach(customer ->{
            if(customer.getElectricTariff().equals(Constants.SELECTED_TARIFF) ){
                customer.setElectricTariff(Constants.SELECTED_TARIFF);
            }
            if(customer.getElectricTariff().equals(Constants.SELECTED_TARIFF) ){
                customer.setGasTariff(Constants.SELECTED_TARIFF);
            }
        });

        DataUtils.saveCustomer(customersList,Constants.CUSTOMER_FILE_PATH);
    }

    private ObservableList<String> getAlternativeTariffList(TariffModel selectedTariff) {
        ObservableList<String> alternativeTariffs = FXCollections.observableArrayList();

            tariffList.forEach(tariffModel -> {
                if(!tariffModel.getName().equals(selectedTariff.getName())){

                if(selectedTariff.getType().equals(Constants.ELECTRICITY)) {
                    if (tariffModel.getType().equals(Constants.ELECTRICITY)) {
                        alternativeTariffs.add(tariffModel.getName());
                    }
                }
                else {
                    if (tariffModel.getType().equals(Constants.GAS)) {
                        alternativeTariffs.add(tariffModel.getName());
                    }
                }
                }
            });
        return alternativeTariffs;
    }

    private ObservableList<CustomerModel> getCustomersOfTariff(TariffModel selectedTariff) {
        List<CustomerModel> customers = DataUtils.getCustomersFromFile(Constants.CUSTOMER_FILE_PATH);

        ObservableList<CustomerModel> c = FXCollections.observableArrayList();

        customers.forEach(customerModel -> {

                if(selectedTariff.getType().equals(Constants.ELECTRICITY)) {
                    if (selectedTariff.getName().equals(customerModel.getElectricTariff())) {
                         c.add(customerModel);
                    }
                }
                else {
                    if (selectedTariff.getName().equals(customerModel.getGasTariff())) {
                        c.add(customerModel);
                    }
                }

            });
        return c;
    }

    private void deleteTariff(int index) {
    	tariffList.remove(index);
        DataUtils.saveTariffListToFile(tariffList,Constants.TARIFF_FILE_PATH);
        loadTariffs();
    	
    }

    void loadTariffs() {
        List<TariffModel> tariffs = DataUtils.getTariffsFromFile(Constants.TARIFF_FILE_PATH);
        if (tariffs!=null) {
            tariffTableView.getItems().setAll(tariffs);
        }
        tariffList = tariffs;

    }
    
    @FXML
    private void addButtonClicked(){
    	
        try {

            FXMLLoader loader = new FXMLLoader();
            URL fxmlURL = getClass().getResource("/org/visualobjectsoftware/intellijaassignment/AddTariffDialog.fxml");
            Parent root = null;
            if (fxmlURL != null) {
                root = loader.load(fxmlURL.openStream());
            } else {
                System.err.println("FXML file not found");
            }


            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) tariffTableView).getScene().getWindow());
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddTariffDialogController addDialogController = loader.getController();
            addDialogController.setDialogStage(dialogStage);
            addDialogController.setTariffController(this); // Pass the reference to TariffController
            
            addDialogController.setTariffList(tariffList);

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
