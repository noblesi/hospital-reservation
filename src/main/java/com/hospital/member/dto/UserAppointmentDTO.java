package com.hospital.member.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 예약 내역 DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserAppointmentDTO {

	/** 예약 고유번호 */
	private String appointmentNo;
	/** 예약한 회원의 환자번호 */
	private String patientNo;
	/** 담당 의사의 면허번호 */
	private int doctorLicenseNo;

	/** 예약 진료일 */
	private Date appointmentDate;
	/** 예약 진료시간 */
	private String appointmentTime;

	/** 환자가 입력한 요청사항 */
	private String requirement;
	/** 승인대기, 승인완료, 진료완료, 예약취소 등의 예약 상태 */
	private String status;

	/** 예약 생성일 */
	private Date createdAt;
	/** 예약 취소일 */
	private Date canceledAt;

	/** 의사 및 진료과 조인으로 조회한 진료과명 */
	private String departmentName;
	/** 의사 테이블 조인으로 조회한 의료진명 */
	private String doctorName;

}
