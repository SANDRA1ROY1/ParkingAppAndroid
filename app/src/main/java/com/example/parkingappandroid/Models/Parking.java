package com.example.parkingappandroid.Models;

import java.io.Serializable;

public class Parking implements Serializable {
    private String carPlateNo;
    private String buildingCode;
    private int hrs;
    private String suitNo;
    private String parkingLoc;
    private String date;

    public Parking(String carPlateNo, String buildingCode, int hrs, String suitNo, String parkingLoc) {
        this.carPlateNo = carPlateNo;
        this.buildingCode = buildingCode;
        this.hrs = hrs;
        this.suitNo = suitNo;
        this.parkingLoc = parkingLoc;
    }

    public Parking(String buildingCode, int hrs) {
        this.buildingCode = buildingCode;
        this.hrs = hrs;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public int getHrs() {
        return hrs;
    }

    public void setHrs(int hrs) {
        this.hrs = hrs;
    }

    public String getSuitNo() {
        return suitNo;
    }

    public void setSuitNo(String suitNo) {
        this.suitNo = suitNo;
    }

    public String getParkingLoc() {
        return parkingLoc;
    }

    public void setParkingLoc(String parkingLoc) {
        this.parkingLoc = parkingLoc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
