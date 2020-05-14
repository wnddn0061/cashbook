package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	//빈 자동주입 에너테이션
	@Autowired
	private MemberService memberService;
	
	//ID중복확인 값 받기
	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck")String memberIdCheck) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")!=null) {
			return "redirect:/index";
				}
		//아이디 중복 확인
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		if(confirmMemberId == null) {
			//아이디를 사용할 수 없음
			model.addAttribute("memberIdCheck",memberIdCheck);
		}else {
			//아이디를 사용할 수 있음
			model.addAttribute("msg","사용중인 ID 입니다.");
		}
		return "/signUp";
	}
	
	//로그인
	//로그인 폼
	@GetMapping("/login")
	public String login( HttpSession session) {
		//세션 :로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")!=null) {
			return "redirect:/index";
		}
		return "login";
	}
	
	//로그인 액션
	@PostMapping("/login")
	public String login(Model model, LoginMember loginMember, HttpSession session) {//매개변수로 세션을 받아올 수 있음(==request.getSession()
		//세션 :로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")!=null) {
			return "redirect:/index";
		}
		
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println(returnLoginMember+"<--returnLoginMember");
		if(returnLoginMember == null) {//로그인 실패시 다시 로그인 창으로
			//포워딩
			model.addAttribute("msg","ID와 PW를 확인하세요.");
			return "/login";
		} else {//로그인(세션) 성공시
			session.setAttribute("loginMember", returnLoginMember);
		return "redirect:/home";
		}
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//세션 : 로그인이 아닐때 활성화
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		session.invalidate();
		return "redirect:/index";
	}
	
	//회원가입
	@GetMapping("/signUp")//Request.get의 단축
	public String signUp( HttpSession session) {
		//세션 : 로그인 중일 때 비활성화
				if(session.getAttribute("loginMember")!=null) {
					return "redirect:/index";
				}
		return "signUp"; //signUp.jsp
	}
	
	@PostMapping("/signUp")
	public String signUp(Member member,  HttpSession session) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")!=null) {
			return "redirect:/index";
		}
		memberService.signUpMember(member);
		//toString의 예시
		//현재 Member는 Command 객체, 도메인 객체 둘 다 사용됨
		System.out.println(member);
		return "redirect:/index";
		/*
		 원래는 이렇게 쓸 것을 줄여줌
		@PostMapping("/signUp")
		public String signIn(@RequestParam("memberId") String memberId,
							 @RequestParam("memberPw") String memberPw,
							 @RequestParam("memberName") String memberName,
							 @RequestParam("memberAddr") String memberAddr,
							 @RequestParam("memberPhone") String memberPhone,
							 @RequestParam("memberMail") String memberMail) {
			return "redirect:/";
		}
		*/
	}
	
	//회원정보(상세보기
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		model.addAttribute("member", member);
		System.out.println(member+"<--Controller.memberInfo.member");
		return "memberInfo";
	}

}
