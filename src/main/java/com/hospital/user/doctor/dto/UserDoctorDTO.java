package com.hospital.user.doctor.dto;

import java.util.List;

import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorScheduleDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDoctorDTO {
	private int doctorLicenseNo;
	private String name;
	private String deptName;
	private String position;
	private String thumbnailUrl;
	private String detailImageUrl;
	private String introTitle;
	private String introContent;
	private String specialty;
	private List<DoctorScheduleDTO> dsList;
	private List<DoctorCareerDTO> dcList;
	private List<DoctorEducationDTO> deList;
}
