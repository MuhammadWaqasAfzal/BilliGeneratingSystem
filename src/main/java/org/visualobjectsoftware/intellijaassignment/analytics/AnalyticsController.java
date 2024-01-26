package org.visualobjectsoftware.intellijaassignment.analytics;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;
import org.visualobjectsoftware.intellijaassignment.Main;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.bill.BillModel;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static eu.hansolo.tilesfx.tools.Helper.round;

public class AnalyticsController {

    @FXML
    private PieChart electricTariffPieChart;

    @FXML
    private PieChart gasTariffPieChart;

    @FXML
    private PieChart unitsPieChart;

    private List<CustomerModel> customers;
    private List<BillModel> bills;

    private double gasUnits = 0.0;
    private double electricityUnits = 0.0;

    @FXML
    private void initialize() {
        customers = DataUtils.getCustomersFromFile(Constants.CUSTOMER_FILE_PATH);
        bills = DataUtils.getBillsFromFile(Constants.BILLS_FILE_PATH);

        Map<String, Long> electricTariffCount = getTariffCount(customers, true);
        Map<String, Long> gasTariffCount = getTariffCount(customers, false);

        updatePieCharts(electricTariffCount, gasTariffCount, calculateUnitCounts(bills));
    }

    public Map<String, Long> getTariffCount(List<CustomerModel> customers, boolean isElectric) {
        return customers.stream()
                .collect(Collectors.groupingBy(
                        isElectric ? CustomerModel::getElectricTariff : CustomerModel::getGasTariff,
                        Collectors.counting()));
    }

    public Map<String, Double> calculateUnitCounts(List<BillModel> bills) {
        bills.forEach(bill -> {
            try {
                if (bill.getType().equals(Constants.ELECTRICITY))
                    electricityUnits = electricityUnits + round(Double.parseDouble(bill.getTotalUnits()),2);
                else
                    gasUnits = gasUnits + round(Double.parseDouble(bill.getTotalUnits()),2);
            }
            catch (Exception e)
            {
                System.out.println("ERROR");
            }
        });
        return Map.of(Constants.ELECTRICITY, electricityUnits, Constants.GAS, gasUnits);
    }

    private void updatePieCharts(Map<String, Long> electricTariffCount,
                                 Map<String, Long> gasTariffCount,
                                 Map<String, Double> unitCounts) {
        updateTariffPieChart(electricTariffPieChart, electricTariffCount);
        updateTariffPieChart(gasTariffPieChart, gasTariffCount);
        updateUnitsPieChart(unitsPieChart, unitCounts);
    }

    private void updateTariffPieChart(PieChart pieChart, Map<String, Long> tariffCount) {
        pieChart.getData().clear();
        tariffCount.forEach((tariff, count) -> pieChart.getData().add(new PieChart.Data(tariff, count)));
    }

    private void updateUnitsPieChart(PieChart pieChart, Map<String, Double> unitCounts) {
        pieChart.getData().clear();
        unitCounts.forEach((type, units) -> pieChart.getData().add(new PieChart.Data(type, units)));
    }

    @FXML
    private void handleBackButtonClick() {
        Main.setScene("DashboardPage.fxml", 600, 600, 600, 600);
    }
}


