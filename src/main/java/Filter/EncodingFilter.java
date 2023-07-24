package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class EncodingFilter
 */
//@WebFilter("/*")
@WebFilter({"/aside/*", "/board/*", "/reply/*", "/file/*", "/user/*", "/city/*"})
public class EncodingFilter extends HttpFilter implements Filter {
 	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 		
 		HttpServletRequest httpRequest = (HttpServletRequest) request;
 		HttpServletResponse httpResponse = (HttpServletResponse) response;
 		
 		request.setCharacterEncoding("utf-8"); 		
 		
		chain.doFilter(request, response);
	}

}
