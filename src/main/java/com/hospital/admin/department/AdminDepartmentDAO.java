package com.hospital.admin.department;

import java.util.List;

import com.hospital.common.AdminDepartmentSearchDTO;
import com.hospital.common.DepartmentDTO;

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
		
		return 0;
	}// selectDepartmentTotalCnt
	
	public List<AdminDepartmentSearchDTO> selectDepartmentList(AdminDepartmentSearchDTO searchDTO){
		List<DepartmentDTO> departmentDTO = null;
		List<AdminDepartmentSearchDTO> adminDepartmentSearchDTO = searchDTO;
		
		return adminDepartmentSearchDTO;
	}//selectDepartmentList
	
	public DepartmentDTO selectDepartmentDetail(String deptNo) {
		
	}// selectDepartmentDetail
	
	public int insertDepartment(DepartmentDTO departmentDTO) {
		
	}// insertDepartment
	
	public int updateDepartment(DepartmentDTO departmentDTO) {
		
	}// updateDepartment
	
	public int updateDepartmentActive(String deptNo, String isActiveYn) {
		
	}// updateDepartmentActive
	
	public int selectDepartmentNameCnt(String deptName) {
		
	}//selectDepartmentNameCnt
}// class
