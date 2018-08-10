package kr.everytime.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.everytime.service.CommentService;
import kr.everytime.service.PostService;
import kr.everytime.service.ReplyService;

public class BoardSearchController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String job = request.getParameter("job");
		PostService ps = PostService.getInstance();

		switch (job) {
		case "postDetail":
			postDetail(request, response, ps);
			break;
		case "search":
			search(request, response);
			break;
		}
	}

	public void postDetail(HttpServletRequest request, HttpServletResponse response, PostService ps) {
		String index = request.getParameter("index");
		String postId = request.getParameter("postId" + index);
		ReplyService rs = ReplyService.getInstance();
		CommentService cs = CommentService.getInstance();
		List<List<String[]>> list = new ArrayList<List<String[]>>();

		String[] postInfo = ps.getPostDetail(postId);
		if (postInfo != null) {

			List<String[]> replies = rs.getReplies(postId);
			if (replies != null) {
				String replyId = null;
				List<String[]> comments = new ArrayList<String[]>();
				if (comments != null) {
					for (int i = 0; i < replies.size(); i++) {
						replyId = replies.get(i)[1];
						comments = cs.getComments(replyId);
						list.add(comments);
					}

					request.setAttribute("comments", list);

				}
				request.setAttribute("replies", replies);
			}
			request.setAttribute("postInfo", postInfo);
			try {
				ForwardController.forward(request, response, "postDetail.jsp");
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void search(HttpServletRequest request, HttpServletResponse response) {
		PostService postService = PostService.getInstance();
		String searchCategory = request.getParameter("searchCategory");
		String keyword = request.getParameter("searchPost");
		List<String[]> searchList = null;
		BoardListController lc = new BoardListController();

		switch (searchCategory) {
		case "total":
			searchList = postService.searchPost(keyword);
			break;
		case "title":
			searchList = postService.searchPostTitle(keyword);
			break;
		case "content":
			searchList = postService.searchPostContent(keyword);
			break;
		}
		request.setAttribute("searchList", searchList);
		request.setAttribute("job", "postList");
		lc.execute(request, response);

	}
}
