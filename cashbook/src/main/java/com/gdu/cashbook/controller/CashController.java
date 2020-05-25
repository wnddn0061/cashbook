package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;

	
	//일 별 가게부 리스트
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
	
	//월별 가게부 리스트
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, @RequestParam(value="day", required = false)
									@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate day) {
		//세션 : 로그인이 아니면 인덱스로
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		
		Calendar cDay = Calendar.getInstance();
		//로컬데이터 타입을 ->calendar 타입으로	
		//값이 안넘어왔을때 오늘 날자
		if(day ==null) {//LocalDate type의 day
			day= LocalDate.now();
		}else {//null이 아니면 로컬데이트를 cDay의 세팅값으로
			//LocalDate -> Calendar 
			cDay.set(day.getYear(), day.getMonthValue()-1,day.getDayOfMonth());//
		}
		//일 별 수입 지출 총액
		String memberId=((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year= cDay.get(Calendar.YEAR);
		System.out.println(year+"<--year.ctrl");
		int month= cDay.get(Calendar.MONTH)+1;
		System.out.println(month+"<--month.ctrl");
		
		List<DayAndPrice> dayAndPriceList = cashService.getDayAndPriceList(memberId, year, month);
		System.out.println(dayAndPriceList+"<--Ctrl.dayAndPriceList");
		for(DayAndPrice dp : dayAndPriceList) {
			System.out.println(dp);
		}
		/*0. 오늘 LocalDate 타입
		 *1.오늘 Calendar타입
		 *2.이번 달의 마지막 날
		 *3.이번 달 1일의 요일
		 */
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		System.out.println(model+"<--Ctrl.model.dayAndPriceList");
		//LocalDate type의 day
		model.addAttribute("day", day);
		//현재 월
		model.addAttribute("month",cDay.get(Calendar.MONTH)+1);
		//마지막날짜
		model.addAttribute("lastDay",cDay.getActualMaximum(Calendar.DATE));
		
		Calendar firstDay = cDay;//첫날을 구함
		firstDay.set(Calendar.DATE,1);//cDay에서 일을 1로 세팅
		System.out.println("firstDay.get(Calendar.DAY_OF_WEEK):"+firstDay.get(Calendar.DAY_OF_WEEK));//요일 구하는 메소드(0 : 일요일 1: 월 2:화...6: 토요일)
		System.out.println(firstDay.get(Calendar.YEAR)+","+(firstDay.get(Calendar.MONTH)+1)+","+firstDay.get(Calendar.DATE));
		model.addAttribute("firstDayOfWeek",firstDay.get(Calendar.DAY_OF_WEEK));
	return "/getCashListByMonth";
	}
	//가계부 삭제
		@GetMapping("/removeCash")
		public String removeCashListByDate(HttpSession session, Cash cashNo, Model model, 
				@RequestParam(value="day",required=false)@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate day){
			//세션
			if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
			return "redirect:/index";
			}
			model.addAttribute("day", day);
			System.out.println(day+"<--Ctrl.remove.day");
			cashService.removeCashListByDate(cashNo);
			System.out.println(cashNo+"<--Ctrl.remove.cashNo");
		
			return "redirect:/getCashListByDate?day="+day;
	}
		
}
