package spring.common.web;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import spring.domain.User;

public class LogonCheckInterceptor extends HandlerInterceptorAdapter {
//public class LogonCheckInterceptor implements HandlerInterceptor {

	// Field

	// Constructor
	public LogonCheckInterceptor() {
		System.out.println(":: "+getClass().getName()+" default Constructor call\n");
	}

	// Method
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) 
							 throws Exception {
		
		System.out.println("[ LogonCheckInterceptor start ]");
		
		// 로그인 유무 확인
		HttpSession session = request.getSession();
		User sessionUser = null;
		
		if ((sessionUser = (User) session.getAttribute("sessionUser")) == null) {
			sessionUser = new User();
			
		}
		
		// 로그인 회원의 경우
		if (sessionUser.isActive()) {
			String uri = request.getRequestURI();
			
			// 로그인 상태에서 로그인 시도
			if (uri.indexOf("logonAction") != -1 || uri.indexOf("logon") != -1) {
				request.getRequestDispatcher("/user/home.jsp").forward(request, response);
				
				System.out.println("[ 로그인 상태! 로그인 후 불필요한 요구 ]");
				System.out.println("[ LogonCheckInterceptor end ]\n");
				
				return false;
			}
			
			System.out.println("[ 로그인 상태 ]");
			System.out.println("[ LogonCheckInterceptor end ]\n");
			
			return true;
			
		} else {	// 미로그인한 회원
			// 로그인 시도
			String uri = request.getRequestURI();
			
			if (uri.indexOf("logonAction") != -1 || uri.indexOf("logon") != -1) {
				System.out.println("[ 로그인 시도 상태 ]");
				System.out.println("[ LogonCheckInterceptor end ]\n");
				
				return true;
			}
			
			request.getRequestDispatcher("/user/logon.jsp").forward(request, response);
			
			System.out.println("[ 로그인 이전 ]");
			System.out.println("[ LogonCheckInterceptor end ]\n");
			
			return false;
		}
		
	}

}
// class end