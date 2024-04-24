package com.sds.testapp.model.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.testapp.domain.Board;


//@Mapper 어노테이션을 지정하면, 아래의 인터페이스 객체는
//Mybatis 의 id 와 연결됨
@Mapper
public interface MybatisBoardDAO {
	public List selectBySearch(String title);
	public List selectAll(Map map);
	public int getTotalCount();
	public Board select(int board_idx);
	public int insert(Board board); // int 로 하는 이유는?  SqlSessionTemplate 을 사용하지 않기 때문에 int 반환
													//받을 기회가 사라져 버려서..
	public int update(Board board);
	public int delete(Board board);
}








