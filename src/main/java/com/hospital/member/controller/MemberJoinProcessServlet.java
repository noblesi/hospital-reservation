package com.hospital.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;
import com.hospital.member.MemberRegisterService;

/**
 * 회원가입 처리를 담당하는 Servlet Controller.
 *
 * 기존 joinProcess.jsp에서 처리하던 회원가입 파라미터 수집,
 * 기본 검증, DTO 생성, 회원가입 Service 호출을 Servlet으로 분리한다.
 */
public class MemberJoinProcessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final MemberRegisterService memberRegisterService = new MemberRegisterService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String joinType = request.getParameter("join_type");

        String loginId = request.getParameter("id");
        String password = request.getParameter("pass");
        String name = request.getParameter("name");

        String birth = request.getParameter("birth");
        String genderFM = request.getParameter("gender");

        String phoneNumber = request.getParameter("hp_no");
        String email = request.getParameter("email");

        String zipcode = request.getParameter("zipcode");
        String address = request.getParameter("address");
        String addressDetail = request.getParameter("addressDetail");

        if(birth == null || "".equals(birth.trim())) {
            redirectJoinForm(request, response, joinType, "생년월일 값이 없습니다.");
            return;
        }//end if

        if(loginId == null || "".equals(loginId.trim())) {
            redirectJoinForm(request, response, joinType, "아이디 값이 없습니다.");
            return;
        }//end if

        loginId = loginId.trim();

        if(!isValidLoginId(loginId)) {
            redirectJoinForm(request, response, joinType, "아이디는 한글 3자 이상 또는 영문+숫자 혼용 6~12자로 입력해주세요.");
            return;
        }//end if

        if(memberRegisterService.checkLoginIdDuplicate(loginId)) {
            redirectJoinForm(request, response, joinType, "이미 사용 중인 아이디입니다.");
            return;
        }//end if

        try {
            MemberDTO memberDTO = createMemberDTO(
                    request,
                    joinType,
                    loginId,
                    password,
                    name,
                    birth,
                    genderFM,
                    phoneNumber,
                    email,
                    zipcode,
                    address,
                    addressDetail
            );

            MinorMemberDTO minorDTO = createMinorDTO(request, joinType);

            boolean result = memberRegisterService.registerMember(memberDTO, minorDTO);

            if(result) {
                request.getSession().setAttribute("registerLoginId", loginId);
                response.sendRedirect(request.getContextPath() + "/member/join/complete.do");
                return;
            }//end if

            redirectJoinForm(request, response, joinType, "회원가입에 실패했습니다.");
        } catch(IllegalArgumentException exception) {
            redirectJoinForm(request, response, joinType, "입력값을 다시 확인해주세요.");
        }//end catch

    }//doPost

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/member/join.do");
    }//doGet

    /**
     * 아이디 형식을 검증한다.
     *
     * 한글 3자 이상 또는 영문+숫자 혼용 6~12자만 허용한다.
     */
    private boolean isValidLoginId(String loginId) {
        return loginId.matches("^[가-힣]{3,}$")
                || loginId.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,12}$");
    }//isValidLoginId

    /**
     * 회원가입 요청 파라미터를 MemberDTO로 변환한다.
     */
    private MemberDTO createMemberDTO(
            HttpServletRequest request,
            String joinType,
            String loginId,
            String password,
            String name,
            String birth,
            String genderFM,
            String phoneNumber,
            String email,
            String zipcode,
            String address,
            String addressDetail) {

        String hasMinorMemberYn = "TC".equals(joinType) ? "Y" : "N";

        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setLoginId(loginId);
        memberDTO.setPassword(password);
        memberDTO.setName(name);
        memberDTO.setBirthDate(Date.valueOf(birth));
        memberDTO.setGenderFM(genderFM);
        memberDTO.setPhoneNumber(phoneNumber);
        memberDTO.setEmail(email);
        memberDTO.setZipCode(zipcode);
        memberDTO.setAddress(address);
        memberDTO.setAddressDetail(addressDetail);
        memberDTO.setHasMinorMemberYn(hasMinorMemberYn);
        memberDTO.setIp(request.getRemoteAddr());

        return memberDTO;
    }//createMemberDTO

    /**
     * 미성년자 회원가입일 때 보호 대상 미성년자 정보를 생성한다.
     */
    private MinorMemberDTO createMinorDTO(HttpServletRequest request, String joinType) {
        if(!"TC".equals(joinType)) {
            return null;
        }//end if

        String childYear = request.getParameter("childYear");
        String childMonth = request.getParameter("childMonth");
        String childDate = request.getParameter("childDate");

        if(childYear == null || childMonth == null || childDate == null
                || "".equals(childYear.trim())
                || "".equals(childMonth.trim())
                || "".equals(childDate.trim())) {
            throw new IllegalArgumentException("미성년자 생년월일 값이 없습니다.");
        }//end if

        String childBirth = childYear + "-" + childMonth + "-" + childDate;

        MinorMemberDTO minorDTO = new MinorMemberDTO();

        minorDTO.setRelationship(request.getParameter("relationshipType"));
        minorDTO.setMinorName(request.getParameter("childName"));
        minorDTO.setMinorBirthDate(Date.valueOf(childBirth));
        minorDTO.setMinorGenderFm(request.getParameter("childGender"));

        return minorDTO;
    }//createMinorDTO

    /**
     * 회원가입 실패 메시지를 세션에 저장하고 회원가입 폼으로 이동한다.
     */
    private void redirectJoinForm(HttpServletRequest request, HttpServletResponse response, String joinType, String message)
            throws IOException {

        request.getSession().setAttribute("joinMessage", message);

        if("TC".equals(joinType)) {
            response.sendRedirect(request.getContextPath() + "/views/member/joinFormChild.jsp?join_type=TC");
            return;
        }//end if

        response.sendRedirect(request.getContextPath() + "/views/member/joinFormCommon.jsp?join_type=TG");
    }//redirectJoinForm

}//class
