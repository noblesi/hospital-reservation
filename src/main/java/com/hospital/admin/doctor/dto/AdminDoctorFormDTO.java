package com.hospital.admin.doctor.dto;

import java.util.List;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorPositionDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.common.dto.DoctorStatusDTO;

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

public class AdminDoctorFormDTO {
	
	private DoctorDTO doctorDTO;
	private List<DoctorScheduleDTO> scheduleList;
	private List<DoctorEducationDTO> educationList;
	private List<DoctorCareerDTO> careerList;
	private List<DepartmentDTO> departmentList;
	private List<DoctorPositionDTO> positionList;
	private List<DoctorStatusDTO> statusList;
	private String profileImageFileName;

}//class
