package org.visualobjectsoftware.intellijaassignment.settings;

import java.io.Serializable;

public class ConstantsModel implements Serializable {

    private String correctionFactor;

    private String caloricFactor ;

    private String userName;

    private String passsword;


    public ConstantsModel(String correctionFactor, String caloricFactor, String userName, String passsword) {
        this.correctionFactor = correctionFactor;
        this.caloricFactor = caloricFactor;
        this.userName = userName;
        this.passsword = passsword;
    }

    public String getCorrectionFactor() {
        return correctionFactor;
    }

    public void setCorrectionFactor(String correctionFactor) {
        this.correctionFactor = correctionFactor;
    }

    public String getCaloricFactor() {
        return caloricFactor;
    }

    public void setCaloricFactor(String caloricFactor) {
        this.caloricFactor = caloricFactor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }
}
