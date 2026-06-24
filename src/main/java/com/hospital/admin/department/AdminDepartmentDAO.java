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
	
	public int selectDepartmentTotalCnt(AdminDepartmentSearchDTO searchDTO) {
//	public int selectDepartmentTotalCnt() {
		/* 사용하는 건지는 잘 모름 */
		AdminDepartmentSearchDTO adminDepartmentSearchDTO = searchDTO;
		
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
	
	public List<DepartmentDTO> selectDepartmentList(AdminDepartmentSearchDTO searchDTO){
		//searchDTO 관련 정리가 안됨
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
		
		return departmentDTOList;
	}//selectDepartmentList
	
	public List<DepartmentDTO> selectDepartmentList(){
		//searchDTO 관련 정리가 안됨
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
		
		return departmentDTOList;
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
		DepartmentDTO departmentDTOTemp = departmentDTO;
		
		int insertCnt=0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		StringBuilder insertSql= new StringBuilder();
		
		insertSql
		.append("	insert into department (dept_no, dept_name, description, dept_loc, is_active_yn)		")
		.append("	values(get_dSeq(),?,?,?,?)		");
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(insertSql.toString());
			
			pstmt.setString(1, departmentDTOTemp.getDeptName() );
			pstmt.setString(2, departmentDTOTemp.getDescription());
			pstmt.setString(3, departmentDTOTemp.getDeptLoc());
			pstmt.setString(4, departmentDTOTemp.getIsActiveYn());
			
			insertCnt = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt,conn);
		}// end try catch
		
		return insertCnt;
	}// insertDepartment
	
	public int updateDepartment(DepartmentDTO departmentDTO) {
		DepartmentDTO departmentDTOTemp = departmentDTO;
		int updateCnt=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		
		StringBuilder updateSql= new StringBuilder();
		
		updateSql
		.append("	update department 		")
		.append("	set	dept_name = ?, is_active_yn = ? 	");
		
		
		try {
			conn = DBConnection.getConnection();
			
			boolean descFlag = false;
			if(!(departmentDTOTemp.getDescription()==null) || !(departmentDTOTemp.getDescription().isEmpty())) {
				descFlag=true;
				updateSql.append(",	description = ?		");
			}// end if
			
			boolean locFlag = false;
			if(!(departmentDTOTemp.getDeptLoc()==null) || !(departmentDTOTemp.getDeptLoc().isEmpty())) {
				locFlag=true;
				updateSql.append(",	dept_loc = ?		");
			}// end if
			updateSql.append("	where dept_no = ?		");
			
			pstmt = conn.prepareStatement(updateSql.toString());
			
			int markCnt = 1;
			System.out.println("들어왔니? markCnt");
			
			pstmt.setString(markCnt, departmentDTOTemp.getDeptName());
			pstmt.setString(++markCnt, departmentDTOTemp.getIsActiveYn());
			if(descFlag) {
				pstmt.setString(++markCnt, departmentDTOTemp.getDescription());
			}// end if
			
			if(locFlag) {
				pstmt.setString(++markCnt, departmentDTOTemp.getDeptLoc());
			}// end if
			
			pstmt.setString(++markCnt, departmentDTOTemp.getDeptNo());
			
			updateCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt,conn);
		}// end try catch
		
		return updateCnt;
	}// updateDepartment
	
	public int updateDepartmentActive(String deptNo, String isActiveYn) {
		int updateCnt=0;
		
		String deptNoTemp = deptNo;
		String isActiveYnTemp = isActiveYn;
		
		if(deptNoTemp == "" || deptNoTemp == null) {
			return 0;
		}// end if
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		
		StringBuilder updateSql= new StringBuilder();
		
		updateSql
		.append("	update department 		")
		.append("	set is_active_yn = ?		")
		.append("	where dept_no = ? 		");
		
		try {
			conn = DBConnection.getConnection();
			
			pstmt = conn.prepareStatement(updateSql.toString());
			pstmt.setString(1, isActiveYnTemp);
			pstmt.setString(2, deptNoTemp);
			
			updateCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt,conn);
		}// end try catch
		
		return updateCnt;
	}// updateDepartmentActive
	
	public int selectDepartmentNameCnt(String deptName) {
		int deptNameCnt = 0;
		String deptNameTemp = deptName;
		
		if(deptNameTemp == "" || deptNameTemp == null) {
			return 0;
		}// end if
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
		.append("	select count(*) from department		")
		.append("	where dept_name = ?;		");
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			pstmt.setString(1, deptNameTemp);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				deptNameCnt=rs.getInt(0);
			}// end if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return deptNameCnt;
	}//selectDepartmentNameCnt
}// class
