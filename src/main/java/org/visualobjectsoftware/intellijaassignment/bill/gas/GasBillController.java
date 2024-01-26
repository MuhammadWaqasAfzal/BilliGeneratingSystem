package org.visualobjectsoftware.intellijaassignment.bill.gas;


import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.bill.BillModel;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;
import org.visualobjectsoftware.intellijaassignment.tariff.TariffModel;
import org.visualobjectsoftware.intellijaassignment.utility.EmailUtility;
import org.visualobjectsoftware.intellijaassignment.utility.ScreenCaptureToPDF;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static eu.hansolo.tilesfx.tools.Helper.round;

public class GasBillController {
    @FXML
    private Label standingChargeValue;

    @FXML
    private Label usageChargeValue;

    @FXML
    private Label vatLabel;

    @FXML
    private Label totalSupplyValue;

    @FXML
    private Label vatValue;
    @FXML
    private Label totalBill;

    @FXML
    private Label openingReadDateValue;

    @FXML
    private Label closingReadDateValue;

    @FXML
    private Label readTypeValue;
    @FXML
    private Label openingReadValue;
    @FXML
    private Label closingReadTypeValue;
    @FXML
    private Label closingReadValue;


    @FXML
    private Label unitsValue;
    @FXML
    private Label m3Value;
    @FXML
    private Label totalm3Value;

    @FXML
    private Label dateValue;

    @FXML
    private Label m3Value2;

    @FXML
    private Label correctionValue;

    @FXML
    private Label calorificValue;

    @FXML
    private Label kWhValue;

    @FXML
    private Label perRateValue;


    @FXML
    private Label totalPriceValue;
    @FXML
    private Label abc;

    @FXML
    private Label txt1;
    @FXML
    private Label txt2;

    @FXML
    private Label totalUnitsValue;


    private Stage dialogStage;
    public static CustomerModel customer;
    public static String dayOpeningRead;
    public static String dayClosingRead;

    public static String correctionFactor;
    public static String calorificFactor;

    public static LocalDate startDate;
    public static LocalDate endDate;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    public void initialize() {


        TariffModel tariff = null;

        for (TariffModel tariffModel : DataUtils.getTariffsFromFile(Constants.TARIFF_FILE_PATH)) {
            if (Objects.equals(tariffModel.getName(), customer.getGasTariff())) {
                tariff = tariffModel;
            }
        }

        txt1.setText("Gas Readings for Meter " + customer.getElectricMeterNo());
        txt2.setText("How we calculate your gas charges m3 to kWh conversion" + customer.getElectricMeterNo());


        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);

        double standingCharge = round(Double.parseDouble(tariff.getStandingCharge()) * totalDays, 2);

        abc.setText(String.valueOf(totalDays) + " days  x " + tariff.getStandingCharge() + "p per day ");
        standingChargeValue.setText(" £" + standingCharge);


        openingReadDateValue.setText(String.valueOf(startDate));
        closingReadDateValue.setText(String.valueOf(endDate));

        readTypeValue.setText("E");
        openingReadValue.setText(dayOpeningRead);

        closingReadTypeValue.setText("E");
        closingReadValue.setText(dayClosingRead);

        double dayTotalUnits = round(Double.parseDouble(dayClosingRead) - Double.parseDouble(dayOpeningRead), 2);
        unitsValue.setText(String.valueOf(round(dayTotalUnits, 2)));
        m3Value.setText(String.valueOf(round(dayTotalUnits * 2.83, 2)));

        totalm3Value.setText(String.valueOf(round(dayTotalUnits * 2.83, 2)) + "m3");


        dateValue.setText(String.valueOf(closingReadDateValue.getText()));
        m3Value2.setText(totalm3Value.getText());
        correctionValue.setText("x " + correctionFactor);
        calorificValue.setText("x " + calorificFactor + "         /3.6    =");


        double kwh = round((Double.parseDouble(m3Value.getText()) * Double.parseDouble(correctionFactor) * Double.parseDouble(calorificFactor)) / 3.6, 2);
        kWhValue.setText(String.valueOf(kwh));

        perRateValue.setText("x " + tariff.getDayRate());

        Double price = round(kwh * Double.parseDouble(tariff.getDayRate()), 2);

        totalPriceValue.setText("£" + String.valueOf(price));


        usageChargeValue.setText("£" + String.valueOf(price));


        totalSupplyValue.setText("£" + String.valueOf(standingCharge + price));

        double vat = calculateVat(tariff.getVat(),standingCharge,price);

        vatValue.setText("£" + String.valueOf(vat));
        vatLabel.setText("Plus VAT at " + tariff.getVat() + "%");

        Double temp = round(standingCharge + price + vat, 2);
        totalBill.setText("£" + temp);

        totalUnitsValue.setText("£" + price);
        System.out.println("Gas Bill");


        saveBill(dayTotalUnits);


    }

    public double calculateVat(String tariff , double standingCharge, double price){
        double vat = round(Double.parseDouble(tariff) * 0.01 * (standingCharge + price), 2);
        return vat;
    }

    private void saveBill(double dayTotalUnits) {
        BillModel bill = new BillModel(customer.getEmail(), startDate, endDate, Constants.GAS, customer.getElectricMeterNo(), totalBill.getText(), String.valueOf(dayTotalUnits), unitsValue.getText(), unitsValue.getText());
        DataUtils.saveBillToFile(bill, Constants.BILLS_FILE_PATH);
    }


    public WritableImage captureNode(VBox vbox) {
        WritableImage image = new WritableImage((int) vbox.getWidth(), (int) vbox.getHeight());
        vbox.snapshot(null, image);
        return image;
    }


    @FXML
    private void generatePdf() {
        try {

            Rectangle2D screenBounds = new Rectangle2D(dialogStage.getX(), dialogStage.getY(), dialogStage.getWidth(), dialogStage.getHeight());
            BufferedImage billImage = ScreenCaptureToPDF.captureScreenArea(screenBounds);
            String fileName = customer.getGasMeterNo()+".pdf";
            ScreenCaptureToPDF.saveAsPdf(billImage, fileName);
            ScreenCaptureToPDF.saveAsPdf(billImage, "screen_capture.pdf");
            System.out.println("PDF generated successfully.");

            EmailUtility.sendEmailWithAttachment(customer.getEmail(),"Bill","Your gas bill",fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendEmail() {
        try {
            String fileName = customer.getGasMeterNo()+".pdf";
            EmailUtility.sendEmailWithAttachment(customer.getEmail(),"Bill","Your gas bill",fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

