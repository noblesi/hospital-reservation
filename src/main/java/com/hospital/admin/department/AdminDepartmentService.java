package com.hospital.admin.department;

import java.util.List;

import com.hospital.admin.department.dto.AdminDepartmentSearchDTO;
import com.hospital.common.dto.DepartmentDTO;

public class AdminDepartmentService {

	private AdminDepartmentDAO adminDepartmentDAO;
	
	public AdminDepartmentService() {
		adminDepartmentDAO = AdminDepartmentDAO.getInstance();
	}//AdminDepartmentService
	
	public int getTotalCount(AdminDepartmentSearchDTO searchDTO) {
		AdminDepartmentSearchDTO adminDepartmentSearchDTO = searchDTO;
		int totalCont=0;
		totalCont = adminDepartmentDAO.selectDepartmentTotalCnt(adminDepartmentSearchDTO);
		
		return totalCont;
	}// getTotalCount
	
	public List<DepartmentDTO> searchDepartmentList(AdminDepartmentSearchDTO searchDTO){
		AdminDepartmentSearchDTO adminDepartmentSearchDTO = searchDTO;
		List<DepartmentDTO> list = adminDepartmentDAO.selectDepartmentList(adminDepartmentSearchDTO);
		
		return list;
	}// searchDepartmentList
	
	public List<DepartmentDTO> searchDepartmentList(){
		List<DepartmentDTO> list = adminDepartmentDAO.selectDepartmentList();
		
		return list;
	}// searchDepartmentList
	
	public DepartmentDTO searchDepartmentDetail(String deptNo) {
		DepartmentDTO departmentDTO = null;
		departmentDTO = adminDepartmentDAO.selectDepartmentDetail(deptNo);
		
		return departmentDTO;
	}// searchDepartmentDetail
	
	public boolean registerDepartment(DepartmentDTO departmentDTO) {
		DepartmentDTO departmentDTOTemp = departmentDTO;
		
		boolean successRegister = false;
		System.out.println( "register");
		successRegister = (adminDepartmentDAO.insertDepartment(departmentDTOTemp) == 1);
		
		return successRegister;
	}// registerDepartment
	
	public boolean modifyDepartment(DepartmentDTO departmentDTO) {
		DepartmentDTO departmentDTOTemp = departmentDTO;
		boolean successModify = false;
		
		if(departmentDTOTemp.getDescription()==null || departmentDTOTemp.getDescription().isEmpty()) {
			departmentDTOTemp.setDescription("");
		}// end if
		if(departmentDTOTemp.getDeptLoc()==null || departmentDTOTemp.getDeptLoc().isEmpty()) {
			departmentDTOTemp.setDeptLoc("");
		}// end if
		
		successModify = (adminDepartmentDAO.updateDepartment(departmentDTOTemp) == 1);
		
		return successModify;
	}// modifyDepartment
	
	public boolean changeDepartmentActive(String deptNo, String isActiveYn) {
		String deptNoTemp = deptNo;
		String isActiveYnTemp = isActiveYn;
		boolean successYN = false;
		//isActiveYnTemp = (isActiveYnTemp=="Y" ? "N" : "Y");
		if("Y".equals(isActiveYnTemp)) {
			isActiveYnTemp = "N";
		} else if("N".equals(isActiveYnTemp)) {
			isActiveYnTemp = "Y";
		} else {
			System.out.println("이건뭐야" + isActiveYnTemp);
		}
		
		
		System.out.println(isActiveYnTemp);
		successYN = (adminDepartmentDAO.updateDepartmentActive(deptNoTemp, isActiveYnTemp)==1);
		
		return successYN;
	}// changeDepartmentActive
	
	public boolean checkDepartmentName(String deptName) {
		String deptNameTemp = deptName;
		boolean checkName = false;
		
		checkName = (adminDepartmentDAO.selectDepartmentNameCnt(deptNameTemp)==1);
		
		return checkName;
	}// checkDepartmentName
	
}//class
