package com.hospital.admin.dashboard;

public class AdminDashboardSummaryDTO {
    private int totalAppointmentCount;
    private int completedTreatmentCount;

    public int getTotalAppointmentCount() {
        return totalAppointmentCount;
    }

    public void setTotalAppointmentCount(int totalAppointmentCount) {
        this.totalAppointmentCount = totalAppointmentCount;
    }

    public int getCompletedTreatmentCount() {
        return completedTreatmentCount;
    }

    public void setCompletedTreatmentCount(int completedTreatmentCount) {
        this.completedTreatmentCount = completedTreatmentCount;
    }
}
