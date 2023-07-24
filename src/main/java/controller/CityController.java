package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.Region;

import com.google.protobuf.DescriptorProtos.MethodOptions.IdempotencyLevel;

import db.BoardDao;
import db.CityDao;
import db.ReplyDao;
import entity.Board;
import entity.City;
import utility.JsonUtil;

/**
 * Servlet implementation class CityController
 */
@WebServlet("/city/*")
public class CityController extends HttpServlet {
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] uri = req.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		CityDao cDao = new CityDao();
		

		RequestDispatcher rd = null;
		String cname = null, countryCode = null, district = null;
		int cid = 0, population = 0;
		City city = null;

		switch (action) {
		case "citylist":
			rd = req.getRequestDispatcher("/WEB-INF/view/city/citylist.jsp");
			rd.forward(req, resp);		
			break;
		case "citylist10":
			List<City> list = new ArrayList<City>();
			list = cDao.getCityList();
			req.setAttribute("cityList", list);
			rd = req.getRequestDispatcher("/WEB-INF/view/city/citylisttop10.jsp");
			rd.forward(req, resp);	
			break;
			
		case "citysearch":
			if (req.getMethod().equals("GET")) {
				rd = req.getRequestDispatcher("/WEB-INF/view/city/citysearch.jsp");
				rd.forward(req, resp);	
			} else 	{
				cname = req.getParameter("cname");
				city = cDao.getCity(cname);
				req.setAttribute("cname", cname);
				req.setAttribute("city", city);
			
				rd = req.getRequestDispatcher("/WEB-INF/view/city/cityinfo.jsp");
				rd.forward(req, resp);	
			}
			break;
			
		case "cityinfo":
			cname = req.getParameter("cname");
			city = cDao.getCity(cname);
			req.setAttribute("cname", cname);
			req.setAttribute("city", city);

			rd = req.getRequestDispatcher("/WEB-INF/view/city/cityinfo.jsp");
			rd.forward(req, resp);	

			break;
			
		case "cityinsert":
			if (req.getMethod().equals("GET")) {
				rd = req.getRequestDispatcher("/WEB-INF/view/city/cityinsert.jsp");
				rd.forward(req, resp);	
			} else {
				cname = req.getParameter("cname");
				countryCode = req.getParameter("countryCode");
				district = req.getParameter("district");
				population = Integer.parseInt(req.getParameter("population"));
				city = new City(cname, countryCode, district, population);
				cDao.insertCity(city);
				resp.sendRedirect("/bbs/city/citylist");
			}
			break;
			
		case "cityupdate":
			if (req.getMethod().equals("GET")) {
				cid = Integer.parseInt(req.getParameter("cid"));
				city = cDao.getCity(cid);
				req.setAttribute("city", city);
				rd = req.getRequestDispatcher("/WEB-INF/view/city/cityupdate.jsp");
				rd.forward(req, resp);	
			} else {
				cid = Integer.parseInt(req.getParameter("cid"));
				cname = req.getParameter("cname");
				countryCode = req.getParameter("countryCode");
				district = req.getParameter("district");
				population = Integer.parseInt(req.getParameter("population"));
				city = new City(cid, cname, countryCode, district, population);
				cDao.updateCity(city);
				
				req.setAttribute("cname", cname);
				req.setAttribute("city", city);

				rd = req.getRequestDispatcher("/WEB-INF/view/city/cityinfo.jsp");
				rd.forward(req, resp);	
			}
			break;
			
		case "citydelete":
			cid = Integer.parseInt(req.getParameter("cid"));
			cname = req.getParameter("cname");
			req.setAttribute("cid", cid);
			req.setAttribute("cname", cname);
			rd = req.getRequestDispatcher("/WEB-INF/view/city/citydelete.jsp");
			rd.forward(req, resp);	
 
			break;
		case "deleteConfirm":
			cid = Integer.parseInt(req.getParameter("cid"));
			cDao.deleteCity(cid);
			resp.sendRedirect("/bbs/city/citylist");			
			break;
		default:
			break;
		}
	}

	

}
