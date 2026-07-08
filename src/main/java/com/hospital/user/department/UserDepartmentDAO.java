package com.hospital.user.department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.util.DBConnection;

public class UserDepartmentDAO {
	
	private static UserDepartmentDAO userDepartmentDAO;
	
	private UserDepartmentDAO() {
	}
	
	public static UserDepartmentDAO getInstance() {
		if(userDepartmentDAO == null) {
			userDepartmentDAO = new UserDepartmentDAO();
		}
		return userDepartmentDAO;
	}//getInstance
	
	public List<DepartmentDTO> getDepartmentList() throws SQLException {
		
		List<DepartmentDTO> list = new ArrayList<DepartmentDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();

			StringBuilder selectSql = new StringBuilder();
			selectSql 
				.append("	select	* ")
				.append("	from	department ");

			pstmt = con.prepareStatement(selectSql.toString());
			
			rs = pstmt.executeQuery();
			
			
			DepartmentDTO departmentDTO = null;
			
			while (rs.next()) {
				departmentDTO = new DepartmentDTO();
				departmentDTO.setDeptNo(rs.getString("dept_no"));
				departmentDTO.setDeptName(rs.getString("dept_name"));
				departmentDTO.setDescription(rs.getString("description"));
				departmentDTO.setDeptLoc(rs.getString("dept_loc"));
				departmentDTO.setIsActiveYn(rs.getString("is_active_yn"));
				
				list.add(departmentDTO);
			}// end while
			
		} finally {
			DBConnection.close(rs, pstmt, con);
		}
		return list;
	}//getDepartmentList
	
}
