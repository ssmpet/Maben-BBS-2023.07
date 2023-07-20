package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.BoardDao;
import db.ReplyDao;
import entity.Reply;

/**
 * Servlet implementation class ReplyController
 */
@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] uri = req.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		HttpSession session = req.getSession();
		String sessionUid = (String) session.getAttribute("uid");
		session.setAttribute("menu", "board");
		BoardDao bDao = new BoardDao();
		ReplyDao rDao = new ReplyDao();
		RequestDispatcher rd = null;

		switch(action) {
		case "write":
			int bid = Integer.parseInt(req.getParameter("bid"));
			String uid = req.getParameter("uid");
			String comment = req.getParameter("comment");

//			System.out.println("uid = " + uid);
//			System.out.println("session= uid " + sessionUid);

			int isMine = uid.equals(sessionUid) ? 1 : 0;
			Reply reply = new Reply(comment, isMine, sessionUid, bid);
			rDao.insertReply(reply);
			bDao.increaseReplyCount(bid);
			resp.sendRedirect("/bbs/board/detail?bid=" + bid + "&uid=" + uid + "&option=DNI");
			break;
		}
	}


}
