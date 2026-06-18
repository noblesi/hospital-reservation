package com.hospital.admin.department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hospital.admin.department.dto.AdminDepartmentSearchDTO;
import com.hospital.common.DBConnection;
import com.hospital.common.dto.DepartmentDTO;


public class AdminDepartmentDAO{
	private static AdminDepartmentDAO adminDepartmentDAO;
	private AdminDepartmentDAO() {
	}//AdminDepartmentDAO
	
	public static AdminDepartmentDAO getInstance() {
		if(adminDepartmentDAO == null) {
			adminDepartmentDAO = new AdminDepartmentDAO();
		}// end if
		return adminDepartmentDAO;
	}//getInstance
	
//	public int selectDepartmentTotalCnt(AdminDepartmentSearchDTO searchDTO) {
	public int selectDepartmentTotalCnt() {
		/* 사용하는 건지는 잘 모름 */
		//AdminDepartmentSearchDTO adminDepartmentSearchDTO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int totalCnt = 0;
		
		String selectSql="select count(*) from department";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalCnt=rs.getInt(0);
			}// end if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return totalCnt;
	}// selectDepartmentTotalCnt
	
	public List<AdminDepartmentSearchDTO> selectDepartmentList(AdminDepartmentSearchDTO searchDTO){
		List<DepartmentDTO> departmentDTOList = new ArrayList<DepartmentDTO>();
		List<AdminDepartmentSearchDTO> adminDepartmentSearchDTO = new ArrayList<AdminDepartmentSearchDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String selectSql="select * from department";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql);
			rs = pstmt.executeQuery();
			
			DepartmentDTO departmentDTO = null;
			
			while(rs.next()) {
				departmentDTO = new DepartmentDTO();
				departmentDTO.setDeptNo(rs.getString("dept_no"));
				departmentDTO.setDeptName(rs.getString("dept_name"));
				departmentDTO.setDescription(rs.getString("description"));
				departmentDTO.setDeptLoc(rs.getString("dept_loc"));
				departmentDTO.setIsActiveYn(rs.getString("is_active_yn"));
				departmentDTOList.add(departmentDTO);
			}// end if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return adminDepartmentSearchDTO;
	}//selectDepartmentList
	
	public DepartmentDTO selectDepartmentDetail(String deptNo) {
		DepartmentDTO departmentDTO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql= new StringBuilder();
		
		selectSql
		.append("	select dept_no, dept_name, description, dept_loc, is_active_yn		")
		.append("	from department		")
		.append("	where dept_no = ?		");
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			pstmt.setString(1, deptNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				departmentDTO = new DepartmentDTO();
				departmentDTO.setDeptNo(rs.getString("dept_no"));
				departmentDTO.setDeptName(rs.getString("dept_name"));
				departmentDTO.setDescription(rs.getString("description"));
				departmentDTO.setDeptLoc(rs.getString("dept_loc"));
				departmentDTO.setIsActiveYn(rs.getString("is_active_yn"));
			}// end if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return departmentDTO;
	}// selectDepartmentDetail
	
	public int insertDepartment(DepartmentDTO departmentDTO) {
		
		return 0;
	}// insertDepartment
	
	public int updateDepartment(DepartmentDTO departmentDTO) {
		
		return 0;
	}// updateDepartment
	
	public int updateDepartmentActive(String deptNo, String isActiveYn) {
		
		return 0;
	}// updateDepartmentActive
	
	public int selectDepartmentNameCnt(String deptName) {
		
		return 0;
	}//selectDepartmentNameCnt
}// class
