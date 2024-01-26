package org.visualobjectsoftware.intellijaassignment.selectCustomer;

import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import javafx.collections.ObservableList;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;
import org.visualobjectsoftware.intellijaassignment.selectCustomer.CustomerFilter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class CustomerFilterTest {

    private List<CustomerModel> customers;

    @BeforeAll
    public static void setUpClass() {
        // Initialize JavaFX toolkit
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        customers = new ArrayList<>();
        customers.add(new CustomerModel("Alice", "alice@example.com", "123 Street", "12345", "E123", "G123", "Basic", "Standard"));
        customers.add(new CustomerModel("Bob", "bob@example.com", "456 Avenue", "67890", "E456", "G456", "Premium", "Economy"));
        customers.add(new CustomerModel("Charlie", "charlie@example.com", "789 Lane", "54321", "E789", "G789", "Basic", "Premium"));
    }

    @Test
    void testFilterCustomers_ByName() {
        String searchText = "Alice";
        ObservableList<CustomerModel> filteredCustomers = CustomerFilter.filterCustomers(customers, searchText);

        assertEquals(1, filteredCustomers.size());
        assertEquals("Alice", filteredCustomers.get(0).getName());
    }

    @Test
    void testFilterCustomers_ByEmail() {
        String searchText = "bob@example.com";
        ObservableList<CustomerModel> filteredCustomers = CustomerFilter.filterCustomers(customers, searchText);

        assertEquals(1, filteredCustomers.size());
        assertEquals("bob@example.com", filteredCustomers.get(0).getEmail());
    }

    @Test
    void testFilterCustomers_ByPartialMatch() {
        String searchText = "av";
        ObservableList<CustomerModel> filteredCustomers = CustomerFilter.filterCustomers(customers, searchText);

        assertEquals(1, filteredCustomers.size());
        assertEquals("Bob", filteredCustomers.get(0).getName());
    }

    @Test
    void testFilterCustomers_NoMatch() {
        String searchText = "xyz";
        ObservableList<CustomerModel> filteredCustomers = CustomerFilter.filterCustomers(customers, searchText);

        assertTrue(filteredCustomers.isEmpty());
    }

    @AfterAll
    public static void tearDownClass() {
        // Shut down the JavaFX toolkit
        Platform.runLater(Platform::exit);

    }

}
