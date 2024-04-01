package org.visualobjectsoftware.intellijaassignment.analytics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;
import org.visualobjectsoftware.intellijaassignment.bill.BillModel;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;
import org.visualobjectsoftware.intellijaassignment.utility.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsControllerTest {

    private AnalyticsController controller;
    private List<CustomerModel> mockCustomers;
    private List<BillModel> mockBills;

    @BeforeEach
    void setUp() {
        controller = new AnalyticsController();


        // Prepare mock data for customers
        mockCustomers = Arrays.asList(
               DataUtils.getCustomersFromFile(Constants.CUSTOMER_FILE_PATH).get(0),
               DataUtils.getCustomersFromFile(Constants.CUSTOMER_FILE_PATH).get(1),
               DataUtils.getCustomersFromFile(Constants.CUSTOMER_FILE_PATH).get(2)
        );


        // Prepare mock data for bills
        mockBills = Arrays.asList(
             DataUtils.getBillsFromFile(Constants.BILLS_FILE_PATH).get(0),
                DataUtils.getBillsFromFile(Constants.BILLS_FILE_PATH).get(1)
        );
    }

    @Test
    void testGetTariffCount() {
        // Testing for electric tariffs
        Map<String, Long> electricTariffCount = controller.getTariffCount(mockCustomers, true);
        assertEquals(1, electricTariffCount.get("Tariff3"));

        // Testing for gas tariffs
        Map<String, Long> gasTariffCount = controller.getTariffCount(mockCustomers, false);
        assertEquals(null, gasTariffCount.get("GasTariff"));
    }

    @Test
    void testCalculateUnitCounts() {
        Map<String, Double> unitCounts = controller.calculateUnitCounts(mockBills);
        assertEquals(0.0, unitCounts.get(Constants.ELECTRICITY));
        assertEquals(521.0, unitCounts.get(Constants.GAS));
    }
}


