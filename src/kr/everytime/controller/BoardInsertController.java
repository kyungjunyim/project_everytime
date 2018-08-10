package kr.everytime.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.everytime.service.CommentService;
import kr.everytime.service.PostService;
import kr.everytime.service.ReplyService;

public class BoardInsertController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String job = request.getParameter("job");

		switch (job) {
		case "insertReply":
			addReply(request, response);
			break;
		case "writeComment":
			addComment(request, response);
			break;
		case "writePost":
			addPost(request, response);
			break;
		case "writePostPage":
			openWritePage(request, response);
			break;
		}
	}

	public void addReply(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String generatedId = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				generatedId = cookie.getValue();
				break;
			}
		}

		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("generatedId");
		String[] aryGeneratedId = sessionId.split("/");

		if (aryGeneratedId[0].equals(generatedId)) {
			request.setAttribute("memberId", aryGeneratedId[1]);
			ReplyService rs = ReplyService.getInstance();
			String postId = (String) request.getAttribute("postId");
			String memberId = (String) request.getAttribute("memberId");
			String replyContent = (String) request.getAttribute("replyContent");
			boolean isAnonymous = (boolean) request.getAttribute("isAnonymous");
			rs.addReply(postId, memberId, replyContent, isAnonymous);
			BoardSearchController bsc = new BoardSearchController();
			request.setAttribute("job", "postDetail");
			bsc.execute(request, response);
		}
	}

	public void addComment(HttpServletRequest request, HttpServletResponse response) {
		CommentService commentService = CommentService.getInstance();
		String postId = request.getParameter("postId");
		String replyId = request.getParameter("replyId");
		String commentContent = request.getParameter("commentContent");
		String anonymous = request.getParameter("isAnonymous");
		boolean isAnonymous = false;
		if (anonymous.equals("anonymous")) {
			isAnonymous = true;
		}

		String memberId = null;
		String userGeneratedId = null;

		Cookie[] cookies = (Cookie[]) request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				userGeneratedId = cookie.getValue();
			}
		}

		memberId = userGeneratedId.split("/")[1];

		boolean res = commentService.addComment(postId, replyId, memberId, commentContent, isAnonymous);

		if (res) {
			BoardSearchController sc = new BoardSearchController();
			sc.execute(request, response);
		}
	}

	public void addPost(HttpServletRequest request, HttpServletResponse response) {
		PostService postService = PostService.getInstance();

		String postTitle = request.getParameter("postTitle");
		String postContent = request.getParameter("postContent");
		
		String anonymous = request.getParameter("isAnonymous");

		boolean isAnonymous = false;
		
		if(anonymous != null)
		{
		if (anonymous.equals("1")) {
			isAnonymous = true;
		}
		}

		String memberId = null;
		String userGeneratedId = null;

		Cookie[] cookies = (Cookie[]) request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				userGeneratedId = cookie.getValue();
			}
		}
		memberId = userGeneratedId.split("/")[1];

		boolean res = postService.addPost(postTitle, postContent, memberId, isAnonymous);

		if (res) {
			BoardListController ls = new BoardListController();
			request.setAttribute("job", "postList");
			request.setAttribute("pageNum","1");
			ls.execute(request, response);
		}
	}
	
	public void openWritePage(HttpServletRequest request, HttpServletResponse response){
		try {
			ForwardController.forward(request, response, "writePost.jsp");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
