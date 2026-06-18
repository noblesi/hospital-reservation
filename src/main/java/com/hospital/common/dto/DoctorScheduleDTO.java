package com.hospital.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DoctorScheduleDTO {
	private int scheduleNo;
	private int doctorLicenseNo;
	private int dayOfWeek;
	private String startTime;
	private String endTime;
	private String status;
}// class
