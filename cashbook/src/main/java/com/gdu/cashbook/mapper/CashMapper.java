package com.gdu.cashbook.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
@Mapper
public interface CashMapper {
	//cash리스트
	//로그인 사용자의 오늘 날짜 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
	//수입,지출 
	public int selectCashKindSum(Cash cash);
	//가계부 추가
	//가계부 삭제
	
	//가계부 수정

}
