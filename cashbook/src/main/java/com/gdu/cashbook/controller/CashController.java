package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	@GetMapping("/getCashListByDate")
	//세션 : 로그인 중이 아니면 인덱스로
	public String getCashListByDate(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
	}
	//로그인 아이디
	String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
	//오늘 날짜를 생성 원하는 문자열 형태로 변경
	Date today = new Date();
	//날짜 포맷
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String strToday = sdf.format(today);
	System.out.println(strToday + "<==strToday");
	
	//1회용 캐시타입
	Cash cash = new Cash();//+id, + date("yyyy-MM-dd")
	cash.setMemberId(loginMemberId);//아이디 호출
	System.out.println(loginMemberId+"<--Ctrl.loginMemberId");
	cash.setCashDate(strToday);//날짜 호출
	System.out.println(strToday+"<--Ctrl.strToday");
	
	
	//호출한 아이디,날짜를 리스트로
	List<Cash> cashList = cashService.getCashListByDate(cash);
	System.out.println(cashList+"<--cashController.List");
	//모델 안에 리스트를 담아줌
	model.addAttribute("cashList", cashList);
	System.out.println(model+"<--cashController.model");
	//모델 안에 오늘 날짜를 담아줌 
	model.addAttribute("today", today);
	System.out.println(model+"<--cashController.model");
	//디버깅
	for(Cash c : cashList) {
		System.out.println(c);
	}
		return "getCashListByDate";
	}
}
