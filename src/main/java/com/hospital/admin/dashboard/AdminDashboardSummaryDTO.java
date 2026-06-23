package com.hospital.admin.dashboard;

public class AdminDashboardSummaryDTO {
    private int totalAppointmentCount;
    private int todayAppointmentCount;
    private int pendingAppointmentCount;
    private int completedTreatmentCount;
    private int cancelledAppointmentCount;
    private int completionRate;

    public int getTotalAppointmentCount() {
        return totalAppointmentCount;
    }

    public void setTotalAppointmentCount(int totalAppointmentCount) {
        this.totalAppointmentCount = totalAppointmentCount;
    }

    public int getTodayAppointmentCount() {
        return todayAppointmentCount;
    }

    public void setTodayAppointmentCount(int todayAppointmentCount) {
        this.todayAppointmentCount = todayAppointmentCount;
    }

    public int getPendingAppointmentCount() {
        return pendingAppointmentCount;
    }

    public void setPendingAppointmentCount(int pendingAppointmentCount) {
        this.pendingAppointmentCount = pendingAppointmentCount;
    }

    public int getCompletedTreatmentCount() {
        return completedTreatmentCount;
    }

    public void setCompletedTreatmentCount(int completedTreatmentCount) {
        this.completedTreatmentCount = completedTreatmentCount;
    }

    public int getCancelledAppointmentCount() {
        return cancelledAppointmentCount;
    }

    public void setCancelledAppointmentCount(int cancelledAppointmentCount) {
        this.cancelledAppointmentCount = cancelledAppointmentCount;
    }

    public int getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(int completionRate) {
        this.completionRate = completionRate;
    }
}
