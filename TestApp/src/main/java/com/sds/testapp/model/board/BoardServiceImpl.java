package com.sds.testapp.model.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.testapp.domain.Board;
import com.sds.testapp.exception.BoardException;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private MybatisBoardDAO mybatisBoardDAO;
	
	@Override
	public int getTotalCount() {
		return mybatisBoardDAO.getTotalCount();
	}
	
	@Override
	public List selectBySearch(String title) {
		return mybatisBoardDAO.selectBySearch(title);
	}
	
	public List selectAll(Map map) {
		return mybatisBoardDAO.selectAll(map);
	}

	@Override
	public Board select(int board_idx) {
		return mybatisBoardDAO.select(board_idx);
	}

	@Override
	public void insert(Board board) throws BoardException{
		int result = mybatisBoardDAO.insert(board);
		
		if(result < 1) {
			throw new BoardException("글등록 실패");
		}
		
	}

	@Override
	public void update(Board board) throws BoardException{
		int result = mybatisBoardDAO.update(board);
		
		if(result <1) {
			throw new BoardException("수정실패");
		}
		
	}

	@Override
	public void delete(Board board) throws BoardException{
		int result = mybatisBoardDAO.delete(board);
		
		if(result <1) {
			throw new BoardException("삭제실패");
		}
		
	}

}