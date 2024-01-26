package org.visualobjectsoftware.intellijaassignment.bill.gas;

import org.junit.jupiter.api.Test;
import org.visualobjectsoftware.intellijaassignment.bill.electricity.ElectricityBillController;

import static org.junit.jupiter.api.Assertions.*;

class GasBillControllerTest {

    @Test
    public void testCalculateVat() {
        GasBillController controller = new GasBillController();

        // Test with normal values
        double vat = controller.calculateVat("20", 100, 200);
        assertEquals(60.0, vat, "VAT calculation with normal values is incorrect");

        // Test with zero tariff
        vat = controller.calculateVat("0", 100, 200);
        assertEquals(0.0, vat, "VAT should be zero with zero tariff");

        // Test with zero standing charge and price
        vat = controller.calculateVat("20", 0, 0);
        assertEquals(0.0, vat, "VAT should be zero with zero standing charge and price");

        // Test with negative values
        vat = controller.calculateVat("20", -100, -200);
        assertEquals(-60.0, vat, "VAT calculation with negative values is incorrect");

        // Test with a high tariff value
        vat = controller.calculateVat("100", 100, 200);
        assertEquals(300.0, vat, "VAT calculation with high tariff value is incorrect");

        // Add more test cases as needed to cover a wide range of scenarios
    }

}