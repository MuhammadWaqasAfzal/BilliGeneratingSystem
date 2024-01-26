package org.visualobjectsoftware.intellijaassignment.customers;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;

import java.io.Serializable;
import java.util.Objects;

public class CustomerModel implements Serializable {

	private String address;
	private transient Button deleteButton;
	private transient Button editButton;
	private String email;
	private String electricMeterNo;
	private String gasMeterNo;

	private String gasTariff;
	private String electricTariff;

	private String name;
	private String postCode;
	public  transient BooleanProperty selected = new SimpleBooleanProperty(false);




	public CustomerModel(String name, String email, String address, String postCode, String electricMeterNo,String gasMeterNo,String electricTariff,String gasTariff) {
		super();
		this.name = name;
		this.email = email;
		this.address = address;
		this.postCode = postCode;
		this.electricMeterNo = electricMeterNo;
		this.gasMeterNo = gasMeterNo;
		this.gasTariff = gasTariff;
		this.electricTariff = electricTariff;


		this.editButton = new Button("Edit");
		this.deleteButton = new Button("Delete");

	}

	public String getAddress() {
		return address;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public Button getEditButton() {
		return editButton;
	}
	public String getEmail() {
		return email;
	}

	public String getGasTariff() {
		return gasTariff;
	}
	public String getElectricTariff() {
		return electricTariff;
	}

	public String getElectricMeterNo() {
		return electricMeterNo;
	}

	public String getGasMeterNo() {
		return gasMeterNo;
	}


	public String getName() {
		return name;
	}


	public String getPostCode() {
		return postCode;
	}



	public void setAddress(String address) {
		this.address = address;
	}

	public void setGasTariff(String tariff) {
		this.gasTariff = tariff;
	}
	public void setElectricTariff(String tariff) {
		this.electricTariff = tariff;
	}


	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}


	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setElectricMeterNo(String meterNo) {
		this.electricMeterNo = meterNo;
	}

	public void setGasMeterNo(String meterNo) {
		this.gasMeterNo = meterNo;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}


	 public boolean isSelected() {

	        		return selected.get();
	 }

	 public BooleanProperty selectedProperty() {
	        return selected;
	}

	    public void setSelected(boolean selected) {
	        this.selected.set(selected);
	    }

	    public void setSelectedProperty(Boolean selected) {
	        this.selected.set(selected);
	    }



	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CustomerModel that = (CustomerModel) obj;
		return Objects.equals(email, that.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}


}
