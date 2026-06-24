package com.hospital.member.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 아이디 찾기 및 비밀번호 찾기 기능에서
 * 동일한 매개변수(name, phoneNumber, email, birthDate)를
 * 사용하는 메서드가 반복되어 요청 데이터를 DTO로 캡슐화하기 위해 생성
 * (기존에 클래스 다이어그램에는 없음)
 */
@Getter
@Setter
public class FindAccountDTO {

	private String loginId;
	private String name;
	private String phoneNumber;
	private String email;
	private Date birthDate;

}//FindAccountDTO
