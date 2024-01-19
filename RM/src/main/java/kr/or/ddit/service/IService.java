package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProductVO;
import kr.or.ddit.vo.SubVO;

public interface IService {

	public int registerMember(MemberVO memberVO);

	public MemberVO selectMember(MemberVO memberVO);

	public List<ProductVO> selectProductList();

	public MemberVO buySub(SubVO subVO);

	public ServiceResult updateStorage(SubVO subVO);


}
