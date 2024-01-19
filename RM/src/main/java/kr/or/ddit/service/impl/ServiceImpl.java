package kr.or.ddit.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mapper.AllMapper;
import kr.or.ddit.service.IService;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProductVO;
import kr.or.ddit.vo.SubVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceImpl implements IService {

	@Autowired
	AllMapper mapper;
//	@Autowired
//	PasswordEncoder pe;

	/**
	 * 회원가입
	 */
	@Override
	public int registerMember(MemberVO memberVO) {
		// 비밀번호 암호화
//		memberVO.setMemPw(pe.encode(memberVO.getMemPw()));
		int result = 0;
		// 아이디 중복 체크
		String memIdCheck = mapper.selectMemId(memberVO.getMemId());
		// 아이디 중복이 존재하지 않으면 회원가입 진행
		if (memIdCheck == null || memIdCheck == "") {
			result = mapper.registerMember(memberVO);
		}

		return result;
	}

	/**
	 * 1. 회원가입 완료 시 회원정보 조회 
	 * 2. 로그인 시 아이디,비밀번호 확인
	 * 3. 사용현황 대시보드 조회
	 */
	@Override
	public MemberVO selectMember(MemberVO memberVO) {

		MemberVO memVO = mapper.selectMember(memberVO);

		if (memVO == null) {
			memVO = new MemberVO();
			memVO.setMemId("아이디와 비밀번호를 확인해주세요");

		} else {
			// 잔여 스토리지 용량 퍼센트 계산
			double storageSize = memVO.getSubVO().getSubSize();
			double reStorageSize = memVO.getSubVO().getSubResize();
			double percent = (reStorageSize/storageSize) * 100;
			// 소수점 2자리 까지 끊기
			String result = String.format("%.2f%%", percent);

			log.info("잔여 스토리지용량 확인 : {}", result);
			memVO.getSubVO().setStoragePercent(result);

		}
		return memVO;
	}
 
	/**
	 * 서비스 종류 조회
	 */
	@Override
	public List<ProductVO> selectProductList() {
		List<ProductVO> proList = mapper.selectProductList();
		return proList;
	}

	/**
	 * 서비스 구매
	 */
	@Override
	public MemberVO buySub(SubVO subVO) {
		LocalDate now = LocalDate.now();
		now = now.plusYears(1);
		Date date = java.sql.Date.valueOf(now);

		switch (subVO.getProNm()) {

		case "basic":
			subVO.setMemId(subVO.getMemId());
			subVO.setSubCnt(50);
			subVO.setSubUseCnt(1);
			subVO.setSubSize(1048576);
			subVO.setSubResize(1048576);
			subVO.setSubDate(date);
			break;

		case "standard":
			subVO.setMemId(subVO.getMemId());
			subVO.setSubCnt(100);
			subVO.setSubUseCnt(1);
			subVO.setSubSize(1048576 * 2);
			subVO.setSubResize(1048576 * 2);
			subVO.setSubDate(date);
			break;
		case "premium":
			subVO.setMemId(subVO.getMemId());
			subVO.setSubCnt(200);
			subVO.setSubUseCnt(1);
			subVO.setSubSize(1048576 * 4);
			subVO.setSubResize(1048576 * 4);
			subVO.setSubDate(date);
			break;
		}

		log.info("buySub 서비스 실행");
		log.info("subVO:{}", subVO);

		int result = mapper.buySub(subVO);
		MemberVO memberVO = new MemberVO();
		if (result != 0) {
			memberVO.setMemId(subVO.getMemId());
			memberVO = mapper.selectMember(memberVO);
		} else {
			memberVO.getSubVO().setSubCd("상품 구매에 실패하였습니다.");
		}

		return memberVO;
	}

	/**
	 * 서비스기간 연장신청
	 */
	@Override
	public ServiceResult updateStorage(SubVO subVO) {
		ServiceResult result = null;
		int status = mapper.updateStorage(subVO);
		if (status != 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

}
