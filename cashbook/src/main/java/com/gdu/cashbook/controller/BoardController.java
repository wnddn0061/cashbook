package com.gdu.cashbook.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Board;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@GetMapping("/boardList")
	//게시판 리스트
	public String selectBoardList(Board board, HttpSession session) {
		//세션 : 로그인이 아니면 인덱스로
				if(session.getAttribute("loginMember")==null) {
					return "redirect:/index";
				}
		boardService.selectBoardList(board);
		return "/boardList";
	}
}
