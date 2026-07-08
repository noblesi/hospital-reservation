package com.hospital.user.department;

import java.sql.SQLException;
import java.util.List;

import com.hospital.common.dto.DepartmentDTO;

public class UserDepartmentService {

	public List<DepartmentDTO> searchDepartmentList() throws SQLException{
		UserDepartmentDAO userDepartmentDAO = UserDepartmentDAO.getInstance();
		return userDepartmentDAO.getDepartmentList();
	}//searchDepartmentList
	
}
