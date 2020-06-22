package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	//게시판 리스트
	public List<Board> selectBoardListMember(Board board);
	//게시글 상세보기
	public Board selectBoardListMemberOne(int boardNo);
	//게시글 추가하기
	public void addBoardList(Board board);
	//게시글 수정하기
	public int modifyBoardList(Board board);
	//게시글 삭제하기
	public int removeBoardList(int boardNo);
	

}
