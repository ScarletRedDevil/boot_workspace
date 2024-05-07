package com.sds.movieapp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.movieapp.common.Pager;
import com.sds.movieapp.domain.Notice;
import com.sds.movieapp.exception.NoticeException;
import com.sds.movieapp.model.cs.notice.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private Pager pager;
	
	
	//게시물 목록
	@GetMapping("/cs/notice/list")
	public String getList(Model model, @RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		
		pager.init(noticeService.selectCount(), currentPage);
		
		HashMap map=new HashMap();
		map.put("startIndex", pager.getStartIndex());
		map.put("rowCount", pager.getPageSize());
		
		List noticeList = noticeService.selectAll(map); //3단계: 일 시키기 
		model.addAttribute("noticeList", noticeList); //4단계: 결과 저장
		model.addAttribute("pager", pager); //4단계: 결과 저장
		
		return "cs/notice/list";
	}
	
	//글쓰기 폼 요청 처리 
	@GetMapping("/cs/notice/writeform")
	public String getWriteForm() {
		return "cs/notice/regist";
	}
	
	//글쓰기 요청 처리 
	@PostMapping("/cs/notice/regist")
	public String regist(Notice notice) {
		noticeService.insert(notice); //3단계: 글 등록
		
		return "redirect:/cs/notice/list";
	}
	
	//글 한건 요청 처리 
	@GetMapping("/cs/notice/detail")
	public String getDetail(Notice notice, Model model) {
		log.debug("선택한 글의 id는 "+notice.getId());
		
		Notice dto = noticeService.select(notice);
		model.addAttribute("notice", dto);
		
		return "cs/notice/content";
	}
	
	//글 한건 수정 요청 처리 
	@PostMapping("/cs/notice/edit")
	public String edit(Notice notice) {
		
		noticeService.update(notice);
		
		return "redirect:/cs/notice/detail?id="+notice.getId();
	}
	
	@PostMapping("/cs/notice/del")
	public String del(Notice notice) {
		
		noticeService.delete(notice);
		
		return "redirect:/cs/notice/list";
	}
	
	@ExceptionHandler(NoticeException.class)
	public ModelAndView handle(NoticeException e) {
		ModelAndView mav = new ModelAndView("error/result");
		mav.addObject("e", e);
		
		return mav;
	}
	
}







