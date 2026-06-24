package com.hospital.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.MemberDTO;
import com.hospital.member.dto.UserAppointmentDTO;
import com.hospital.member.dto.UserMedicalRecordDTO;

/**
 * 마이페이지 관련 비즈니스 로직 처리
 */
public class UserMyPageService {
	
	/** DAO 객체 */
	private UserMyPageDAO umpDAO;
	
	/**
	 * DAO 객체 생성
	 */
	public UserMyPageService() {
		umpDAO = UserMyPageDAO.getInstance();
	}//UserMyPageService
	
	/**
	 * 로그인한 회원 정보 조회
	 * 
	 * @param loginId 로그인 아이디
	 * @return 회원 정보 DTO
	 */
	public MemberDTO searchMemberInfo(String loginId) {
		
		MemberDTO mDTO = null;
		
		try {
			mDTO = umpDAO.selectMember(loginId);
			
		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch
		
		return mDTO;
	}//searchMemberInfo
	
	/**
	 * 예약 내역 조회
	 * 
	 * @param patientNo 환자번호
	 * @return 예약 목록
	 */
	public List<UserAppointmentDTO> searchAppointmentList(String patientNo) {
		
		List<UserAppointmentDTO> list = new ArrayList<UserAppointmentDTO>();
		
		try {
			list = umpDAO.selectAppointmentList(patientNo);
			
		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch
		
		return list;
	}//searchAppointmentList

	/**
	 * 마이페이지 하단 예약 취소 및 변경 영역에 표시할 예약 목록 조회
	 * 현재일 기준 최근 3개월 예약을 조회하며, 취소 가능 여부는 DTO에 함께 담는다.
	 * 
	 * @param patientNo 환자번호
	 * @return 예약 취소 및 변경 영역에 표시할 예약 목록
	 */
	public List<UserAppointmentDTO> searchManageAppointmentList(String patientNo) {
		
		List<UserAppointmentDTO> list = new ArrayList<UserAppointmentDTO>();
		
		try {
			list = umpDAO.selectManageAppointmentList(patientNo);
			
		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch
		
		return list;
	}//searchManageAppointmentList

	/**
	 * 진료 기록 조회
	 * 
	 * @param patientNo 환자번호
	 * @return 진료 기록 목록
	 */
	public List<UserMedicalRecordDTO> searchMedicalRecordList(String patientNo) {
		
		List<UserMedicalRecordDTO> list = new ArrayList<UserMedicalRecordDTO>();
		
		try {
			list = umpDAO.selectMedicalRecordList(patientNo);
			
		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch
		
		return list;
	}//searchMedicalRecordList
	
	/**
	 * 예약 취소 처리
	 * 
	 * @param appointmentNo 예약번호
	 * @param patientNo 환자번호
	 * @return 취소 성공 여부
	 */
	public boolean cancelAppointment(String appointmentNo, String patientNo) {
		
		boolean flag = false;
		
		try {
			// DAO의 UPDATE 결과
			int cnt = umpDAO.updateAppointmentCancel(appointmentNo, patientNo);
			
			// 정상적으로 1건 수정되면 true
			flag = cnt == 1;
			
		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch
		
		return flag;
	}//cancelAppointment 
}//class
