package com.gdu.cashbook.controller;

import java.util.List;

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
	public String selectBoardList(Board board) {
		boardService.selectBoardList(board);
		return "/boardList";
	}
}
