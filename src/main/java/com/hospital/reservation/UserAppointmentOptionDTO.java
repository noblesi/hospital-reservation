package com.hospital.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAppointmentOptionDTO {
	private String deptNo;
	private String deptName;
	private int doctorLicenseNo;
	private String doctorName;
	private String positionName;
	private String specialty;
	private String thumbnailUrl;
	private int dayOfWeek;
	private String startTime;
	private String endTime;
	private String availableTime;
	private boolean reservable;
}
