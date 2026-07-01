package com.hospital.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminAuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 초기화할 리소스가 없다.
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (isPublicAdminRequest(httpRequest) || hasAdminSession(httpRequest)) {
			chain.doFilter(request, response);
			return;
		}

		if (isAjaxRequest(httpRequest)) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "관리자 로그인 후 이용해 주세요.");
			return;
		}

		HttpSession session = httpRequest.getSession();
		session.setAttribute("adminLoginMessage", "관리자 로그인 후 이용해 주세요.");
		httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/login.do");
	}

	@Override
	public void destroy() {
		// 해제할 리소스가 없다.
	}

	private boolean hasAdminSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute("loginAdmin") != null;
	}

	private boolean isPublicAdminRequest(HttpServletRequest request) {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		return "/admin/login.do".equals(path)
				|| "/admin/login/process.do".equals(path)
				|| "/admin/logout.do".equals(path);
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
