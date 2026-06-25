package com.hospital.admin.member;

import java.util.List;

import com.hospital.admin.member.dto.AdminMemberSearchDTO;
import com.hospital.common.dto.MemberDTO;

// 관리자 회원 관리 Service
public class AdminMemberService {

    // 관리자 회원 DAO
    private final AdminMemberDAO adminMemberDAO = new AdminMemberDAO();

    // 회원 목록 조회
    // - searchDTO 를 받아서 페이징 계산 후 DAO 에 전달
    public List<MemberDTO> getMemberList(AdminMemberSearchDTO searchDTO) {
        applyPaging(searchDTO);
        return adminMemberDAO.selectMemberList(searchDTO);
    }

    // 회원 총 건수 조회
    // - 페이지 수 계산에 사용
    public int getMemberCount(AdminMemberSearchDTO searchDTO) {
        return adminMemberDAO.selectMemberCount(searchDTO);
    }

    // 회원 상세 조회
    // - memberNo 로 특정 회원 1명 조회
    public MemberDTO getMemberDetail(int memberNo) {
        return adminMemberDAO.selectMemberDetail(memberNo);
    }

    // 페이징 계산
    // - currentPage 와 pageScale 로 startNum 계산
    private void applyPaging(AdminMemberSearchDTO searchDTO) {

        if (searchDTO == null) {
            return;
        }

        // 페이지 번호가 1보다 작으면 1로 고정
        int currentPage = Math.max(searchDTO.getCurrentPage(), 1);

        // 페이지당 건수가 1보다 작으면 10으로 고정
        int pageScale = Math.max(searchDTO.getPageScale(), 10);

        // 시작 위치 계산
        // 예) 1페이지 → 0, 2페이지 → 10, 3페이지 → 20
        int startNum = (currentPage - 1) * pageScale;

        searchDTO.setCurrentPage(currentPage);
        searchDTO.setPageScale(pageScale);
        searchDTO.setStartNum(startNum);
        searchDTO.setEndNum(startNum + pageScale);
    }
}

