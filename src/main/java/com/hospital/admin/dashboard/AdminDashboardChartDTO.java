package com.hospital.admin.dashboard;

public class AdminDashboardChartDTO {
    private String label;
    private int count;
    private int rate;

    public AdminDashboardChartDTO() {
    }

    public AdminDashboardChartDTO(String label, int count, int rate) {
        this.label = label;
        this.count = count;
        this.rate = rate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
