package kr.or.ddit.vo;

import lombok.Data;


@Data
public class MemberVO {
	
	 private String memId; // 아이디
	 private String memPw; // 비밀번호
	 private String memNm; // 이름
	 private String memHp; // 연락처
	 private String memMail; // 이메일
	 private SubVO subVO; // 구독 정도를 담을 변수
	 private String subCd;// 구독 코드
	
}
