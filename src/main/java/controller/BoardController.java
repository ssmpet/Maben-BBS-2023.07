package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import javax.servlet.http.Part;

import db.BoardDao;
import db.ReplyDao;
import entity.Board;
import entity.Reply;
import utility.JsonUtil;

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
	public static final String UPLOAD_PATH = "c:/Temp/upload/";
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] uri = req.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		HttpSession session = req.getSession();
		String sessionUid = (String) session.getAttribute("uid");
		session.setAttribute("menu", "board");
		BoardDao bDao = new BoardDao();
		ReplyDao rDao = new ReplyDao();
		JsonUtil ju = new JsonUtil();
		RequestDispatcher rd = null;

		int bid = 0, page = 0;
		String title = null, content = null, files = null, uid = null;
		String field = null, query = null;
		Board board = null;
		List<String> fileList = null;


		switch (action) {
		case "list":
			String page_ = req.getParameter("p");
			field = req.getParameter("f");
			query = req.getParameter("q");
			
			page = (page_ == null || page_.equals("") ? 1 : Integer.parseInt(page_));
			field = (field == null || field.equals("") ? "title" : field);
			query = (query == null || query.equals("") ? "" : query);
			
			
			int totalBoardCount = bDao.getBoardCount(field, query);
			int totalPages = (int)Math.ceil(totalBoardCount / (double)LIST_PER_PAGE);
			if (page > totalPages) page = totalPages;
			
			List<Board> list = bDao.listBoard(field, query, page);
//			int startPage = (int)((page - 1) / PAGE_PER_SCREEN) + 1;  
			int startPage = (int) Math.ceil((page-0.5)/PAGE_PER_SCREEN -1) * PAGE_PER_SCREEN + 1;
			int endPage = Math.min(totalPages, startPage + PAGE_PER_SCREEN - 1);
			List<String> pageList = new ArrayList<String>();
			for (int i = startPage; i <= endPage; i++)
				pageList.add(String.valueOf(i));

			session.setAttribute("currentBoardPage", page);
			req.setAttribute("field", field);
			req.setAttribute("query", query);
			req.setAttribute("boardList", list);
			req.setAttribute("today", LocalDate.now().toString());
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("startPage", startPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("pageList", pageList);
			
			rd = req.getRequestDispatcher("/WEB-INF/view/board/list.jsp");
			rd.forward(req, resp);			
			break;
			
		case "detail":
			bid = Integer.parseInt(req.getParameter("bid"));
			uid = req.getParameter("uid");
			field = req.getParameter("f");
			query = req.getParameter("q");
			String option = req.getParameter("option");
			
//			System.out.println("detail uid = " + uid);
			
			// 본인이 조회한 경우 및 댓글 작성후에는 조회수를 증가시키지 않음
			if ( !uid.equals(sessionUid) && !(option==null || option.equals("")) ) // option=DNI
				bDao.increaseViewCount(bid);

			board = bDao.getBoard(bid);

			String jsonFiles = board.getFiles();
			if (!(jsonFiles == null || jsonFiles.equals(""))) {
				fileList = ju.jsonToList(board.getFiles());
				req.setAttribute("fileList", fileList);
			}
			
			List<Reply> replyList = rDao.getReplyList(bid);
			
			req.setAttribute("board", board);
			req.setAttribute("replyList", replyList);
			req.setAttribute("field", field);
			req.setAttribute("query", query);
//			rd = req.getRequestDispatcher("/WEB-INF/view/board/detail.jsp");
			rd = req.getRequestDispatcher("/WEB-INF/view/board/detailEditor.jsp");
			rd.forward(req, resp);
			break;
			
		case "write":
			if (req.getMethod().equals("GET")) {
//				rd = req.getRequestDispatcher("/WEB-INF/view/board/write.jsp");
				rd = req.getRequestDispatcher("/WEB-INF/view/board/writeEditor.jsp");
				rd.forward(req, resp);
			} else {
				title = req.getParameter("title");
				content = req.getParameter("content");
				
				List<Part> fileParts = (List<Part>)req.getParts();
				fileList = new ArrayList<String>();
				
//				System.out.println(fileParts.size());
				
				for (Part part: fileParts) {
					String filename = part.getSubmittedFileName();
					if (filename == null || filename.equals("")) continue;
					
//					System.out.println(filename);
					
					part.write(UPLOAD_PATH + filename);
					fileList.add(filename);
				}
//				fileList.forEach(x -> System.out.println(x));

				// JSON 형식으로 바꾸기
				files = ju.listToJson(fileList);
				
				board = new Board(sessionUid, title, content, files);
				bDao.insertBoard(board);
				resp.sendRedirect("/bbs/board/list?p=1&f=&q=");
			}
			break;
			
		case "update":
			if (req.getMethod().equals("GET")) {
				bid = Integer.parseInt(req.getParameter("bid"));
				board = bDao.getBoard(bid);
				board.setTitle(board.getTitle().replace("\"", "&quot;"));
				fileList = ju.jsonToList(board.getFiles());
				
				req.setAttribute("board", board);
//				req.setAttribute("fileList", fileList);
				session.setAttribute("fileList", fileList);
				
//				rd = req.getRequestDispatcher("/WEB-INF/view/board/update.jsp");
				rd = req.getRequestDispatcher("/WEB-INF/view/board/updateEditor.jsp");
				rd.forward(req, resp);
			} else {
				bid = Integer.parseInt(req.getParameter("bid"));
				title = req.getParameter("title");
				content = req.getParameter("content");
				fileList = (List<String>)session.getAttribute("fileList");
				
				if (fileList != null && fileList.size() > 0) {
					
					String[] delFiles = req.getParameterValues("delFile");
					if ( delFiles != null && delFiles.length > 0) {
						for(String delFile: delFiles) {
							fileList.remove(delFile);					// fileList에서 삭제
							File df = new File(UPLOAD_PATH + delFile);	// 실제 파일 삭제
							df.delete();
						}
					}
				} else {
					fileList = new ArrayList<String>();
				}
				
				List<Part> fileParts = (List<Part>) req.getParts();
				for (Part part: fileParts) {
					String filename = part.getSubmittedFileName();
					if (filename == null || filename.equals("")) continue;

					part.write(UPLOAD_PATH + filename);
					fileList.add(filename);
				}

				files = ju.listToJson(fileList);
				board = new Board(bid, title, content, files);
				bDao.updateBoard(board);
				session.setAttribute("currentBoardPage", "1");
				resp.sendRedirect("/bbs/board/detail?bid=" + bid + "&uid=" + sessionUid);
			}
			break;
			
		case "delete":
			
			bid = Integer.parseInt(req.getParameter("bid"));
			req.setAttribute("field", req.getParameter("f"));
			req.setAttribute("query", req.getParameter("q"));
			
			rd = req.getRequestDispatcher("/WEB-INF/view/board/delete.jsp?bid=" + bid);
			rd.forward(req, resp);
			break;
			
		case "deleteConfirm":
			
			bid = Integer.parseInt(req.getParameter("bid"));
			bDao.deleteBoard(bid);
			
			req.setAttribute("p", session.getAttribute("currentBoardPage"));
			field = req.getParameter("f");
			query = URLEncoder.encode(req.getParameter("q"), "utf-8");

			resp.sendRedirect("/bbs/board/list?p="+ session.getAttribute("currentBoardPage") + "&f=" + field + "&q=" + query);
//			rd = req.getRequestDispatcher("/WEB-INF/view/board/list.jsp?p=" + session.getAttribute("currentBoardPage"));
//			rd.forward(req, resp);
			break;
			
//		case "testBoard":
//			uid = sessionUid;
//			title = "게시판 제목";
//			content = "게시판 내용";
//			for (int i=0; i<200; i++) {
//				board = new Board(uid, title + i, content + i, null);
//				bDao.insertBoard(board);
//			}
//			resp.sendRedirect("/bbs/board/list?p=1&f=&q=");
//			break;

		default:
			rd = req.getRequestDispatcher("/WEB-INF/view/error/error500.jsp");
			rd.forward(req, resp);
			break;
		}

	}

}
 