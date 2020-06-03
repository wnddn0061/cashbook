package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
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
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session, Board board, Model model) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
			return "redirect:/index";
		}
		return "addBoard";
	}
	@PostMapping("/addBoard")
	public String addBoard(HttpSession session, Model model,Board board) {
		//세션 : 로그인 중일 때 비활성화
		if(session.getAttribute("loginMember")==null) {//로그인이 안돼있으면 인덱스로
			return "redirect:/index";
		}
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		board.setAdminId(memberId);
		
		boardService.addBoarListMember(board);
		return "redirect:/addBoard";
	}
}
