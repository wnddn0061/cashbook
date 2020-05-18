package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper// Mapper의 기능 + Component의 기능
public interface MemberMapper {
	//ID중복확인
	public String checkMemberId(String checkMemberId);
	//로그인
	public LoginMember selectLoginMember(LoginMember loginMember);
	//회원가입
	public int signUp(Member member);
	//회원정보(상세보기)
	public Member selectMemberOne(LoginMember loginMember);
	//회원탈퇴
	public int removeMember(LoginMember loginMember); 
	//회원정보 수정
	public int modifyMember(Member member);
	//아이디찾기
	public String selectMemberIdByMember(Member member);
	//비밀번호찾기
	public int updateMemberPw(Member member);
	//파일 불러오기
	public String selectMemberPic(String memberId);
}
