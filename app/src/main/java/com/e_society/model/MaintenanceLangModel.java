package com.e_society.model;

public class MaintenanceLangModel {

    String _id, house,houseName, maintenanceAmount, creationDate, paymentDate, lastDate, penalty, maintenancePaid, month;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(String maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getMaintenancePaid() {
        return maintenancePaid;
    }

    public void setMaintenancePaid(String maintenancePaid) {
        this.maintenancePaid = maintenancePaid;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
