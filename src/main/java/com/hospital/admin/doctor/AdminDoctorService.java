package com.hospital.admin.doctor;

import java.util.List;
import com.hospital.admin.department.AdminDepartmentDAO;
import com.hospital.admin.doctor.dto.AdminDoctorFormDTO;
import com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO;
import com.hospital.admin.doctor.dto.AdminDoctorSearchDTO;
import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorScheduleDTO;


public class AdminDoctorService {
	private AdminDoctorDAO adminDoctorDAO;
	private AdminDepartmentDAO adminDepartmentDAO; 
	
	public AdminDoctorService() {
		adminDoctorDAO = AdminDoctorDAO.getInstance();
		adminDepartmentDAO = AdminDepartmentDAO.getInstance();
	}

	public int totalCnt(AdminDoctorSearchDTO searchDTO) {
		// 전체 건수 조회
		int totalCnt = 0;
		
		AdminDoctorSearchDTO adminDoctorSearchDTO = searchDTO;
		totalCnt = adminDoctorDAO.selectDoctorTotalCnt(adminDoctorSearchDTO);
		
		return totalCnt;
	}
	public List<DoctorDTO> searchDoctorList(AdminDoctorSearchDTO searchDTO){
		// 의료진 목록 검색
		List<DoctorDTO> list = null;
		AdminDoctorSearchDTO adminDoctorSearchDTO = searchDTO;
		
		list = adminDoctorDAO.selectDoctorList(adminDoctorSearchDTO);
		return list;
	}
	public AdminDoctorFormDTO searchDoctorDetail(int doctorLicenseNo){
		// 의료진 상세 조회
		int doctorLicenseNoTemp = doctorLicenseNo;
		DoctorDTO doctorDTO = null;
		AdminDoctorFormDTO adminDoctorFormDTO = new AdminDoctorFormDTO();
		
		adminDoctorFormDTO.setDoctorDTO(adminDoctorDAO.selectDoctorDetail(doctorLicenseNoTemp));
		adminDoctorFormDTO.setCareerList(adminDoctorDAO.selectDoctorCareerList(doctorLicenseNoTemp));
		adminDoctorFormDTO.setDepartmentList(adminDepartmentDAO.selectDepartmentList());
		adminDoctorFormDTO.setEducationList(adminDoctorDAO.selectDoctorEducationList(doctorLicenseNoTemp));
		adminDoctorFormDTO.setScheduleList(adminDoctorDAO.selectDoctorSchedules(doctorLicenseNoTemp));
		adminDoctorFormDTO.setPositionList(adminDoctorDAO.selectDoctorPostionAllList());
		adminDoctorFormDTO.setStatusList(adminDoctorDAO.selectDoctorStatusAllList());
		adminDoctorFormDTO.setPorfileImageFileName(null);
		adminDoctorFormDTO.setDetailImageFileName(null);
		
		return adminDoctorFormDTO;
	}
	
	public boolean registerDoctor(AdminDoctorFormDTO formDTO){
		// 의료진 등록
		boolean successFlag = false;
		int successCnt = 0 ;
		AdminDoctorFormDTO adminDoctorFormDTO = formDTO;
		successCnt = adminDoctorDAO.insertDoctor(adminDoctorFormDTO);
		if(successCnt > 0) {
			successFlag = true;
		}// end if
		
		return successFlag;
	}
	public boolean modifyDoctor(AdminDoctorFormDTO formDTO) {
		// 의료진 정보 수정
		boolean successFlag = false;
		int successCnt = 0 ;
		AdminDoctorFormDTO adminDoctorFormDTO = formDTO;
		successCnt = adminDoctorDAO.updateDoctor(adminDoctorFormDTO.getDoctorDTO());
		if(successCnt > 0) {
			successFlag = true;
		}// end if
		
		return successFlag;
	}
	public boolean changeDoctorStatus(int doctorLicenseNo, String statusCode) {
		// 의료진 상태 변경
		boolean successFlag = false;
		int successCnt = 0 ;
		int doctorLicenseNoTemp = doctorLicenseNo;
		String statusCodeTemp = statusCode; 
		successCnt = adminDoctorDAO.updateDoctorStatus(doctorLicenseNoTemp, statusCodeTemp);
		if(successCnt > 0) {
			successFlag = true;
		}// end if
		
		return successFlag;
	}
	public boolean checkDoctorLicenseNo(int doctorLicenseNo) {
		// 의사면허번호 중복 확인
		int checkCnt = 0;
		boolean successFlag = false;
		int doctorLicenseNoTemp = doctorLicenseNo;
		checkCnt = adminDoctorDAO.selectDoctorLicenseNoCnt(doctorLicenseNoTemp);
		if(checkCnt == 0) {
			successFlag = true;
		}// end if
		
		return successFlag;
	}
	public AdminDoctorFormOptionDTO getDoctorFormOptions(){
		// 진료과/직급/상태 선택값 조회
		AdminDoctorFormOptionDTO adminDoctorFormOptionDTO = new AdminDoctorFormOptionDTO(); 
		adminDoctorFormOptionDTO.setDepartmentList(adminDepartmentDAO.selectDepartmentList());
		adminDoctorFormOptionDTO.setPositionList(adminDoctorDAO.selectDoctorPostionAllList());
		adminDoctorFormOptionDTO.setStatusList(adminDoctorDAO.selectDoctorStatusAllList());
		return adminDoctorFormOptionDTO;
	}
	public boolean saveDoctorSchedule(int doctorLicenseNo, List<DoctorScheduleDTO> schedules) {
		// 진료 가능 요일 저장
		int doctorLicenseNoTemp = doctorLicenseNo;
		List<DoctorScheduleDTO> list = schedules;
		boolean successflag = false;
		int sucssessCnt = 0;
		for(int i =0; i < list.size(); i++) {
			sucssessCnt = adminDoctorDAO.updateDoctorSchedules(doctorLicenseNoTemp, list.get(i));
			if(sucssessCnt == 1) {
				successflag = true;
			} else {
				successflag = false;
				return successflag;
			}
		}// end for
		return successflag;
	}
	public boolean saveDoctorEducation(int doctorLicenseNo, List<DoctorEducationDTO> educations){
		// 학력 저장
		int doctorLicenseNoTemp = doctorLicenseNo;
		List<DoctorEducationDTO> list = educations;
		boolean successflag = false;
		int sucssessCnt = 0;
		for(int i =0; i < list.size(); i++) {
			sucssessCnt = adminDoctorDAO.updateDoctorEducation(doctorLicenseNoTemp, list.get(i));
			if(sucssessCnt == 1) {
				successflag = true;
			} else {
				successflag = false;
				return successflag;
			}
		}// end for
		return successflag;
		
	}
	public boolean saveDoctorCareer(int doctorLicenseNo, List<DoctorCareerDTO> careers){
		// 경력 저장
		int doctorLicenseNoTemp = doctorLicenseNo;
		List<DoctorCareerDTO> list = careers;
		boolean successflag = false;
		int sucssessCnt = 0;
		for(int i =0; i < list.size(); i++) {
			sucssessCnt = adminDoctorDAO.updateDoctorCareer(doctorLicenseNoTemp, list.get(i));
			if(sucssessCnt == 1) {
				successflag = true;
			} else {
				successflag = false;
				return successflag;
			}
		}// end for
		return successflag;
	}
}
