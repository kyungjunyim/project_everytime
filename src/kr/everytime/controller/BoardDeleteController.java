package kr.everytime.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.everytime.service.CommentService;
import kr.everytime.service.ReplyService;

public class BoardDeleteController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String job = request.getParameter("job");
		switch (job) {
		case "replyDelete":
			deleteReply(request, response);
			break;
		case "commentDelete":
			deleteComment(request, response);
			break;
		}
	}

	public void deleteReply(HttpServletRequest request, HttpServletResponse response) {
		ReplyService rs = ReplyService.getInstance();
		String memberId = (String) request.getAttribute("memberId");
		String postId = (String) request.getAttribute("postId");
		String replyId = (String) request.getAttribute("replyId");
		rs.deleteReply(postId, replyId, memberId);
		BoardSearchController bsc = new BoardSearchController();
		request.setAttribute("job", "postDetail");
		bsc.execute(request, response);
	}

	public void deleteComment(HttpServletRequest request, HttpServletResponse response) {
		CommentService commentService = CommentService.getInstance();

		String postId = request.getParameter("postId");
		String replyId = request.getParameter("replyId");
		String commentId = request.getParameter("commentId");
		String commentMemberId = request.getParameter("commentMemberId");

		boolean res = commentService.deleteComment(postId, replyId, commentId, commentMemberId);

		if (res) {
			BoardSearchController sc = new BoardSearchController();
			sc.execute(request, response);
		}
	}
}
