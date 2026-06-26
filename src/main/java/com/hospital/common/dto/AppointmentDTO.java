package com.hospital.common.dto;

import java.sql.Date;
import java.sql.Timestamp;

// 관리자 예약 관리용 DTO
public class AppointmentDTO {
    private String appointmentNo;
    private String patientNo;
    private int doctorLicenseNo;
    private Date appointmentDate;
    private String appointmentTime;
    private String status;
    private Timestamp createDate;

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    public int getDoctorLicenseNo() {
        return doctorLicenseNo;
    }

    public void setDoctorLicenseNo(int doctorLicenseNo) {
        this.doctorLicenseNo = doctorLicenseNo;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
