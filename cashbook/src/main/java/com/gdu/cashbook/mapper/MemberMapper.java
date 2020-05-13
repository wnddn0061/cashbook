package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	//ID중복확인
	public String checkMemberId(String checkMemberId);
	//로그인
	public LoginMember selectLoginMember(LoginMember loginMember);
	//회원가입
	public int signUp(Member member);
}
