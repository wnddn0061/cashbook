package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value="day",required=false)
									@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate day) { //형변환
		//세션 : 로그인이 아니면 인덱스로
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		//day 값이 null이면 현재 시간을 넣음
		if(day==null) {
			day = LocalDate.now();
		}
		System.out.println(day);
		
		//로그인 아이디
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		
		/*
		//오늘 날짜를 생성 원하는 문자열 형태로 변경
		Date day = new Date();
		//날짜 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(day);
		System.out.println(strToday + "<==strToday");
		*/
		
		//1회용 캐시타입
		Cash cash = new Cash();//+id, + date("yyyy-MM-dd")
		cash.setMemberId(loginMemberId);//아이디 호출
		cash.setCashDate(day.toString());
		//toString" 메서드는 객체가 가지고 있는 정보나 값들을 문자열로 만들어 리턴하는 메소드
		System.out.println(loginMemberId+"<--Ctrl.loginMemberId");
		
		//호출한 아이디,날짜를 리스트로
		Map<String,Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("day", day);
		//모델 안에 리스트를 담아줌
		model.addAttribute("cashList", map.get("cashList"));
		//모델 안에 오늘 날짜를 담아줌 
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		
		System.out.println(map+"<--cashController.map");
		System.out.println(model+"<--cashController.model");
	
		return "getCashListByDate";
		}
}
