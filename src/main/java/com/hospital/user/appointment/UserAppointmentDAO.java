package com.hospital.user.appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.DBConnection;
import com.hospital.common.DepartmentDTO;
import com.hospital.common.DoctorDTO;
import com.hospital.common.DoctorScheduleDTO;

/**
 * 병원 진료 예약에 관한 DB 업무를 구현하는 클래스
 */
public class UserAppointmentDAO {
	static UserAppointmentDAO userAppointmentDAO;

	private UserAppointmentDAO() {
	}

	/**
	 * 싱글톤 패턴 디자인 구현 method.
	 * 
	 * @return
	 */
	public static UserAppointmentDAO getInstance() {
		if (userAppointmentDAO == null) {
			userAppointmentDAO = new UserAppointmentDAO();
		}

		return userAppointmentDAO;
	}

	public List<DepartmentDTO> selectDepartmentList() throws SQLException {
		List<DepartmentDTO> deptList = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			
			String selectDepts = "select dept_no, dept_name, description, is_active_yn, dept_loc from department";
			pstmt = con.prepareStatement(selectDepts);
			
			rs = pstmt.executeQuery();
			
			DepartmentDTO deptDTO = null;
			
			if (rs != null) {
				while (rs.next()) {
					deptDTO = new DepartmentDTO();
					
					deptDTO.setDeptNo(rs.getString("dept_no"));
					deptDTO.setDeptName(rs.getString("dept_name"));
					deptDTO.setDescription(rs.getString("description"));
					deptDTO.setIsActiveYn(rs.getString("is_active_yn"));
					deptDTO.setDeptLoc(rs.getString("dept_loc"));
					
					deptList.add(deptDTO);
				}
			}
			
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		
		return deptList;
	}

	public List<DoctorDTO> selectDoctorList(String deptNo) {

		return null;
	}

	public UserAppointmentOptionDTO selectDoctorDetail(int doctorLicenseNo) {

		return null;
	}

	public List<DoctorScheduleDTO> selectDoctorSchedule(int doctorLicenseNo) {

		return null;
	}

	/**
	 * @param doctorLicenseNo
	 * @param appointmentDate
	 * @return 선택한 의사와 날짜의 예약된 시간들을 반환.
	 */
	public List selectReservedTime(int doctorLicenseNo, Date appointmentDate) {

		return null;
	}

	public int selectAppointmentConflict(UserAppointmentRequestDTO requestDTO) {

		return 0;
	}

	public int insertAppointment(UserAppointmentRequestDTO requestDTO) {

		return 0;
	}

	public UserAppointmentConfirmDTO selectAppointmentConfirm(String appointmentNo) {

		return null;
	}

	public int updateCancelAppointment(String appointmentNo, String patientNo) {

		return 0;
	}

}
