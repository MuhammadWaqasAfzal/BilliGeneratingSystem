package org.visualobjectsoftware.intellijaassignment.utility;

import org.visualobjectsoftware.intellijaassignment.bill.BillModel;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;
import org.visualobjectsoftware.intellijaassignment.settings.ConstantsModel;
import org.visualobjectsoftware.intellijaassignment.tariff.TariffModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {

	public static void createFileIfNotExists(String file) {
		try {
			Path filePath = Paths.get(file);
			if (Files.notExists(filePath)) {
				Files.createFile(filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void saveTariffListToFile(List<TariffModel> tariffList,String path) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(tariffList);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error saving tariff list to file: " + e.getMessage());
		}
	}



	public static void saveCustomer(List<CustomerModel> customerList,String path) {

		createFileIfNotExists(path);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(customerList);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error saving customer list to file: " + e.getMessage());

		}

	}

	public static void saveBillToFile(BillModel bill,String path) {
		List<BillModel> allBills = new ArrayList<>();
		allBills.add(bill);

		// Check if getBillsFromFile() returns a non-null list
		List<BillModel> existingBills = getBillsFromFile(path);
		if (existingBills != null) {
			allBills.addAll(existingBills);
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(allBills);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error saving bill list to file: " + e.getMessage());
		}
	}



	public static List<TariffModel> getTariffsFromFile(String path) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
			return (List<TariffModel>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static List<CustomerModel> getCustomersFromFile(String path) {
		DataUtils.createFileIfNotExists(path);
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
			return (List<CustomerModel>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static List<BillModel> getBillsFromFile(String path) {
		DataUtils.createFileIfNotExists(path);
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
			return (List<BillModel>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void saveConstantsToFile(ConstantsModel constants,String path) {

		// Check if getBillsFromFile() returns a non-null lis
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(constants);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error saving constants list to file: " + e.getMessage());
		}
	}
	public static ConstantsModel getConstantFromFile(String path) {
		DataUtils.createFileIfNotExists(path);
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
			return (ConstantsModel) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


}
