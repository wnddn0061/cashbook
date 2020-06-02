package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	//board리스트
	public List<Board> selectBoardList(Board board);
	//게시물 추가
	public int addBoard(Board board);
	//게시물 수정
	public int modifyBoard(Board board);
	//게시물 삭제
	public int removeBoard(int boardNo);
}
