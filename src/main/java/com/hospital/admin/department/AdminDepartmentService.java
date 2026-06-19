package com.hospital.admin.department;

import java.util.List;

import com.hospital.admin.department.dto.AdminDepartmentSearchDTO;
import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.util.PaginationUtil;

public class AdminDepartmentService {
	private final AdminDepartmentDAO adminDepartmentDAO;

	public AdminDepartmentService() {
		this(AdminDepartmentDAO.getInstance());
	}//AdminDepartmentService

	public AdminDepartmentService(AdminDepartmentDAO adminDepartmentDAO) {
		this.adminDepartmentDAO = adminDepartmentDAO;
	}//AdminDepartmentService

	/**
	 * 진료과 목록과 pagination 정보를 함께 조회한다.
	 */
	public AdminDepartmentPage getDepartmentPage(AdminDepartmentSearchDTO searchDTO) {
		PaginationUtil.Pagination pagination = getPagination(searchDTO);
		searchDTO.applyPagination(pagination);
		return new AdminDepartmentPage(adminDepartmentDAO.selectDepartmentList(searchDTO), pagination);
	}//getDepartmentPage

	public PaginationUtil.Pagination getPagination(AdminDepartmentSearchDTO searchDTO) {
		int totalCount = adminDepartmentDAO.selectDepartmentTotalCnt(searchDTO);
		return PaginationUtil.create(searchDTO.getCurrentPage(), totalCount, searchDTO.getPageScale());
	}//getPagination

	public static class AdminDepartmentPage {
		private final List<DepartmentDTO> departmentList;
		private final PaginationUtil.Pagination pagination;

		/**
		 * 진료과 목록 조회 결과와 pagination 정보를 묶는다.
		 */
		public AdminDepartmentPage(List<DepartmentDTO> departmentList, PaginationUtil.Pagination pagination) {
			this.departmentList = departmentList;
			this.pagination = pagination;
		}//AdminDepartmentPage

		public List<DepartmentDTO> getDepartmentList() {
			return departmentList;
		}//getDepartmentList

		public PaginationUtil.Pagination getPagination() {
			return pagination;
		}//getPagination
	}//AdminDepartmentPage

}//class
