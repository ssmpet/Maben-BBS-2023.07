package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

import com.mysql.cj.x.protobuf.MysqlxCrud.Delete;

import db.UserDao;
import entity.User;
import utility.UserService;

/**
 * Servlet implementation class BbsUserController
 */
@WebServlet("/user/*")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1, 		// 1 MB
		maxFileSize = 1024 * 1024 * 10,				// 10 MB
		maxRequestSize = 1024 * 1024 * 10
)
public class UserController extends HttpServlet {
	public static final String PROFILE_PATH = "c:/temp/profile/";
	public static final int LIST_PER_PAGE = 10;		// 한 페이지당 사용자 목록의 갯수
	public static final int PAGE_PER_SCREEN = 10;	// 한 화면에 표시되는 페이지 갯수
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String[] uri = request.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		HttpSession session = request.getSession();
		session.setAttribute("menu", "user");
		UserDao uDao = new UserDao();
		
		RequestDispatcher rd = null;
		User user = null;
		String uid = null,  pwd = null, pwd2 = null, uname = null, email = null, addr = null, filename = null;
		Part filePart = null;
		
		switch (action) {
		case "login":
			if (request.getMethod().equals("GET")) {
				rd = request.getRequestDispatcher("/WEB-INF/view/user/login.jsp");
				rd.forward(request, response);
			} else {
				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd");
				
				UserService us = new UserService();
				
				int result = us.login(uid, pwd);
				
				if (result == UserService.CORRECT_LOGIN) {

					user = uDao.getUser(uid);
					request.getSession().setAttribute("uid", uid);
					request.getSession().setAttribute("uname", user.getUname());
					request.getSession().setAttribute("email", user.getEmail());
					request.getSession().setAttribute("profile", user.getProfile());
					request.getSession().setAttribute("addr", user.getAddr());
				
					request.setAttribute("msg", user.getUname() + "님 환영합니다.");
					request.setAttribute("url", "/bbs/board/list?p=1&f=&q=");
					rd = request.getRequestDispatcher("/WEB-INF/view/common/alertMsg.jsp");
					rd.forward(request, response);				
					
				} else if (result == UserService.WRONG_PASSWORD) {
					request.setAttribute("msg", "잘못되 패스워드 입니다. 다시 입력하세요.");
					request.setAttribute("url", "/bbs/user/login");
					rd = request.getRequestDispatcher("/WEB-INF/view/common/alertMsg.jsp");
					rd.forward(request, response);				
					
				}else {
					request.setAttribute("msg", "ID가 없습니다. 회원 가입 페이지로 이동합니다.");
					request.setAttribute("url", "/bbs/user/register");
					rd = request.getRequestDispatcher("/WEB-INF/view/common/alertMsg.jsp");
					rd.forward(request, response);		
				}

			}
			break;
		
		case "logout":
			session.invalidate();
			response.sendRedirect("/bbs/user/login");
			break;
			
		case "list":
			String page = request.getParameter("page");
			List<User> list = uDao.getUserList(Integer.parseInt(page));
			request.setAttribute("userList", list);
			
			int totalUsers = uDao.getUserCount();
			int totalPages = (int) (Math.ceil(totalUsers / (double) LIST_PER_PAGE));
			int startPage = (int) Math.ceil((Integer.parseInt(page) - 0.5) / PAGE_PER_SCREEN - 1) * PAGE_PER_SCREEN + 1;
			int endPage = Math.min(totalPages, startPage + PAGE_PER_SCREEN - 1);
			
			List<String> pageList = new ArrayList<String>();
			for (int i=startPage; i<=endPage; i++) {
				pageList.add(String.valueOf(i));
			}
			session.setAttribute("currentUserPage", page);
			
			request.setAttribute("pageList", pageList);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			
			rd = request.getRequestDispatcher("/WEB-INF/view/user/list.jsp?page=" + page);
			rd.forward(request, response);
			break;
			
		case "register":
			if (request.getMethod().equals("GET")) {
				rd = request.getRequestDispatcher("/WEB-INF/view/user/register.jsp");
				rd.forward(request, response);
			} else {

				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd");
				pwd2 = request.getParameter("pwd2");
				uname = request.getParameter("uname");
				email = request.getParameter("email");
				addr = request.getParameter("addr");

				filePart = request.getPart("profile");
				
				try {
					filename = filePart.getSubmittedFileName();
					int dotPosition = filename.indexOf(".");
					String firstPart = filename.substring(0, dotPosition);
					filename = filename.replace(firstPart, uid);
					filePart.write(PROFILE_PATH + filename);
				} catch (Exception e) {
					System.out.println("프로필 사진을 입력하지 않았습니다.");			
				}

				// uid가 중복 --> 등록 화면
				if (uDao.getUser(uid) != null) {
					request.setAttribute("msg", "사용자 ID가 중복되었습니다. ");
					request.setAttribute("url", "/bbs/user/register");
					rd = request.getRequestDispatcher("/WEB-INF/view/common/alertMsg.jsp");
					rd.forward(request, response);
				} else if (!pwd.equals(pwd2)) { 	// pwd == pwd2 --> 등록화면
					request.setAttribute("msg", "패스워드 입력이 잘못되었습니다.");
					request.setAttribute("url", "/bbs/user/register");
					rd = request.getRequestDispatcher("/WEB-INF/view/common/alertMsg.jsp");
					rd.forward(request, response);
				} else {
					String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
					user = new User(uid, hashedPwd, uname, email, filename, addr);
					uDao.registerUser(user);
					request.setAttribute("msg", "등록을 마쳤습니다. 로그인 하세요.");
					request.setAttribute("url", "/bbs/user/login");
					rd = request.getRequestDispatcher("/WEB-INF/view/common/alertMsg.jsp");
					rd.forward(request, response);
				}
			}
			break;
			
		case "update":
			if (request.getMethod().equals("GET")) {
				uid = request.getParameter("uid");
				user = uDao.getUser(uid);
				request.setAttribute("user", user);
				
				rd = request.getRequestDispatcher("/WEB-INF/view/user/update.jsp");
				rd.forward(request, response);
			} else {
				uid = request.getParameter("uid");
				uname = request.getParameter("uname");
				email = request.getParameter("email");
				addr = request.getParameter("addr");
				String oldFilename = request.getParameter("filename");
				
				filePart = request.getPart("profile");

				try {
					filename = filePart.getSubmittedFileName();
					int dotPosition = filename.indexOf(".");

					if ( !(oldFilename == null && oldFilename.equals("")) && !(filename == null && filename.equals(""))) {
						File oldFile = new File(PROFILE_PATH + oldFilename);
						oldFile.delete();
					}
					
					String firstPart = filename.substring(0, dotPosition);
					filename = filename.replace(firstPart, uid);
					filePart.write(PROFILE_PATH + filename);			
				} catch (Exception e) {
					System.out.println("프로필 사진을 변경하지 않았습니다.");
				}
				filename = (filename == null || filename.equals("")) ? oldFilename : filename;
//				System.out.println(filename);
				user = new User(uid, uname, email, filename, addr);
				uDao.updateUser(user);
				request.getSession().setAttribute("uname", uname);
				request.getSession().setAttribute("email", email);
				request.getSession().setAttribute("profile", filename);
				request.getSession().setAttribute("addr", addr);
				response.sendRedirect("/bbs/user/list?page=" + session.getAttribute("currentUserPage"));
			}
			break;
		case "updatePwd":
			if (request.getMethod().equals("GET")) {
				rd = request.getRequestDispatcher("/WEB-INF/view/user/updatePwd.jsp?uid=" + request.getParameter("uid"));
				rd.forward(request, response);
			} else {
				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd");
				pwd2 = request.getParameter("pwd2");
		
				// 패스워드 확인
				if ( !(pwd == null || pwd.equals("")) ) {
					// 패스워드와 패스워드확인이 같지 않으면 
					if (!pwd.equals(pwd2)) {
						request.setAttribute("msg", "패스워드 입력이 잘못되었습니다.");
						request.setAttribute("url", "/bbs/user/updatePwd?uid=" + uid);
					} else {

						pwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
						uDao.updateUserPassword(pwd, uid);
						request.setAttribute("msg", "패스워드가 변경되었습니다.");
						request.setAttribute("url", "/bbs/user/update?uid=" + uid);
					}
				} else {
					
					request.setAttribute("msg", "패스워드를 입력하지 않았습니다.");
					request.setAttribute("url", "/bbs/user/updatePwd?uid=" + uid);
				}

				rd = request.getRequestDispatcher("/WEB-INF/view/common/alertMsg.jsp");
				rd.forward(request, response);					
			}
			break;
			
		case "delete":
			uid = request.getParameter("uid");
			rd = request.getRequestDispatcher("/WEB-INF/view/user/delete.jsp?uid=" + uid);
			rd.forward(request, response);
			break;
			
		case "deleteConfirm":
			uid = request.getParameter("uid");
			uDao.deleteUser(uid);
			response.sendRedirect("/bbs/user/list?page=" + session.getAttribute("currentUserPage"));
			break;
			
		case "testuser":
//			pwd = "1234";
//			String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
//			addr = "경기도 수원시 장안구";
//			
//			for (int i=21; i<=270; i++) {
//				uid = "user" + i;
//				uname = "사용자" + i;
//				email = uid + "@naver.com";
//				user = new User(uid, hashedPwd, uname, email, filename, addr);
//				uDao.registerUser(user);				
//			}
			response.sendRedirect("/bbs/user/list?page=1");
			break;
		default:
			rd = request.getRequestDispatcher("/WEB-INF/view/error/error404.jsp");
			rd.forward(request, response);
			break;
		
		}

	}

}
