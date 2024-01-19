package kr.or.ddit.vo;

import lombok.Data;

@Data
public class ProductVO {
	
	String proNm; // 서비스 이름
	int proRecnt; // 추천 사용인원
	String proDate;	// 구독기간
	String proSize; // 스토리지 용량
	String proPrice; // 가격
}
