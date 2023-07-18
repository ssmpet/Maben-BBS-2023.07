package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BbsUserController
 */
@WebServlet("/user/*")
public class UserController extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] uri = request.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		
		RequestDispatcher rd = null;
		switch (action) {
		case "login":
			if (request.getMethod().equals("GET")) {
				rd = request.getRequestDispatcher("/WEB-INF/view/user/login.jsp");
				rd.forward(request, response);
			} else {

			}
			break;
			
		case "list":
			rd = request.getRequestDispatcher("/WEB-INF/view/user/list.jsp");
			rd.forward(request, response);
			break;
			
		case "register":
			if (request.getMethod().equals("GET")) {
				rd = request.getRequestDispatcher("/WEB-INF/view/user/register.jsp");
				rd.forward(request, response);
			} else {
				
				String uid = request.getParameter("uid");
				String pwd = request.getParameter("pwd");
				String pwd2 = request.getParameter("pwd2");
				String uname = request.getParameter("uname");
				String email = request.getParameter("email");
				String addr = request.getParameter("addr");
				String profile = request.getParameter("profile");

			}
			break;
		}
		
	}

}
