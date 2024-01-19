package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

@Data
public class SubVO {

	
	private String subCd; // 구독코드
	private String memId; // 구매자 아이디
	private int subCnt; // 제공 사용인원수
	private int subUseCnt; // 실 사용인원수
	private int subSize; // 제공 스토리지 용량(1TB=1048576KB)
	private int subResize; // 잔여 스토리지 용량(1TB=1048576KB)
	private Date subDate; // 구독 기한
	private String comNm; // 회사명
	private String comTel; // 회사연락처
	private String comMail; // 담당자 이메일
	private String proNm; // 구독중인 상품이름 
	private String storagePercent; // 잔여 스토리지 용량 퍼센트 
	
}
