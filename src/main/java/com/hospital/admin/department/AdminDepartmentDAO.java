package com.hospital.admin.department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hospital.admin.department.dto.AdminDepartmentSearchDTO;
import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.util.DBConnection;


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

		StringBuilder selectSql = new StringBuilder("select count(*) from department where 1=1");
		List<Object> params = new ArrayList<Object>();
		appendSearchCondition(selectSql, params, searchDTO);

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			bindParams(pstmt, params);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				totalCnt=rs.getInt(1);
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

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder selectSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		selectSql
		.append("	select * from (	")
		.append("		select row_number() over (order by dept_no) rn,	")
		.append("			dept_no, dept_name, description, dept_loc, is_active_yn	")
		.append("		from department	")
		.append("		where 1=1	");
		appendSearchCondition(selectSql, params, searchDTO);
		selectSql.append("	)	");

		if(searchDTO != null && searchDTO.getStartNum() > 0 && searchDTO.getEndNum() > 0) {
			selectSql.append("	where rn between ? and ?	");
			params.add(searchDTO.getStartNum());
			params.add(searchDTO.getEndNum());
		}// end if

		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			bindParams(pstmt, params);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				departmentDTOList.add(mapDepartment(rs));
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

	/**
	 * 진료과 번호로 단일 진료과 상세 정보를 조회한다.
	 */
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
				departmentDTO = mapDepartment(rs);
			}// end if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch

		return departmentDTO;
	}// selectDepartmentDetail

	/**
	 * 신규 진료과 정보를 등록하고 반영된 row 수를 반환한다.
	 */
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

		if(deptNoTemp == null || deptNoTemp.isBlank()) {
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
		int totalCnt = 0;

		if(deptName == null || deptName.isBlank()) {
			return totalCnt;
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
			pstmt.setString(1, deptName.trim());
			rs = pstmt.executeQuery();

			if(rs.next()) {
				totalCnt = rs.getInt(1);
			}// end if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch

		return totalCnt;
	}//selectDepartmentNameCnt

	/**
	 * 검색어와 활성 여부 조건을 SQL에 추가한다.
	 */
	private void appendSearchCondition(StringBuilder sql, List<Object> params, AdminDepartmentSearchDTO searchDTO) {
		if(searchDTO == null) {
			return;
		}// end if

		if(searchDTO.getIsActiveYn() != null && !searchDTO.getIsActiveYn().isBlank()) {
			sql.append(" and is_active_yn = ?	");
			params.add(searchDTO.getIsActiveYn());
		}// end if

		if(searchDTO.getKeyword() == null || searchDTO.getKeyword().isBlank()) {
			return;
		}// end if

		String keyword = "%" + searchDTO.getKeyword().trim() + "%";
		String field = searchDTO.getField();

		if("deptName".equals(field)) {
			sql.append(" and dept_name like ?	");
			params.add(keyword);
			return;
		}// end if

		if("description".equals(field)) {
			sql.append(" and description like ?	");
			params.add(keyword);
			return;
		}// end if

		if("deptLoc".equals(field)) {
			sql.append(" and dept_loc like ?	");
			params.add(keyword);
			return;
		}// end if

		sql.append(" and (dept_name like ? or description like ? or dept_loc like ?)	");
		params.add(keyword);
		params.add(keyword);
		params.add(keyword);
	}//appendSearchCondition

	/**
	 * PreparedStatement에 SQL parameter를 순서대로 바인딩한다.
	 */
	private void bindParams(PreparedStatement pstmt, List<Object> params) throws SQLException {
		for(int i=0; i<params.size(); i++) {
			Object param = params.get(i);
			if(param instanceof Integer) {
				pstmt.setInt(i + 1, (Integer)param);
			} else {
				pstmt.setString(i + 1, String.valueOf(param));
			}// end if
		}// end for
	}//bindParams

	/**
	 * 조회 결과 한 row를 DepartmentDTO로 변환한다.
	 */
	private DepartmentDTO mapDepartment(ResultSet rs) throws SQLException {
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDeptNo(rs.getString("dept_no"));
		departmentDTO.setDeptName(rs.getString("dept_name"));
		departmentDTO.setDescription(rs.getString("description"));
		departmentDTO.setDeptLoc(rs.getString("dept_loc"));
		departmentDTO.setIsActiveYn(rs.getString("is_active_yn"));
		return departmentDTO;
	}//mapDepartment
}// class
