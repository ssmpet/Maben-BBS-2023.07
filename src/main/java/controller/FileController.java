package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.UserDao;

/**
 * Servlet implementation class FileController
 */
@WebServlet("/file/*")
public class FileController extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] uri = req.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		HttpSession session = req.getSession();
		
		String file = null, path = null;
		OutputStream os = null;
		File f = null;
		FileInputStream fis = null;
		byte[] buffer = new byte[1024*8];
		
		switch (action) {
		case "download":
			file = req.getParameter("file");
			path = BoardController.UPLOAD_PATH + file;
			
			os = resp.getOutputStream();
			resp.setContentType("text/html; charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setHeader("Content-disposition", "attachment; fileName=" + URLEncoder.encode(file, "utf-8"));
			
			f = new File(path);
			fis = new FileInputStream(f);
			while(true) {
				int count = fis.read(buffer);
				if (count == -1) break;
				os.write(buffer, 0, count);
			}
			fis.close();
			os.close();
			break;
			
		case "profile":
			file = req.getParameter("file");
			path = UserController.PROFILE_PATH + file;
			os = resp.getOutputStream();
			resp.setContentType("text/html; charset=utf-8");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setHeader("Content-disposition", "attachment; fileName=" + URLEncoder.encode(file, "utf-8"));
			
			f = new File(path);
			fis = new FileInputStream(f);
			while(true) {
				int count = fis.read(buffer);
				if (count == -1) break;
				os.write(buffer, 0, count);
			}
			fis.close();
			os.close();
			break;
		}
		
	}

}
