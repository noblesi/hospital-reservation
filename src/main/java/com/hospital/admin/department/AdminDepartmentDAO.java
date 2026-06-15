package com.hospital.admin.department;

import java.util.List;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.department.dto.AdminDepartmentSearchDTO;


public class AdminDepartmentDAO {
	private static AdminDepartmentDAO adminDepartmentDAO;
	
	private AdminDepartmentDAO() {
	}//AdminDepartmentDAO
	
	public static AdminDepartmentDAO getInstance() {
		if(adminDepartmentDAO == null) {
			adminDepartmentDAO = new AdminDepartmentDAO();
		}// end if
		return adminDepartmentDAO;
	}//getInstance
	
	public int selectDepartmentTotalCnt(AdminDepartmentSearchDTO searchDTO) {
		AdminDepartmentSearchDTO adminDepartmentSearchDTO = null;
		
		return 0;
	}// selectDepartmentTotalCnt
	
	public List<AdminDepartmentSearchDTO> selectDepartmentList(AdminDepartmentSearchDTO searchDTO){
		List<DepartmentDTO> departmentDTO = null;
		List<AdminDepartmentSearchDTO> adminDepartmentSearchDTO = null;
		
		return adminDepartmentSearchDTO;
	}//selectDepartmentList
	
	public DepartmentDTO selectDepartmentDetail(String deptNo) {
		DepartmentDTO departmentDTO = null;
		
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
