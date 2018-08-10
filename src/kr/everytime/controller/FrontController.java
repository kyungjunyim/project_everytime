package kr.everytime.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.everytime.dao.CommentViewDAO;
import kr.everytime.dao.MemberDAO;
import kr.everytime.dao.PostViewDAO;
import kr.everytime.dao.ReplyViewDAO;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Controller> urlList = null;
	public void init() {
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.loadMember();
		
		PostViewDAO postViewDAO = PostViewDAO.getInstance();
		postViewDAO.loadPostView();
		
		ReplyViewDAO replyViewDAO = ReplyViewDAO.getInstance();
		replyViewDAO.loadReplyView();
		
		CommentViewDAO commentViewDAO = CommentViewDAO.getInstance();
		commentViewDAO.loadCommentView();
		
		urlList = new HashMap<String, Controller>();
		urlList.put("/member.do", new MemberController());
		urlList.put("/boardInsert.do", new BoardInsertController());
		urlList.put("/boardUpdate.do", new BoardUpdateController());
		urlList.put("/boardDelete.do", new BoardDeleteController());
		urlList.put("/boardSearch.do", new BoardSearchController());
		urlList.put("/boardList.do", new BoardListController());
	}
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		Controller subController = urlList.get(path);
		subController.execute(request, response);
	}
}
