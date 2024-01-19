package kr.or.ddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.IService;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProductVO;
import kr.or.ddit.vo.SubVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AllController {

	@Autowired
	IService service;

	/**
	 * 회원가입
	 * 
	 * @param memberVO 회원가입 정보
	 * @return memVO 회원가입 완료된 회원 정보
	 */
	@PostMapping("/registerMember")
	public MemberVO registerMember(@RequestBody MemberVO memberVO) {
		log.info("registerMember 실행...!");
		log.info("memberVO:{}", memberVO);

		MemberVO memVO = new MemberVO();
		int result = service.registerMember(memberVO);
		if (result != 0) {
			memVO = service.selectMember(memberVO);
		} else {
			memVO.setMemId("이미 사용중인 아이디입니다.");
		}
		return memVO;
	}

	/**
	 * 로그인
	 * 
	 * @param memberVO(memId : 아이디, memPw : 비밀번호)
	 * @return memVO 회원정보, 이용중인 스토리지 정보
	 */
	@GetMapping("/login")
	public MemberVO login(@RequestBody MemberVO memberVO) {

		log.info("login 실행...!");
		log.info("memberVO:{}", memberVO);
		MemberVO memVO = new MemberVO();

		if (memberVO.getMemPw() != null && memberVO.getMemPw() != "") {
			memVO = service.selectMember(memberVO);
		} else {
			memVO.setMemPw("비밀번호를 입력해주세요");
		}

		return memVO;
	}

	/**
	 * 서비스 종류 조회
	 * 
	 * @return 서비스 종류
	 */
	@GetMapping("/selectProductList")
	public List<ProductVO> selectProductList() {
		List<ProductVO> proList = service.selectProductList();
		return proList;
	}

	/**
	 * 서비스 구독 신청
	 * 
	 * @param productVO 서비스 이름, 회사정보
	 * @return 회원정보, 이용중인 서비스 정보
	 */
	@PostMapping("/buySub")
	public MemberVO buySub(@RequestBody SubVO subVO) {
		log.info("buySub 실행...!");
		log.info("subVO:{}", subVO);
		MemberVO memberVO = service.buySub(subVO);
		return memberVO;
	}

	/**
	 * 서비스 사용 현황 조회
	 * 
	 * @param memberVO (memId : 아이디, subCd : 이용중인 서비스 코드)
	 * @return 회원정보, 이용중인 서비스 정보
	 */
	@GetMapping("/selectUsingStorage")
	public MemberVO selectUsingStorage(@RequestBody MemberVO memberVO) {
		log.info("selectUsingStorage 실행...!");
		log.info("memberVO:{}", memberVO);
		MemberVO memVO = service.selectMember(memberVO);
		return memVO;

	}

	/**
	 * 서비스기간 연장신청
	 * 
	 * @param subVO (memId : 아이디, subCd : 이용중인 서비스 코드, subDate : 연장날짜)
	 * @return 회원정보, 이용중인 서비스 정보
	 */
	@PutMapping("/updateStorage")
	public MemberVO updateStorage(@RequestBody SubVO subVO) {
		log.info("updateStorage 실행...!");
		log.info("subVO:{}", subVO);

		MemberVO memberVO = new MemberVO();

		if (subVO.getSubCd() == null || subVO.getSubCd() == "" || subVO.getSubDate() == null) {
			memberVO.setSubCd("입력하신 정보를 확인해주세요.");

		} else {
			ServiceResult result = service.updateStorage(subVO);

			if (result.equals(ServiceResult.OK)) {
				memberVO.setMemId(subVO.getMemId());
				memberVO = service.selectMember(memberVO);
			} else {
				memberVO.setMemId("서비스 오류, 다시 시도해주세요.");
			}

		}
		return memberVO;
	}

}
