package com.sds.movieapp;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sds.movieapp.model.movie.MovieService;

import jakarta.servlet.http.HttpServletRequest;

//스프링MVC에서는 xml파일에서 빈설정을 할수있었는데 boot에서는 @configuration 클래스에서 담당
//따라서 아래 클래스를 Aspect로 등록해 aop구현하기

//<bean> aspect가 될 빈 등록, 누구를
//<aop-config>에서 어느 지점에 어느 시점에 작동할지

@Aspect
@Component
public class MenuAspect {
	
	@Autowired
	private MovieService movieService;

	//일반컨트롤러에 대해 aop 적용
	@Pointcut("execution(public * com.sds.movieapp.controller..*(..))")
	public void includeExecution() {}
	
	//RestController들에 대해서는 적용  제외함.
	@Pointcut("!execution(public * com.sds.movieapp.controller.Rest*Controller.*(..))")
	public void excludeExecution() {}
	
	@Around("includeExecution() && excludeExecution()")
	public Object getMenu(ProceedingJoinPoint joinPoint) throws Throwable{
		Object obj = null;
		
		obj = joinPoint.proceed();// 대표컨트롤러가 원래 호출하려던 바로 그 하위컨트롤러의 메서드 호출
		//하위컨트롤러들의 메서드는 String or ModelAndView를 반환함.
		
		List movieTypeList = movieService.getMovieTypeList();
//		request객체에 담기
		
		//이 요청과 관련된 request객체 꺼내기 (스프링 자체에서 지원하는 객체 사용)
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		
		request.setAttribute("movieTypeList", movieTypeList);
		
		return obj;
	}
}
