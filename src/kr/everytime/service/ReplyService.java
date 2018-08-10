package kr.everytime.service;

import java.util.ArrayList;
import java.util.List;

import kr.everytime.dao.ReplyViewDAO;
import kr.everytime.dto.ReplyViewDTO;

public class ReplyService {
	private static ReplyService replyService;
	private ReplyViewDAO replyViewDAO;

	static {
		replyService = new ReplyService();
	}

	private ReplyService() {
		replyViewDAO = ReplyViewDAO.getInstance();
	}

	public static ReplyService getInstance() {
		return replyService;
	}

	public boolean addReply(String postId, String memberId, String replyContent, boolean isAnonymous) {

		ReplyViewDAO rvd = ReplyViewDAO.getInstance();

		String returnId = rvd.addReply(postId, memberId, replyContent, isAnonymous);
		if (returnId != null) {
			return true;
		} else {
			return false;

		}
	}

	public boolean deleteReply(String postId, String replyId, String memberId) {
		ReplyViewDAO rvd = ReplyViewDAO.getInstance();
		String returnId = rvd.deleteReply(postId, replyId, memberId);
		if (returnId != null) {
			return true;
		} else {
			return false;
		}
	}

	public List<String[]> getReplies(String postId) {

		Object[] objs = replyViewDAO.getReplies(postId);
		if (objs == null) {
			return null;
		}
		List<String[]> replies = new ArrayList<String[]>();

		for (int i = 0; i < objs.length; i++) {
			String[] str = new String[7];

			str[0] = ((ReplyViewDTO) objs[i]).getPostId();
			str[1] = ((ReplyViewDTO) objs[i]).getReplyId();
			str[2] = ((ReplyViewDTO) objs[i]).getNickName();
			str[3] = ((ReplyViewDTO) objs[i]).getReplyContent();
			str[4] = "" + ((ReplyViewDTO) objs[i]).getPostDate();
			str[5] = "" + ((ReplyViewDTO) objs[i]).isAnonymous();

			replies.add(str);
		}

		return replies;
	}

}
