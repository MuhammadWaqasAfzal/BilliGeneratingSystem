package org.visualobjectsoftware.intellijaassignment.bill.history;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.Main;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.bill.BillModel;
import org.visualobjectsoftware.intellijaassignment.interfaces.DataReturnCallback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class BillHistoryController implements DataReturnCallback {


    @FXML
    private TextField searchField;

    @FXML
    private TableView<BillModel> historyTableView;

    @FXML
    private TableColumn<BillModel, String> emailColumn;

    @FXML
    private TableColumn<BillModel, String> billTypeColumn;

    @FXML
    private TableColumn<BillModel, String> meterNoColumn;

    @FXML
    private TableColumn<BillModel, String> totalUnitsColumn;

    @FXML
    private TableColumn<BillModel, String> amountColumn;

    @FXML
    private TableColumn<BillModel, String> billingPeriodFromColumn;

    @FXML
    private TableColumn<BillModel, String> billingPeriodToColumn;


    @FXML
    private Button setDateFilter;

    private ObservableList<BillModel> bills;

    @FXML
    private void initialize() {
        initializeTableColumns();
        loadData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));
    }

    private void filterData(String searchText) {
        ObservableList<BillModel> filteredList = FXCollections.observableArrayList();

        for (BillModel bill : bills) {
            if (bill.getUserEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                    bill.getType().toLowerCase().contains(searchText.toLowerCase()) ||
                    bill.getMeterNO().toLowerCase().contains(searchText.toLowerCase()) ||
                    bill.getTotalUnits().toLowerCase().contains(searchText.toLowerCase()) ||
                    bill.getBillAmount().toLowerCase().contains(searchText.toLowerCase())){
                filteredList.add(bill);
            }
        }

        historyTableView.setItems(filteredList);
    }

    private void initializeTableColumns() {


        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserEmail()));
        billTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        meterNoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeterNO()));
        totalUnitsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalUnits()));
        amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBillAmount()));
        billingPeriodFromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().toString() ));
        billingPeriodToColumn.setCellValueFactory(cellData -> new SimpleStringProperty(  cellData.getValue().getEndDate().toString()));



    }

    private void loadData() {

        List<BillModel> billsList = DataUtils.getBillsFromFile(Constants.BILLS_FILE_PATH);
        bills = FXCollections.observableArrayList(billsList);

        historyTableView.setItems(bills);
    }

    @FXML
    private void handleBackButtonClick() {
        Main.setScene("DashboardPage.fxml",600,600,600,600);
    }

    @FXML
    private void setDateFilterButtonClick() {
        if(setDateFilter.getText().equals("Clear Filter"))
            clearFilter();
        else
            loadFilterDialogBox();

    }

    private void clearFilter() {
        historyTableView.setItems(bills);
        setDateFilter.setText("Date Filter");
    }

    private void loadFilterDialogBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlURL = getClass().getResource("/org/visualobjectsoftware/intellijaassignment/FilterDialogBox.fxml");
            Parent root = null;
            if (fxmlURL != null) {
                root = loader.load(fxmlURL.openStream());
            } else {
                System.err.println("FXML file not found");
            }
            Stage dialogStage = new Stage();

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) historyTableView).getScene().getWindow());
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            FilterDialogBoXController filterDialogBoXController = loader.getController();
            filterDialogBoXController.setDialogStage(dialogStage);
            filterDialogBoXController.setCallback(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDataReturn(LocalDate startDate,LocalDate endDate) {


        ObservableList<BillModel> filteredList = FXCollections.observableArrayList();


        if(startDate==null && endDate == null) {
            historyTableView.setItems(bills);
        }
        else{
            setDateFilter.setText("Clear Filter");
            for (BillModel bill : bills) {
                if (endDate == null) {
                    if ((startDate.isBefore(bill.getStartDate()) || startDate.isEqual(bill.getStartDate()))) {
                        filteredList.add(bill);
                    }
                } else if (startDate == null) {
                    if ((endDate.isAfter(bill.getEndDate()) || endDate.isEqual(bill.getEndDate()))) {
                        filteredList.add(bill);
                    }
                } else {
                    if ((startDate.isBefore(bill.getStartDate()) || startDate.isEqual(bill.getStartDate()))
                            && (endDate.isAfter(bill.getEndDate()) || endDate.isEqual(bill.getEndDate()))) {
                        filteredList.add(bill);
                    }
                }
            }

            historyTableView.setItems(filteredList);
        }

    }



}
