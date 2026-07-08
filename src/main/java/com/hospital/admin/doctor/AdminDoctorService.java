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
import com.hospital.common.util.GetKey;

import kr.co.sist.chipher.DataDecryption;
import kr.co.sist.chipher.DataEncryption;



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
	public List<DoctorDTO> searchDoctorList(){
		// 의료진 목록 검색
		List<DoctorDTO> list = null;
		
		list = adminDoctorDAO.selectDoctorList();
		return list;
	}
	
	public AdminDoctorFormDTO searchDoctorDetail(int doctorLicenseNo){
		// 의료진 상세 조회
		int doctorLicenseNoTemp = doctorLicenseNo;
		AdminDoctorFormDTO adminDoctorFormDTO = new AdminDoctorFormDTO();
		
		DoctorDTO doctorDTO = adminDoctorDAO.selectDoctorDetail(doctorLicenseNoTemp);
		
		DataDecryption dd = new DataDecryption(GetKey.getKey());
		
		try {
			//doctorDTO.setName(dd.decrypt(doctorDTO.getName()));
			doctorDTO.setPhoneNum(dd.decrypt(doctorDTO.getPhoneNum()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//adminDoctorFormDTO.setDoctorDTO(adminDoctorDAO.selectDoctorDetail(doctorLicenseNoTemp));
		adminDoctorFormDTO.setDoctorDTO(doctorDTO);
		
		adminDoctorFormDTO.setCareerList(adminDoctorDAO.selectDoctorCareerList(doctorLicenseNoTemp));
		adminDoctorFormDTO.setDepartmentList(adminDepartmentDAO.selectDepartmentList());
		adminDoctorFormDTO.setEducationList(adminDoctorDAO.selectDoctorEducationList(doctorLicenseNoTemp));
		adminDoctorFormDTO.setScheduleList(adminDoctorDAO.selectDoctorSchedules(doctorLicenseNoTemp));
		adminDoctorFormDTO.setPositionList(adminDoctorDAO.selectDoctorPostionAllList());
		adminDoctorFormDTO.setStatusList(adminDoctorDAO.selectDoctorStatusAllList());
		adminDoctorFormDTO.setProfileImageFileName(adminDoctorDAO.selectDoctorDetail(doctorLicenseNoTemp).getThumbnailUrl());

		return adminDoctorFormDTO;
	}
	
	public boolean registerDoctor(AdminDoctorFormDTO formDTO){
		// 의료진 등록
		boolean successFlag = false;
		int successCnt = 0 ;
		AdminDoctorFormDTO adminDoctorFormDTO = formDTO;
		
		DataEncryption de = new DataEncryption(GetKey.getKey());
		
		DoctorDTO doctorDTO = adminDoctorFormDTO.getDoctorDTO();
		try {
			doctorDTO.setPhoneNum(de.encrypt(doctorDTO.getPhoneNum()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		adminDoctorFormDTO.setDoctorDTO(doctorDTO);
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
		
		DataEncryption de = new DataEncryption(GetKey.getKey());
		DoctorDTO doctorDTO = adminDoctorFormDTO.getDoctorDTO();
		
		try {
			doctorDTO.setPhoneNum(de.encrypt(doctorDTO.getPhoneNum()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		adminDoctorFormDTO.setDoctorDTO(doctorDTO);
		
		
		successCnt = adminDoctorDAO.updateDoctor(adminDoctorFormDTO.getDoctorDTO());
		int doctorLicenseNo = adminDoctorFormDTO.getDoctorDTO().getDoctorLicenseNo();
		List<DoctorCareerDTO> originCareerList = adminDoctorDAO.selectDoctorCareerList(doctorLicenseNo);
		List<DoctorEducationDTO> originEducationList = adminDoctorDAO.selectDoctorEducationList(doctorLicenseNo);
		
		List<DoctorCareerDTO> careerList = adminDoctorFormDTO.getCareerList();
		List<DoctorEducationDTO> educationList = adminDoctorFormDTO.getEducationList();
		List<DoctorScheduleDTO> scheduleList = adminDoctorFormDTO.getScheduleList();
		
		/////career///////////////////////////////////////////////////////////////
		// 업데이트 또는 인설트 판단
		
		int careerNo = 0;
		int originCareerNo = 0;
		
		boolean deleteCareerFlag = false;
		for(int i = 0; i < careerList.size(); i++) {
			careerNo = careerList.get(i).getCareerNo();
			if( adminDoctorDAO.selectDoctorCareerChk(doctorLicenseNo, careerNo) ) {
				adminDoctorDAO.updateDoctorCareer(doctorLicenseNo, adminDoctorFormDTO.getCareerList().get(i) );
			} else if(careerNo==0 && (!careerList.get(i).getCareerYear().isEmpty() || !careerList.get(i).getCareerContent().isEmpty()) ){
				adminDoctorDAO.insertDoctorCareer(adminDoctorFormDTO.getCareerList().get(i));
			} 
		}// end for
		
		for(int i =0; i < originCareerList.size(); i++ ) {
			originCareerNo = originCareerList.get(i).getCareerNo();
			deleteCareerFlag= false;
			for(int j =0; j < careerList.size(); j++) {
				if(originCareerList.get(i).getCareerNo() == careerList.get(j).getCareerNo()) {
					j=careerList.size();
					deleteCareerFlag=false;
				} else if(j == careerList.size()-1) {
					deleteCareerFlag= true;
				}
			}// end for
			if(deleteCareerFlag) {
				adminDoctorDAO.deleteDoctorCareers(doctorLicenseNo, originCareerNo);
			}
		}// end for
		
		//////education//////////////////////////////////////////////////////////////////////////////////
		int educationNo = 0;
		int originEducationNo = 0;
		boolean deleteEducationFlag = false;
		// 업데이트 또는 인설트 판단
		for(int i = 0; i < educationList.size(); i++) {
			educationNo = educationList.get(i).getEducationNo();
			if( adminDoctorDAO.selectDoctorCareerChk(doctorLicenseNo, educationNo) ) {
				adminDoctorDAO.updateDoctorEducation(doctorLicenseNo, educationList.get(i) );
			} else if(educationNo==0 && (!educationList.get(i).getEducationYear().isEmpty() || !educationList.get(i).getEducationContent().isEmpty()) ){
				adminDoctorDAO.insertDoctorEducation(educationList.get(i));
			} 
		}// end for
		
		for(int i =0; i < originEducationList.size(); i++ ) {
			originEducationNo = originEducationList.get(i).getEducationNo();
			deleteEducationFlag= false;
			for(int j =0; j < educationList.size(); j++) {
				if(originEducationList.get(i).getEducationNo() == originEducationList.get(j).getEducationNo()) {
					j=educationList.size();
					deleteEducationFlag=false;
				} else if(j == educationList.size()-1) {
					deleteEducationFlag= true;
				}
			}// end for
			if(deleteEducationFlag) {
				adminDoctorDAO.deleteDoctorCareers(doctorLicenseNo, originEducationNo);
			}
		}// end for
		////////schedule///////////////////////////////////////////////////////////////////////////
		//adminDoctorDAO.updateDoctorSchedules(doctorLicenseNo, scheduleList);
		saveDoctorSchedule(doctorLicenseNo, scheduleList);
				
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
		
		int doctorLicenseNoTemp = doctorLicenseNo;
		checkCnt = adminDoctorDAO.selectDoctorLicenseNoCnt(doctorLicenseNoTemp);
		
		return checkCnt > 0;
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
