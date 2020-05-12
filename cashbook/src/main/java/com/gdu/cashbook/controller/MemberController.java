package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/signIn")
	public String signIn() {
		return "signIn"; //signIn.jsp
	}
	
	@PostMapping("/signIn")
	public String signIn(Member member) {
		memberService.signInMember(member);
		//toString 예시
		//현재 Member는 Command 객체, 도메인 객체 둘 다 사용됨
		System.out.println(member);
		return "redirect:/index";
	}
	/*
	 원래는 이렇게 쓸 것을 줄여줌
	@PostMapping("/signIn")
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
