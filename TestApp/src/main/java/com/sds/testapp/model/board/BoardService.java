package com.sds.testapp.model.board;

import java.util.List;
import java.util.Map;

import com.sds.testapp.domain.Board;

public interface BoardService {
	public List selectBySearch(String title);
	public int getTotalCount();
	public List selectAll(Map map);
	public Board select(int board_idx);
	
	//서비스는 DAO에게 일 시킨 후, int를 반환받아 Exception 을 던질 수 있으므로 void로 가자
	public void insert(Board board); 
	public void update(Board board);
	public void delete(Board board);
}