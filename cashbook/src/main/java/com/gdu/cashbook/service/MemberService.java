package com.gdu.cashbook.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
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
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	//ID중복확인
	public String checkMemberId(String checkMemberId) {
		return memberMapper.checkMemberId(checkMemberId);
	}
	//회원가입
	public int signUpMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		//확장자 필요
		String originName = mf.getOriginalFilename();
		System.out.println(originName+"<--Service.OriginName");
		int lastDot = originName.lastIndexOf("."); 
		String extension = originName.substring(lastDot);
		/*
		//이미지 파일만 받으려면
		if(mf.getContentType().equals("image/png") || mf.getContentType().equals("image/jpeg")) {//jpeg나 png파일만 받을 수 있다
			//업로드
		}else {
			//업로드 실패
		}
		*/
		//새로운 이름을 생성 :UUID
		//분리
		String memberPic = memberForm.getMemberId()+extension;
		
		//3.서비스에 보내기
		
		//memberForm -> member
		//파일->디스크에 물리적으로 저장
		//1. db에 저장
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberMail(memberForm.getMemberMail());
		member.setMemberPic(memberPic);
		int row =memberMapper.signUp(member);
		System.out.println(member+"<--memberService.member");
		
		//2.파일 저장
		String path = "D:\\git-cashbook\\cashbook\\src\\main\\resources\\static\\upload";
		//빈파일 생성
		File file = new File(path+"\\"+memberPic);
		try {
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			//자바의 예외 1. 예외처리를 해야만 문법적으로 이상이 없는 예외
			//자바의 예외 2. 코드에서 구현하지 않아도 아무 문제없는 예외
		}
		
		return row;
		//return memberMapper.signUp(member);
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
		//memberid에 추가
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
	//ID 찾기
	public String getMemberIdByString(Member member) {
		return memberMapper.selectMemberIdByMember(member);	
	}
	//Pw 찾기
	public int getMemberPw(Member member) {
		//pw추가
		UUID uuid = UUID.randomUUID();//랜덤문자열 생성
		
		String memberPw = uuid.toString().substring(0,8); //문자열로 만들어 달라(앞에서 8자리만)
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		if(row ==1 ) {
			System.out.println(memberPw+"<--update memberPW");
			//메일로 update 성공한 랜덤 pw를 전송
			//메일객체 new JavaMailSender();
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			//받는 사람 
			mailMessage.setTo(member.getMemberMail());
			//보낸 사람
			mailMessage.setFrom("wnddn56@gmail.com");
			//제목
			mailMessage.setSubject("cashbook 비밀번호 찾기");
			//내용
			mailMessage.setText("비밀번호는"+memberPw+"입니다");
			javaMailSender.send(mailMessage);
		}
		return row;
	}
}
