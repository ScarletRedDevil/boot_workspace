package com.sds.movieapp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sds.movieapp.domain.CustomUserDetails;
import com.sds.movieapp.domain.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

//모든 컨트롤러에서 사용자명을 저장할 수 있도록 aop로 구현 

@Slf4j
//@Aspect
//@Component //이제 스프링시큐리티가 로그인 자격을 확인하므로 여기서 하는건 중복행동이 됨. 간섭하지 않도록 지워야함
public class AuthAspect {
	
	//포함시킬 포인터컷
	@Pointcut("execution(public * com.sds.movieapp.controller..*(..))")
	public void includeExecution() {}
	
	//제외시킬 포인트컷
	@Pointcut("!execution(public * com.sds.movieapp.controller.Rest*Controller.*(..))")
	public void excludeExecution() {}
	
	//하위 컨트롤러로 들어가는 메서드 호출을 가로채서, 사용자 이름을 심어놓아야, header.html에서 사용자이름을 출력할 수 있다
	@Around("includeExecution() && excludeExecution()")
	public Object checkSession(ProceedingJoinPoint joinPoint) throws Throwable{
		Object obj=null;
		
		
		//스프링으로부터 세션을 얻기 
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		
		//auth.getPrincipal() 로그인 하지 않았을 경우 스프링 시큐리티가 auth.getPrincipal() 메서드의 반환값으로 
		//anonymousUser 를 반환함..따라서 조건문으로 처리해야 함 
		//아래의 if문은 로그인한 경우 동작함
		if(auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
			Member member = userDetails.getMember();
			
			request.setAttribute("nickname", member.getNickname());//우측 상단의 사용자명을 위한 처리 
			request.setAttribute("member", member);
			
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
		} 
		
		obj = joinPoint.proceed();
		
		return obj;
	}
}








