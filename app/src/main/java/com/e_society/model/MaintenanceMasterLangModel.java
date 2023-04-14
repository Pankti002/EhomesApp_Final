package com.e_society.model;

public class MaintenanceMasterLangModel {
    String _id,maintenanceAmount,penalty;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(String maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }
}
