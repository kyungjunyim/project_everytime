package kr.everytime.service;

import java.util.ArrayList;
import java.util.List;

import kr.everytime.dao.CommentViewDAO;
import kr.everytime.dto.CommentViewDTO;

public class CommentService {
	private static CommentService commentService;
	private CommentViewDAO commentViewDAO;

	static {
		commentService = new CommentService();
	}

	private CommentService() {
		commentViewDAO = CommentViewDAO.getInstance();
	}

	public static CommentService getInstance() {
		return commentService;
	}

	public boolean addComment(String postId, String replyId, String memberId, String commentContent,
			boolean isAnonymous) {
		String res = commentViewDAO.addComment(postId, replyId, memberId, commentContent, isAnonymous);
		if (res == null)
			return false;
		return true;
	}

	public boolean deleteComment(String postId, String replyId, String commentId, String memberId) {
		String res = commentViewDAO.deleteComment(replyId, commentId, memberId);
		if (res == null)
			return false;
		return true;
	}

	public List<String[]> getComments(String replyId) {
		Object[] objs = commentViewDAO.getComment(replyId);
		List<String[]> comments = new ArrayList<String[]>();
		if (objs == null) {
			return null;
		}
		for (int i = 0; i < objs.length; i++) {
			String[] str = new String[7];
			str[0] = ((CommentViewDTO) objs[i]).getPostId();
			str[1] = ((CommentViewDTO) objs[i]).getReplyId();
			str[2] = ((CommentViewDTO) objs[i]).getCommentId();
			str[3] = ((CommentViewDTO) objs[i]).getCommentContent();
			str[4] = "" + ((CommentViewDTO) objs[i]).getPostDate();
			str[5] = ((CommentViewDTO) objs[i]).getNickName();
			str[6] = "" + ((CommentViewDTO) objs[i]).isAnonymous();
			comments.add(str);
		}
		return comments;
	}
}
