package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
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

import db.UserDao;

/**
 * Servlet implementation class FileController
 */
@WebServlet("/file/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
				maxFileSize = 1024 * 1024 * 10, // 10 MB
				maxRequestSize = 1024 * 1024 * 10)
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
			
		case "imageUpload":
			String callback = req.getParameter("CKEditorFuncNum");	// 1

			String error = "";
			String url = null;
			
			List<Part> fileParts = (List<Part>)req.getParts();

			for (Part part: fileParts) {
				String filename = part.getSubmittedFileName();
				if (filename == null || filename.equals("")) continue;
				
				String now = LocalDateTime.now().toString().substring(0, 22).replaceAll("[-T:.]", "");
				int idx = filename.lastIndexOf(".");
				filename = now + filename.substring(idx); 			// 고유한 파일 이름으로 변경
				
				part.write(BoardController.UPLOAD_PATH + filename);
				url = "/bbs/file/download?file=" + filename;
			}
			String ajaxResponse = "<script>"
					+ "		window.parent.CKEDITOR.tools.callFunction("
					+ 		callback + ", '" + url +"', '" + error + "'"
					+ "		);"
					+ "	</script>";
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(ajaxResponse);
			break;
		}
		
	}

}
