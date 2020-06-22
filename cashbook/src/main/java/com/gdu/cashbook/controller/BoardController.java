package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private String boardDate;
	//게시판 리스트
	@GetMapping("/getBoardList")
	public String selectBoardListMember(HttpSession session, Model model) {
		//세션
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		String loginMember = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(loginMember+"<--loginMember");
		
		Board board = new Board();
		board.setMemberId(loginMember);
		
		List<Board> bd = boardService.selectBoardListMember(board);
		model.addAttribute("board", bd);
		
		return "getBoardList";
	}
	//게시물 상세보기
	@GetMapping("/getBoardListOne")
	public String boardInfo(HttpSession session, Model model, int boardNo) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
			return "redirect:/index";
		}
		
		
		String loginMember = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		
		Board board = boardService.selectBoardListMemberOne(boardNo);
		model.addAttribute("board", board);
		return "getBoardListOne";
	}
	//게시물 추가하기 form
	
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session, Model model,Board board, @RequestParam(value="day", required=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")LocalDate day) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
			return "redirect:/index";
		}
		if(day==null) {
			day=LocalDate.now();
		}
		System.out.println(day+"<--Ctrl.add.day");
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(memberId+"<--Ctrl.add.Get.memberId");
		board.setMemberId(memberId);
		board.setBoardDate(day.toString());
		model.addAttribute("day", day);
		System.out.println(model+"<--Ctrl.add.get.day");
	
		return "addBoard";
	}
	//게시물 추가하기 action
	@PostMapping("/addBoard")
	public String addBoard(HttpSession session, Board board) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
			return "redirect:/index";
		}
		String loginMember = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(loginMember+"<--loginMember");
		
		board.setMemberId(loginMember);
		System.out.println(board+"<--Ctrl.addBoard.board");
		
		
		boardService.addBoarListMember(board);
		return "redirect:/getBoardList";
	}
	//게시물 수정하기 form
	@GetMapping("/modifyBoardList")
	public String modifyBoardList(HttpSession session, Model model, @RequestParam("boardNo") int boardNo) {
		System.out.println(boardNo+"<--boardNo.ctrl.modify");
		//세션
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
		return "redirect:/index";
		}
		Board board = boardService.selectBoardListMemberOne(boardNo);
		model.addAttribute("board", board);
		return "modifyBoardList";
	}
	
	//게시물 수정하기 action
	@PostMapping("/modifyBoardList")
	public String modifyBoardList(Board board, HttpSession session) {
		//세션
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
		return "redirect:/index";
		}
		
		boardService.modifyBoardList(board);
		return "redirect:/getBoardList";
	}
	
	//게시물 삭제 
	@GetMapping("/removeBoardList")
	public String removeBoardList(HttpSession session, @RequestParam("boardNo")int boardNo) {
		//세션
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
		return "redirect:/index";
		}
		boardService.removeBoardList(boardNo);
		return "redirect:/getBoardList";
	}
}
