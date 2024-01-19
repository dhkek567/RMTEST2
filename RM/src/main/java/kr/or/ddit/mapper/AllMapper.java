package kr.or.ddit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProductVO;
import kr.or.ddit.vo.SubVO;

@Mapper
public interface AllMapper {
	
	/**
	 * 회원가입 
	 * @param memberVO 회원가입 정보
	 * @return 회원 insert 성공 여부 
	 */
	public int registerMember(MemberVO memberVO);

	/**
	 * 아이디 중복체크
	 * @param memId 아이디
	 * @return memId 아이디
	 */
	public String selectMemId(String memId);
	
	/**
	 * 1.회원가입 성공 시 회원정보 조회
	 * 2.로그인
	 * 3.사용현황 대시보드 조회
	 * @param memberVO 회원정보(아이디, 비밀번호)
	 * @return 가입된 회원정보, 이용중인 서비스 정보 
	 */
	public MemberVO selectMember(MemberVO memberVO);
	/**
	 * 서비스 상품 리스트 조회 
	 * @return 서비스 상품 리스트
	 */
	public List<ProductVO> selectProductList();
	/**
	 * 서비스 구매
	 * @param subVO (구독신청 정보)
	 * @return 서비스 구독 insert 성공 여부 
	 */
	public int buySub(SubVO subVO);
	/**
	 * 서비스기간 연장신청
	 * @param subVO
	 * @return 서비스 연장 신청 update 성공 여부
	 */
	public int updateStorage(SubVO subVO);

}
