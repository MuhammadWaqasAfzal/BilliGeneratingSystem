package org.visualobjectsoftware.intellijaassignment.bill.history;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.interfaces.DataReturnCallback;

import java.time.LocalDate;

public class FilterDialogBoXController {

    private Stage dialogStage;

    private DataReturnCallback callback;


    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void applyFilterButtonCLicked(){

        if (callback != null) {
            callback.onDataReturn(startDatePicker.getValue(),endDatePicker.getValue());
        }

        dialogStage.close();
    }

    public void setCallback(DataReturnCallback callback) {
        this.callback = callback;
    }

}
