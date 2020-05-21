package com.gdu.cashbook.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("D:\\git-cashbook\\cashbook\\src\\main\\resources\\static\\upload\\")
	private String path;
	
	
	//ID중복확인
	public String checkMemberId(String checkMemberId) {
		return memberMapper.checkMemberId(checkMemberId);
	}
	//회원가입
	public int signUpMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		//확장자를 찾기 위해 originName 출력
		String originName = mf.getOriginalFilename();
		System.out.println(originName+"<--Service.OriginName");
		//확장자 자르기
		int lastDot = originName.lastIndexOf("."); //.을 찾아라
		String extension = originName.substring(lastDot);
		/*
		//특정 파일만 받으려면
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
		//빈파일 생성
		File file = new File(path+memberPic);
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
		//멤버이미지 파일 삭제
		//1.파일 이름 member_pic을 가져와야함(select member_pic from member
		//파일 이름 불러오기
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		//2.파일삭제
		File file = new File(path+memberPic);
		if(file.exists()) {//파일이 있으면
			file.delete();//삭제
		}
		
		//memberid에 추가
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		
		//삭제
		memberMapper.removeMember(loginMember);
	}
	
	
	//회원정보 수정
	//이미지 파일이 있을 시 삭제 -> 삽입
	public int modifyMember(MemberForm memberForm) {
		//1.파일 불러오기
		String originNamePic = memberMapper.selectMemberPic(memberForm.getMemberId());
		MultipartFile mpf = memberForm.getMemberPic();
		//확장자를 찾기 위해 originName 출력
		String originName = mpf.getOriginalFilename();
		System.out.println(originNamePic+"<--originNamePic.Service.modify");
		//memberPic 초기화
		String memberPic = null;
		//기존 파일 삭제
		if(originName.equals("")) {//파일이 없을 경우 원래있던 파일(ex:default)의 이름이랑 같게 해줌
			memberPic = originNamePic;
		}else {
			//새파일 생성
			File rmFile = new File(path+"\\"+memberPic);
			//원래 파일이 아닐 경우 삭제
			if(rmFile.exists() && !originNamePic.equals("default.jpg")) {
				rmFile.delete();
			}
			//확장자 자르기
			int lastDot = originName.lastIndexOf(".");//지정된 문자(".")를 찾는 위치
			String extension = originName.substring(lastDot);
			//db에 저장
			//새로운 이름을 생성 :UUID
			memberPic = memberForm.getMemberId()+extension;
			System.out.println(memberPic);
		}
		//memberForm을 member로 형변환
		//파일을 디스크에 물리적 저장
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberMail(memberForm.getMemberMail());
		member.setMemberPic(memberPic);
		int row =memberMapper.modifyMember(member);
		System.out.println(member+"<--member.Service");
		
		if(!originName.equals("")) {
		//빈 파일 생성 및 저장
		File filePic = new File(path+"\\"+originNamePic);
		//예외처리
		try {
			mpf.transferTo(filePic);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			}
		}
		return row;
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
