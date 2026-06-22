package com.hospital.member.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 진료 기록 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserMedicalRecordDTO {

	/** 진료 기록 고유번호 */
	private String recordNo;
	/** 진료 기록과 연결된 예약번호 */
	private String appointmentNo;
	/** 진료받은 회원의 환자번호 */
	private String patientNo;

	/** 실제 진료일 */
	private Date treatmentDate;

	/** 진료완료 등의 진료 상태 */
	private String status;

	/** 예약 및 의사 테이블 조인으로 조회한 진료과명 */
	private String deptName;
	/** 예약 및 의사 테이블 조인으로 조회한 의료진명 */
	private String doctorName;

}
