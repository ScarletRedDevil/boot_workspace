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

//모든 컨트롤러에서 사용자명을 저장할 수 있도록 aop 구현

@Slf4j
@Aspect
@Component
public class AuthAspect {
	// 포함시킬 포인트컷
	@Pointcut("execution(public * com.sds.movieapp.controller..*(..))")
	public void includeExecution() {
	}

	// 제외시킬 포인트컷
	@Pointcut("!execution(public * com.sds.movieapp.controller.Rest*Controller.*(..))")
	public void excludeExecution() {
	}

	// 하위컨트롤러로 들어가는 메서드 호출을 가로채서 사용자 이름을 심어놓아야 header.html에 사용자이름을 출력할 수 있음.
	@Around("includeExecution() && excludeExecution()")
	public Object checkSession(ProceedingJoinPoint joinPoint) throws Throwable {
		Object obj = null;

		// 스프링으로부터 세션 얻어오기
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String nickname = auth.getName();

		// 아래 if문은 로그인한 경우에 동작함.
		if (auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
			Member member = userDetails.getMember();

			request.setAttribute("nickname", member.getNickname());//우측 사용자명 보여주기
			request.setAttribute("member", member);//모든 컨트롤러에 이름 가져오기
			//만약 다른 정보도 받아오고 싶다면 위처럼 추가하면 됨.
		}
		
		obj = joinPoint.proceed();

		return obj;
	}

}
