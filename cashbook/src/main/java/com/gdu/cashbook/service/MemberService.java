package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;

@Service
//트랜잭션을 처리할 수 있다.
@Transactional
//클래스 실행 중 예외 발생 시 롤백
public class MemberService {
	@Autowired
	//@Transactional
	//@Transactional 이 클래스 안에 있을 시 해당 메소드 실행 중 예외 발생 시 롤백
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	
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
	
	//상세보기
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	//회원탈퇴
	public void removeMember(LoginMember loginMember) {
		//System.out.println(loginMember+"<--Service.remove.loginMember");
		//추가
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		//삭제
		memberMapper.removeMember(loginMember);
	}
	//회원정보 수정
	public int modifyMember(Member member) {
		return memberMapper.modifyMember(member);
	}

}
