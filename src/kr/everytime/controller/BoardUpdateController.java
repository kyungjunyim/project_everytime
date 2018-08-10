package kr.everytime.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.everytime.service.PostService;

public class BoardUpdateController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String job = request.getParameter("job");

		switch (job) {
		case "postUpdate":
			boolean sessionResult = sessionCheck(request, response);
			if (sessionResult) {
				updatePost(request, response);
			} else {

			}
			break;
		}
	}

	public void updatePost(HttpServletRequest request, HttpServletResponse response) {
		PostService ps = PostService.getInstance();
		String postId = (String) request.getAttribute("postId");
		String memberId = (String) request.getAttribute("memberId");
		String newPostTitle = (String) request.getAttribute("newPostTitle");
		String newPostContent = (String) request.getAttribute("newPostContent");
		boolean newAnonymous = (boolean) request.getAttribute("newAnonymous");
		ps.updatePost(postId, memberId, newPostTitle, newPostContent, newAnonymous);
		BoardSearchController bsc = new BoardSearchController();
		request.setAttribute("job", "postDetail");
		bsc.execute(request, response);

	}

	public boolean sessionCheck(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String generatedId = null;
		boolean result = true;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				generatedId = cookie.getValue();
				break;
			}

			HttpSession session = request.getSession();
			String sessionId = (String) session.getAttribute("generatedId");
			String[] aryGeneratedId = sessionId.split("/");

			if (aryGeneratedId[0].equals(generatedId)) {
				request.setAttribute("memberId", aryGeneratedId[1]);

			} else {
				result = false;
			}
		}
		return result;

	}
}