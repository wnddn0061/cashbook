package com.gdu.cashbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@GetMapping("/signIn")
	public String signIn() {
		return "signIn";
	}
	
	@PostMapping("/signIn")
	public String signIn(Member member) {//현재 멤버는 Command 객체, 도메인 객체
		System.out.println(member);
		return "redirect:/index";
	}
	/*
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
