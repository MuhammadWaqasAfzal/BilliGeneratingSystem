package org.visualobjectsoftware.intellijaassignment.selectCustomer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;

import java.util.List;

// In a separate utility class
public class CustomerFilter {

    public static ObservableList<CustomerModel> filterCustomers(List<CustomerModel> customers, String searchText) {
        ObservableList<CustomerModel> filteredList = FXCollections.observableArrayList();

        for (CustomerModel customer : customers) {
            if (matchesSearch(customer, searchText)) {
                filteredList.add(customer);
            }
        }

        return filteredList;
    }

    private static boolean matchesSearch(CustomerModel customer, String searchText) {
        return customer.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                customer.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                customer.getAddress().toLowerCase().contains(searchText.toLowerCase()) ||
                customer.getPostCode().toLowerCase().contains(searchText.toLowerCase()) ||
                customer.getElectricMeterNo().toLowerCase().contains(searchText.toLowerCase()) ||
                customer.getGasMeterNo().toLowerCase().contains(searchText.toLowerCase())  ||
                customer.getGasTariff().toLowerCase().contains(searchText.toLowerCase())  ||
                customer.getElectricTariff().toLowerCase().contains(searchText.toLowerCase());
    }
}
