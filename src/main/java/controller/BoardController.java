package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.BoardDao;
import entity.Board;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 10)
public class BoardController extends HttpServlet {

	public static final int LIST_PER_PAGE = 10;		// 한 페이지당 글 목록의 갯수
	public static final int PAGE_PER_SCREEN = 10;	// 한 화면에 표시되는 페이지 갯수
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] uri = req.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		HttpSession session = req.getSession();
		String sessionUid = (String) session.getAttribute("uid");
		session.setAttribute("menu", "board");
		BoardDao bDao = new BoardDao();
		RequestDispatcher rd = null;

		int bid = 0, page = 0;
		String title = null, content = null, files = null;
		Board board = null;

		switch (action) {
		case "list":
			String page_ = req.getParameter("p");
			String field = req.getParameter("f");
			String query = req.getParameter("q");
			
			page = (page_ == null || page_.equals("") ? 1 : Integer.parseInt(page_));
			field = (field == null || field.equals("") ? "title" : field);
			query = (query == null || query.equals("") ? "" : query);
			
			List<Board> list = bDao.listBoard(field, query, page);
			int totalBoardCount = bDao.getBoardCount(field, query);
			int totalPages = (int)Math.ceil(totalBoardCount / (double)LIST_PER_PAGE);
//			int startPage = (int)((page - 1) / PAGE_PER_SCREEN) + 1;  
			int startPage = (int) Math.ceil((page-0.5)/PAGE_PER_SCREEN -1) * PAGE_PER_SCREEN + 1;
			int endPage = Math.min(totalPages, startPage + PAGE_PER_SCREEN - 1);
			List<String> pageList = new ArrayList<String>();
			for (int i = startPage; i <= endPage; i++)
				pageList.add(String.valueOf(i));

			session.setAttribute("currentBoardPage", page);
			req.setAttribute("boardList", list);
			req.setAttribute("field", field);
			req.setAttribute("query", query);
			req.setAttribute("today", LocalDate.now().toString());
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("pageList", pageList);
			
			rd = req.getRequestDispatcher("/WEB-INF/view/board/list.jsp");
			rd.forward(req, resp);			
			break;
			
		case "write":
			if (req.getMethod().equals("GET")) {
				rd = req.getRequestDispatcher("/WEB-INF/view/board/write.jsp");
				rd.forward(req, resp);
			} else {
				title = req.getParameter("title");
				content = req.getParameter("content");
				board = new Board(sessionUid, title, content, files);
				bDao.insertBoard(board);
				resp.sendRedirect("/bbs/board/list?p=1&f=&q=");
			}
			break;
		}

	}

}
