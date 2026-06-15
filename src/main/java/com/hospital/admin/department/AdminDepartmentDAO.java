package com.hospital.admin.department;

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
		
	}// selectDepartmentTotalCnt
	+ selectDepartmentList(searchDTO : AdminDepartmentSearchDTO) : List
	+ selectDepartmentDetail(deptNo : String) : DepartmentDTO
	+ insertDepartment(departmentDTO : DepartmentDTO) : int
	+ updateDepartment(departmentDTO : DepartmentDTO) : int
	+ updateDepartmentActive(deptNo : String, isActiveYn : String) : int
	+ selectDepartmentNameCnt(deptName : String) : int
}// class
