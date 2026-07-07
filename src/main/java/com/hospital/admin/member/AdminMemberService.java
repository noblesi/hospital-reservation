package com.hospital.admin.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hospital.admin.member.dto.AdminMemberSearchDTO;
import com.hospital.common.MemberDTO;
import com.hospital.common.util.GetKey;
import com.hospital.common.util.PaginationUtil;

import kr.co.sist.chipher.DataDecryption;

// 관리자 회원 관리 Service
public class AdminMemberService {
    private static final Logger LOGGER = Logger.getLogger(AdminMemberService.class.getName());

    // 관리자 회원 DAO
    private final AdminMemberDAO adminMemberDAO = new AdminMemberDAO();

    // 회원 목록 조회
    // - searchDTO 를 받아서 페이징 계산 후 DAO 에 전달
    public List<MemberDTO> getMemberList(AdminMemberSearchDTO searchDTO) {
        if (isDecryptedSearch(searchDTO)) {
            AdminMemberPage memberPage = getDecryptedSearchMemberPage(searchDTO);
            return memberPage.getMemberList();
        }

        applyPaging(searchDTO);
        List<MemberDTO> memberList = adminMemberDAO.selectMemberList(searchDTO);
        decryptMemberList(memberList);
        return memberList;
    }

    // 회원 총 건수 조회
    // - 페이지 수 계산에 사용
    public int getMemberCount(AdminMemberSearchDTO searchDTO) {
        if (isDecryptedSearch(searchDTO)) {
            return getDecryptedSearchFilteredList(searchDTO).size();
        }

        return adminMemberDAO.selectMemberCount(searchDTO);
    }

    // 회원 상세 조회
    // - memberNo 로 특정 회원 1명 조회
    public MemberDTO getMemberDetail(int memberNo) {
        return getMemberDetail(String.valueOf(memberNo));
    }

    // 회원 상세 조회
    // - patientNo 로 특정 회원 1명 조회
    public MemberDTO getMemberDetail(String patientNo) {
        MemberDTO member = adminMemberDAO.selectMemberDetail(patientNo);
        decryptMember(member);
        return member;
    }

    // 회원 목록과 pagination 정보를 한 번에 조회
    public AdminMemberPage getMemberPage(AdminMemberSearchDTO searchDTO) {
        if (searchDTO == null) {
            searchDTO = new AdminMemberSearchDTO();
        }

        if (isDecryptedSearch(searchDTO)) {
            return getDecryptedSearchMemberPage(searchDTO);
        }

        PaginationUtil.Pagination pagination = getPagination(searchDTO);
        applyPagination(searchDTO, pagination);
        List<MemberDTO> memberList = adminMemberDAO.selectMemberList(searchDTO);
        decryptMemberList(memberList);
        return new AdminMemberPage(memberList, pagination);
    }

