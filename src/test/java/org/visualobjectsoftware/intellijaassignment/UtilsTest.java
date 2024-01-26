package org.visualobjectsoftware.intellijaassignment;

import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.visualobjectsoftware.intellijaassignment.bill.BillModel;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;
import org.visualobjectsoftware.intellijaassignment.tariff.TariffModel;
import org.visualobjectsoftware.intellijaassignment.utility.DataUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @BeforeAll
    public static void setUpClass() {
        // Initialize JavaFX toolkit
        javafx.application.Platform.startup(() -> {});
    }

    @Test
    public void testCreateFileIfNotExists(@TempDir Path tempDir) throws Exception {
        String fileName = "testFile.txt";
        Path testFilePath = tempDir.resolve(fileName);

        assertFalse(Files.exists(testFilePath));
        DataUtils.createFileIfNotExists(testFilePath.toString());
        assertTrue(Files.exists(testFilePath));
    }



    @Test
    public void testSaveCustomer(@TempDir Path tempDir) throws Exception {
        String testFilePath = tempDir.resolve("testCustomers.txt").toString();
        DataUtils.createFileIfNotExists(testFilePath.toString());

        List<CustomerModel> customerList = new ArrayList<>();
        CustomerModel customer = new CustomerModel("test","test@gmail.com","asd","asda","asd","adssa","asdsa","dasda");
        customerList.add(customer);

        DataUtils.saveCustomer(customerList,testFilePath);

        // Read from the file to check if the object was saved correctly
        List<CustomerModel> readCustomerList = DataUtils.getCustomersFromFile(testFilePath);
        assertEquals(customerList, readCustomerList);
    }



    @Test
    public void testSaveTariffListToFile(@TempDir Path tempDir) throws Exception {
        String testFilePath = tempDir.resolve("testTariffs.txt").toString();
        DataUtils.createFileIfNotExists(testFilePath.toString());

        List<TariffModel> tariffList = new ArrayList<>();
        TariffModel tariff = new TariffModel("test","gas","12","!23","12","123","cash");
        tariffList.add(tariff);

        DataUtils.saveTariffListToFile(tariffList,testFilePath);

        List<TariffModel> readTariffList = DataUtils.getTariffsFromFile(testFilePath);
        assertEquals(tariffList, readTariffList);
    }

    @Test
    public void testSaveBillToFile(@TempDir Path tempDir) throws Exception {
        String testFilePath = tempDir.resolve("testBills.txt").toString();
        DataUtils.createFileIfNotExists(testFilePath.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate startDate = LocalDate.parse("12-12-23", formatter);
        LocalDate endDate = LocalDate.parse("19-01-24", formatter);


        BillModel bill = new BillModel("waqas@gmail.com",startDate,endDate,"gas","123","321","123","12","123");
        DataUtils.saveBillToFile(bill,testFilePath);

        List<BillModel> readBills = DataUtils.getBillsFromFile(testFilePath);
        assertTrue(readBills.contains(bill));
    }

    @AfterAll
    public static void tearDownClass() {
        // Shut down the JavaFX toolkit
        Platform.runLater(Platform::exit);

    }


}


