package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Service
//트랜잭션을 처리할 수 있다.
@Transactional
//클래스 실행 중 예외 발생 시 롤백
public class MemberService {
	@Autowired
	//@Transactional
	//@Transactional 이 클래스 안에 있을 시 해당 메소드 실행 중 예외 발생 시 롤백
	private MemberMapper memberMapper;
	//ID중복확인
	public String checkMemberId(String checkMemberId) {
		return memberMapper.checkMemberId(checkMemberId);
	}
	//회원가입
	public int signUpMember(Member member) {
		return memberMapper.signUp(member);
	}
	//로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}

}
