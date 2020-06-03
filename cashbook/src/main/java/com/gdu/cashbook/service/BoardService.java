package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public List<Board> selectBoardListMember(Board board){
		List<Board> boardList = boardMapper.selectBoardListMember(board);
		return boardMapper.selectBoardListMember(board);
	}
	public Board selectBoardListMemberOne(int boardNo) {
		return boardMapper.selectBoardListMemberOne(boardNo);
	}
	public Board addBoarListMember(Board board) {
		return boardMapper.addBoardList(board);
	}

}
