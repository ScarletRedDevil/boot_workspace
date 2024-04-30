package com.sds.movieapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.movieapp.common.Pager;
import com.sds.movieapp.domain.MovieType;
import com.sds.movieapp.model.movie.MovieService;

@Controller
public class MainController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private Pager pager;
	
	@GetMapping("/")
	public String getMain(Model model, @RequestParam(value = "currentPage",defaultValue = "1") int currentPage) {
		//@requestParam으로 int currentPage가 null값이거나 하는 경우를 대비해 기본 값을 준다. 
		pager.init(movieService.selectCount(), currentPage);
		//영화 10편 가져오기
		Map map = new HashMap();
		map.put("startIndex", pager.getStartIndex());//몇번째부터
		map.put("rowCount", pager.getPageSize());//몇 건
		movieService.selectAll(map);
		
		List<MovieType> movieTypeList = movieService.getMovieTypeList(); //3단계
		model.addAttribute("movieTypeList", movieTypeList);//4단계 : 뷰에 보여줄 결과 저장
		
		return "main/index";
	}
}
