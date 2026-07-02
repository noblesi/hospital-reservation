package com.hospital.admin.board.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.auth.dto.AdminDTO;
import com.hospital.admin.board.AdminBoardService;
import com.hospital.common.dto.BoardPostDTO;
import com.hospital.user.board.dto.BoardSearchDTO;

public class AdminBoardSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final AdminBoardService adminBoardService = new AdminBoardService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDTO loginAdmin = (AdminDTO) request.getSession().getAttribute("loginAdmin");
		if (loginAdmin == null || isBlank(loginAdmin.getAdminId())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 로그인 후 이용해 주세요.");
			return;
		}

		BoardPostDTO boardPost = createBoardPost(request, loginAdmin.getAdminId());

		try {
			adminBoardService.saveBoardPost(boardPost);
			request.getSession().setAttribute("message", "게시글이 저장되었습니다.");
			response.sendRedirect(request.getContextPath() + getListUrl(boardPost.getCategory()));
		} catch (SQLException e) {
			throw new ServletException("게시글을 저장하지 못했습니다.", e);
		}
	}

	private BoardPostDTO createBoardPost(HttpServletRequest request, String adminId) {
		BoardPostDTO boardPost = new BoardPostDTO();
		boardPost.setPostId(parseInt(request.getParameter("postId"), 0));
		boardPost.setCategory(request.getParameter("category"));
		boardPost.setTitle(request.getParameter("title"));
		boardPost.setContent(request.getParameter("content"));
		boardPost.setWriterId(adminId);
		return boardPost;
	}

	private String getListUrl(String category) {
		return BoardSearchDTO.CATEGORY_FAQ.equals(category) ? "/admin/faq/list.do" : "/admin/notice/list.do";
	}

	private int parseInt(String value, int defaultValue) {
		try {
			return value == null ? defaultValue : Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
