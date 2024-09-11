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
		
		// �α��� ���� Ȯ��
		HttpSession session = request.getSession();
		User sessionUser = null;
		
		if ((sessionUser = (User) session.getAttribute("sessionUser")) == null) {
			sessionUser = new User();
			
		}
		
		// �α��� ȸ���� ���
		if (sessionUser.isActive()) {
			String uri = request.getRequestURI();
			
			// �α��� ���¿��� �α��� �õ�
			if (uri.indexOf("logonAction") != -1 || uri.indexOf("logon") != -1) {
				request.getRequestDispatcher("/user/home.jsp").forward(request, response);
				
				System.out.println("[ �α��� ����! �α��� �� ���ʿ��� �䱸 ]");
				System.out.println("[ LogonCheckInterceptor end ]\n");
				
				return false;
			}
			
			System.out.println("[ �α��� ���� ]");
			System.out.println("[ LogonCheckInterceptor end ]\n");
			
			return true;
			
		} else {	// �̷α����� ȸ��
			// �α��� �õ�
			String uri = request.getRequestURI();
			
			if (uri.indexOf("logonAction") != -1 || uri.indexOf("logon") != -1) {
				System.out.println("[ �α��� �õ� ���� ]");
				System.out.println("[ LogonCheckInterceptor end ]\n");
				
				return true;
			}
			
			request.getRequestDispatcher("/user/logon.jsp").forward(request, response);
			
			System.out.println("[ �α��� ���� ]");
			System.out.println("[ LogonCheckInterceptor end ]\n");
			
			return false;
		}
		
	}

}
// class end