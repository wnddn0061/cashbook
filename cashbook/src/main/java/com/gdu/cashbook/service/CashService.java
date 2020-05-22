package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;


@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	
	public Map<String, Object> getCashListByDate(Cash cash){
		List<Cash> cashList = cashMapper.selectCashListByDate(cash);
		
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
	//월별 합계
	public List<DayAndPrice> getDayAndPriceList(String memberId, int year, int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		System.out.println(map+"<--map.Service.memberId");
		map.put("year", year);
		System.out.println(map+"<--map.Service.year");
		map.put("month", month);
		System.out.println(map+"<--map.Service.month");
		return cashMapper.selectDayAndPriceList(map);
	}
	//삭제
	public int removeCashListByDate(Cash cashNo) {
		//삭제
		return cashMapper.removeCashListByDate(cashNo);
	}
}
