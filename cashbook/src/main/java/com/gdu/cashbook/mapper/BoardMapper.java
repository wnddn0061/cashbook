package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	//게시판 리스트
	public List<Board> selectBoardListMember(Board board);
	public Board selectBoardListMemberOne(int boardNo);
	public Board addBoardList(Board board);

}
