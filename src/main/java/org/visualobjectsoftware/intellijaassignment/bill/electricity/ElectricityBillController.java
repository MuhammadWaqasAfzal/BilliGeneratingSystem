package org.visualobjectsoftware.intellijaassignment.bill.electricity;


import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.bill.BillModel;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;
import org.visualobjectsoftware.intellijaassignment.tariff.TariffModel;
import org.visualobjectsoftware.intellijaassignment.utility.EmailUtility;
import org.visualobjectsoftware.intellijaassignment.utility.ScreenCaptureToPDF;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static eu.hansolo.tilesfx.tools.Helper.round;

public class ElectricityBillController  {
    @FXML
    private Label standingChargeValue;

    @FXML
    private Label usageChargeValue;

    @FXML
    private Label totalSupplyValue;

    @FXML
    private Label vatValue;

    @FXML
    private Label periodValue1;

    @FXML
    private Label periodValue2;
    @FXML
    private Label openingReadValue1;
    @FXML
    private Label closingReadValue1;
    @FXML
    private Label openingReadValue2;
    @FXML
    private Label closingReadValue2;

    @FXML
    private Label dayKwhValue;
    @FXML
    private Label nightKwhValue;
    @FXML
    private Label totalUnitsValue;

    @FXML
    private Label chargesPeriodValueDay;

    @FXML
    private Label nightUnitsValue;
    @FXML
    private Label nightRateValue;
    @FXML
    private Label nightCharge;
 @FXML
    private Label chargesPeriodValueNight;

    @FXML
    private Label dayUnitsValue;
    @FXML
    private Label dayRateValue;
    @FXML
    private Label dayCharge;
    @FXML
    private Label totalValue;
    @FXML
    private Label totalBill;
    @FXML
    private Label abc;

    @FXML
    private Label txt1;
    @FXML
    private Label txt2;

    @FXML
    private VBox contentBox;



    private Stage dialogStage;
    public static CustomerModel customer ;
    public static  String dayOpeningRead;
    public static  String dayClosingRead;

    public static  String nightOpeningRead;
    public static  String nightClosingRead;

    public static  LocalDate startDate;
    public static  LocalDate endDate;

    public static  String dayRate;
    public static  String nightRate;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }




    @FXML
    public void initialize() {
        TariffModel tariff = null ;

        for (TariffModel tariffModel : DataUtils.getTariffsFromFile(Constants.TARIFF_FILE_PATH)) {
                if(Objects.equals(tariffModel.getName(), customer.getElectricTariff())){
                    tariff = tariffModel;
                }
        }

        txt1.setText("Electricity Readings for Meter "+customer.getElectricMeterNo());
        txt2.setText("Electricity Readings for Meter "+customer.getElectricMeterNo());

        dayRate = tariff.getDayRate();
        nightRate = tariff.getNightRate();

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);

        double standingCharge =  round(Double.parseDouble(tariff.getStandingCharge()) * totalDays,2);

        abc.setText(String.valueOf(totalDays)  + " days  x "+ tariff.getStandingCharge()+"p per day ");
        standingChargeValue.setText(" £" + standingCharge);



        periodValue1.setText("from "+startDate + " to " + endDate);
        periodValue2.setText("from "+startDate + " to " + endDate);

        openingReadValue1.setText(nightOpeningRead);
        closingReadValue1.setText(nightClosingRead);

        openingReadValue2.setText(dayOpeningRead);
        closingReadValue2.setText(dayClosingRead);

        double dayTotalUnits = Double.parseDouble(dayClosingRead) - Double.parseDouble(dayOpeningRead);
        double nightTotalUnits = Double.parseDouble(nightClosingRead) - Double.parseDouble(nightOpeningRead);
        dayKwhValue.setText(String.valueOf(round(dayTotalUnits,2)));
        nightKwhValue.setText(String.valueOf(round(nightTotalUnits,2)));

        totalUnitsValue.setText(round(dayTotalUnits+nightTotalUnits,2) + "kWh");


        chargesPeriodValueDay.setText("from "+startDate + " to " + endDate);
        chargesPeriodValueNight.setText("from "+startDate + " to " + endDate);

        nightUnitsValue.setText(String.valueOf(round(dayTotalUnits,2)));
        dayUnitsValue.setText(String.valueOf(round(nightTotalUnits,2)));

        nightRateValue.setText(nightRate);
        dayRateValue.setText(dayRate);

        double nightPrice = Double.parseDouble(nightRate) * nightTotalUnits;
        double dayPrice = Double.parseDouble(dayRate) * dayTotalUnits;
        nightCharge.setText(String.valueOf(nightPrice));
        dayCharge.setText(String.valueOf(dayPrice));
        double total =round( nightPrice+dayPrice,2);
        totalValue.setText(String.valueOf(total));




        usageChargeValue.setText(String.valueOf(total));


        totalSupplyValue.setText(String.valueOf(standingCharge+Double.parseDouble(usageChargeValue.getText())));

        double vat  = round(Double.parseDouble(tariff.getVat()) * 0.01 * (Double.parseDouble(totalSupplyValue.getText())),2);

        vatValue.setText(String.valueOf(vat));
        totalBill.setText(String.valueOf(round(Double.parseDouble(totalSupplyValue.getText())+Double.parseDouble(vatValue.getText()),2)));

        System.out.println("Electricity Bill");


        saveBill(nightTotalUnits,dayTotalUnits);


    }

    private void saveBill(double nightTotalUnits, double dayTotalUnits) {
        BillModel bill = new BillModel(customer.getEmail(),startDate,endDate, Constants.ELECTRICITY,customer.getElectricMeterNo(),totalBill.getText(),String.valueOf(nightTotalUnits+dayTotalUnits), dayUnitsValue.getText(),nightUnitsValue.getText());
        DataUtils.saveBillToFile(bill,Constants.BILLS_FILE_PATH);
    }


    @FXML
    private void generatePdf() {
        try {
            Rectangle2D screenBounds = new Rectangle2D(dialogStage.getX(), dialogStage.getY(), dialogStage.getWidth(), dialogStage.getHeight());
            BufferedImage billImage = ScreenCaptureToPDF.captureScreenArea(screenBounds);
            String fileName = customer.getElectricMeterNo()+".pdf";
            ScreenCaptureToPDF.saveAsPdf(billImage, fileName);
            ScreenCaptureToPDF.saveAsPdf(billImage, fileName);
            System.out.println("PDF generated successfully.");

            EmailUtility.sendEmailWithAttachment(customer.getEmail(),"Bill","Your electricity bill",fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void sendEmail() {
        generatePdf();
        try {
            String fileName = customer.getElectricMeterNo()+".pdf";
            fileName = "12345.pdf";

            EmailUtility.sendEmailWithAttachment(customer.getEmail(),"Bill","Your electricity bill",fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
