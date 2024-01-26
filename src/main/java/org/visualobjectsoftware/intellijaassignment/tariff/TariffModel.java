package org.visualobjectsoftware.intellijaassignment.tariff;

import javafx.scene.control.Button;
import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;

import java.io.Serializable;
import java.util.Objects;

public class TariffModel implements Serializable {
    private String name;
    private String type;
    private String dayRate;
	private String nightRate;
    private String vat;
    private String standingCharge;
    private String paymentMethod;


    private transient Button editButton;
    private transient Button deleteButton;


    public TariffModel(String name, String type, String dayRate, String nightRate, String vat,String standingCharge,String paymentMethod) {
        this.name = name;
        this.type = type;
        this.dayRate = dayRate;
		this.nightRate = nightRate;
        this.vat = vat;
        this.standingCharge = standingCharge;
        this.paymentMethod = paymentMethod;
        this.editButton = new Button("Edit");
        this.deleteButton = new Button("Delete");
        // Set any event handler or action for the edit button if needed
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDayRate() {
		return dayRate;
	}

	public void setDayRate(String rate) {
		this.dayRate = rate;
	}

	public String getNightRate() {
		return nightRate;
	}

	public void setNightRate(String rate) {
		this.nightRate = rate;
	}


	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}


	public String getStandingCharge() {
		return standingCharge;
	}

	public void setStandingCharge(String standingCharge) {
		this.standingCharge = standingCharge;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String vat) {
		this.paymentMethod = vat;
	}

	public Button getEditButton() {
		return editButton;
	}

	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TariffModel that = (TariffModel) obj;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}


}
