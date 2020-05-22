package com.gdu.cashbook.mapper;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;
@Mapper
public interface CashMapper {
	//cash리스트
	//로그인 사용자의 오늘 날짜 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
	//수입,지출 일 별 합계
	public int selectCashKindSum(Cash cash);
	//수입,지출 월 별 합계
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	//가계부 추가
	public int addCashListByDate(Cash cash);
	//가계부 삭제
	public int removeCashListByDate(Cash cashNo);
	//가계부 수정
	public int modifyCashListByDate(Cash Cash);
}
