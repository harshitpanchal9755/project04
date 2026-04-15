package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.util.ServletUtility;

@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontControler implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Fctl Do Filter");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			ServletUtility.setErrorMessage("Your Sesssion Has Been Expired !.........Please Login Again !!", request);

			String str = request.getRequestURI();

			request.setAttribute("uri", str);
			System.out.println("URI" + str);
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			return;

		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void destroy() {
		
	}
}
