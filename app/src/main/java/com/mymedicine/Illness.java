package com.mymedicine;

import androidx.annotation.NonNull;

import java.util.List;

public class Illness {
    String illness,description,cause,medicine,symptom,salt;

    public Illness(String illness, String description, String cause, String medicine, String symptom) {
        this.illness = illness;
        this.description = description;
        this.cause = cause;
        this.medicine = medicine;
        this.symptom = symptom;
       // this.salt = salt;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @NonNull
    @Override
    public String toString() {

        return illness+" "+cause+" "+description;
    }
}
