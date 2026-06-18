package com.hospital.admin.doctor.dto;

import java.util.List;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorPositionDTO;
import com.hospital.common.dto.DoctorStatusDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminDoctorFormOptionDTO {

	private List<DepartmentDTO> departmentList;
	private List<DoctorPositionDTO> positionList;
	private List<DoctorStatusDTO> statusList;
	
}// class