    // 회원 목록 pagination 계산
    public PaginationUtil.Pagination getPagination(AdminMemberSearchDTO searchDTO) {
        if (searchDTO == null) {
            searchDTO = new AdminMemberSearchDTO();
        }

        int totalCount = adminMemberDAO.selectMemberCount(searchDTO);
        return PaginationUtil.create(searchDTO.getCurrentPage(), totalCount, searchDTO.getPageScale());
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

    private void applyPagination(AdminMemberSearchDTO searchDTO, PaginationUtil.Pagination pagination) {
        searchDTO.setCurrentPage(pagination.getCurrentPage());
        searchDTO.setPageScale(pagination.getPageScale());
        searchDTO.setStartNum((pagination.getCurrentPage() - 1) * pagination.getPageScale());
        searchDTO.setEndNum(searchDTO.getStartNum() + pagination.getPageScale());
    }

    private AdminMemberPage getDecryptedSearchMemberPage(AdminMemberSearchDTO searchDTO) {
        if (searchDTO == null) {
            searchDTO = new AdminMemberSearchDTO();
        }

        List<MemberDTO> filteredList = getDecryptedSearchFilteredList(searchDTO);
        PaginationUtil.Pagination pagination = PaginationUtil.create(
                searchDTO.getCurrentPage(),
                filteredList.size(),
                searchDTO.getPageScale()
        );

        applyPagination(searchDTO, pagination);
        return new AdminMemberPage(getPageList(filteredList, pagination), pagination);
    }

    private List<MemberDTO> getDecryptedSearchFilteredList(AdminMemberSearchDTO searchDTO) {
        List<MemberDTO> memberList = adminMemberDAO.selectMemberListForDecryptedSearch(searchDTO);
        decryptMemberList(memberList);

        String keyword = normalizeKeyword(searchDTO.getSearchKeyword());
        List<MemberDTO> filteredList = new ArrayList<>();

        for (MemberDTO member : memberList) {
            if (matchesDecryptedSearch(member, searchDTO.getSearchType(), keyword)) {
                filteredList.add(member);
            }
        }

        return filteredList;
    }

    private List<MemberDTO> getPageList(List<MemberDTO> memberList, PaginationUtil.Pagination pagination) {
        int fromIndex = Math.max(pagination.getStartNum() - 1, 0);
        int toIndex = Math.min(pagination.getEndNum(), memberList.size());

        if (fromIndex >= toIndex) {
            return new ArrayList<>();
        }

        return new ArrayList<>(memberList.subList(fromIndex, toIndex));
    }

    private boolean isDecryptedSearch(AdminMemberSearchDTO searchDTO) {
        if (searchDTO == null
                || searchDTO.getSearchKeyword() == null
                || searchDTO.getSearchKeyword().trim().isEmpty()) {
            return false;
        }

        String searchType = searchDTO.getSearchType();
        return searchType == null
                || searchType.trim().isEmpty()
                || "email".equals(searchType);
    }

    private String normalizeKeyword(String keyword) {
        return keyword == null ? "" : keyword.trim().toLowerCase(Locale.ROOT);
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        if (value == null) {
            return false;
        }

        return value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private boolean matchesDecryptedSearch(MemberDTO member, String searchType, String keyword) {
        if ("email".equals(searchType)) {
            return containsIgnoreCase(member.getEmail(), keyword);
        }

        return containsIgnoreCase(member.getPatientNo(), keyword)
                || containsIgnoreCase(member.getLoginId(), keyword)
                || containsIgnoreCase(member.getName(), keyword)
                || containsIgnoreCase(member.getEmail(), keyword);
    }

    private void decryptMemberList(List<MemberDTO> memberList) {
        if (memberList == null || memberList.isEmpty()) {
            return;
        }

        try {
            DataDecryption decryption = new DataDecryption(GetKey.getKey());
            for (MemberDTO member : memberList) {
                decryptMember(member, decryption);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "관리자 회원 목록 개인정보 복호화 실패", e);
        }
    }

    private void decryptMember(MemberDTO member) {
        if (member == null) {
            return;
        }

        try {
            DataDecryption decryption = new DataDecryption(GetKey.getKey());
            decryptMember(member, decryption);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "관리자 회원 개인정보 복호화 실패: " + member.getPatientNo(), e);
        }
    }

    private void decryptMember(MemberDTO member, DataDecryption decryption) throws Exception {
        if (member == null) {
            return;
        }

        member.setEmail(decryptValue(decryption, member.getEmail()));
        member.setPhoneNumber(decryptValue(decryption, member.getPhoneNumber()));
    }

    private String decryptValue(DataDecryption decryption, String value) throws Exception {
        if (value == null || value.isBlank()) {
            return value;
        }

        return decryption.decrypt(value);
    }

    public static class AdminMemberPage {
        private final List<MemberDTO> memberList;
        private final PaginationUtil.Pagination pagination;

        public AdminMemberPage(List<MemberDTO> memberList, PaginationUtil.Pagination pagination) {
            this.memberList = memberList;
            this.pagination = pagination;
        }

        public List<MemberDTO> getMemberList() {
            return memberList;
        }

        public PaginationUtil.Pagination getPagination() {
            return pagination;
        }
    }
}

