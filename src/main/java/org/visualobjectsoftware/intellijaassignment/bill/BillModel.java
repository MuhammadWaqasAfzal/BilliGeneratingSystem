package org.visualobjectsoftware.intellijaassignment.bill;

import org.visualobjectsoftware.intellijaassignment.customers.CustomerModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class BillModel implements Serializable {

    private String userEmail;

    private LocalDate startDate;

    private  LocalDate endDate;

    private  String type;

    private String meterNO;

    private String billAmount;

    private String dayUnits;

    private String nightUnits;

    private String totalUnits;

    public BillModel(String userEmail, LocalDate startDate, LocalDate endDate, String type, String meterNO, String billAmount, String totalUnits, String dayUnits, String nightUnits) {
        this.userEmail = userEmail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.meterNO = meterNO;
        this.billAmount = billAmount;
        this.dayUnits = dayUnits;
        this.nightUnits = nightUnits;
        this.totalUnits = totalUnits;
    }

    public String getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(String totalUnits) {
        this.totalUnits = totalUnits;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeterNO() {
        return meterNO;
    }

    public void setMeterNO(String meterNO) {
        this.meterNO = meterNO;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }


    public String getDayUnits() {
        return dayUnits;
    }

    public void setDayUnits(String dayUnits) {
        this.dayUnits = dayUnits;
    }

    public String getNightUnits() {
        return nightUnits;
    }

    public void setNightUnits(String nightUnits) {
        this.nightUnits = nightUnits;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BillModel that = (BillModel) obj;
        return Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail);
    }

}
