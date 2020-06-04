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
	//게시판 리스트
	public List<Board> selectBoardListMember(Board board){
		List<Board> boardList = boardMapper.selectBoardListMember(board);
		return boardMapper.selectBoardListMember(board);
	}
	//게시물 상세보기
	public Board selectBoardListMemberOne(int boardNo) {
		return boardMapper.selectBoardListMemberOne(boardNo);
	}
	//게시물 추가하기
	public void addBoarListMember(Board board) {
		boardMapper.addBoardList(board);
	}

}
