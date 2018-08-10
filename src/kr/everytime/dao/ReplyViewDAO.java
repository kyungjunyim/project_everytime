package kr.everytime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.teamwith.db.ConnectDB;

import kr.everytime.dto.CommentViewDTO;
import kr.everytime.dto.ReplyViewDTO;

public class ReplyViewDAO {
	private static ReplyViewDAO replyViewDAO;
	private Map<String, List<ReplyViewDTO>> replies;
	
	static {
		replyViewDAO = new ReplyViewDAO();
	}
	
	private ReplyViewDAO() {
		replies = new HashMap<String, List<ReplyViewDTO>>();
	}
	
	public static ReplyViewDAO getInstance() {
		return replyViewDAO;
	}
	
	public String generateReplyId(String postId) {
		Iterator<String> keys = replies.keySet().iterator();
		List<ReplyViewDTO> list = null;
		ReplyViewDTO reply = null;
		while(keys.hasNext())
		{
			if(keys.next().equals(postId))
			{
				list = replies.get(postId);
				break;
			}
		}
		if(list != null)
		{
			int size = list.size();
			String replyId;
			reply = list.get(size-1);
			replyId = reply.getReplyId();
			String[] div = replyId.split("-");
			int maxCnt = Integer.parseInt(div[2])+1;
			return div[0]+"-"+div[1]+"-"+maxCnt;
		}
		else {
			return postId + "-" + "1";
		}
	}
	
	public Object[] getReplies(String postId) {
		List<ReplyViewDTO> replies = this.replies.get(postId);
		if(replies != null) 
			return replies.toArray();
		else 
			return null;
	}
	
	public boolean loadReplyView() {
		Connection con = ConnectDB.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(con != null) {
			try {
				pstmt = con.prepareStatement("select * from reply_view");
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					String postId = rs.getString("post_id");
					String replyId = rs.getString("reply_id");
					String nickName = rs.getString("nick_name");
					String replyContent = rs.getString("reply_content");
					Timestamp postDate = rs.getTimestamp("post_date");
					String anonymous = rs.getString("is_anonymous");
					boolean isAnonymous = false;
					if(anonymous.equals("1")) {
						isAnonymous = true;
					}
					ReplyViewDTO viewdto = new ReplyViewDTO(postId, replyId, nickName, replyContent, postDate, isAnonymous);
					
					List<ReplyViewDTO> viewdtoList = replies.get(postId);
					if(viewdtoList == null) {
						viewdtoList = new ArrayList<ReplyViewDTO>();
					}
					viewdtoList.add(viewdto);
					replies.put(postId, viewdtoList);
					
					
				}
				
			}
			catch(SQLException e) {
				System.out.println("SQL ¿À·ù");
			}
			ConnectDB.close(con, pstmt, rs);
		}
		return true;
	}
	
	public String addReply(String postId, String memberId, String replyContent, boolean isAnonymous) {
		String replyId = generateReplyId(postId);
		Timestamp postDate = Timestamp.valueOf(LocalDateTime.now());
		
		if(!nullCheck(new String[]{postId, memberId, replyContent, replyId})) return null;
		
		///DTO
		String nickName = MemberDAO.getInstance().searchMemberNickName(memberId);
		ReplyViewDTO viewdto = new ReplyViewDTO(postId, replyId, nickName, replyContent, postDate, isAnonymous);
		List<ReplyViewDTO> viewdtoList = replies.get(postId);
		viewdtoList.add(viewdto);
		replies.put(postId, viewdtoList);
		
		ReplyDAO replyDao = ReplyDAO.getInstance();
		replyDao.addReply(replyId, postId, memberId, replyContent, postDate, isAnonymous);
		
		return memberId;
		
	}
	
	public String deleteReply(String postId, String replyId, String memberId) {
		if(!nullCheck(new String[]{postId, replyId, memberId})) return null;
		
		String nickName = MemberDAO.getInstance().searchMemberNickName(memberId);
		ReplyDAO replyDao = ReplyDAO.getInstance();
		
		List<ReplyViewDTO> viewdtoList = replies.get(postId);
		for(ReplyViewDTO viewdto: viewdtoList) {
			if(viewdto.getNickName().equals(nickName)) {
				viewdtoList.remove(viewdto);
				
				CommentViewDAO commentViewDAO = CommentViewDAO.getInstance();
				Object[] comments = commentViewDAO.getComment(replyId);
				for(int i = 0; i < comments.length; i++) {
					String commentId = ((CommentViewDTO)comments[i]).getCommentId();
					commentViewDAO.deleteComment(replyId, commentId, memberId);
				}				
				replyDao.deleteReply(replyId, memberId);
			}
		}
		return memberId;
	}
	
	public String updateReplyContent(String postId, String replyId, String memberId, String newReplyContent) {
		if(!nullCheck(new String[]{postId, replyId, memberId, newReplyContent})) return null;
		
		String nickName = MemberDAO.getInstance().searchMemberNickName(memberId);
		ReplyDAO replyDao = ReplyDAO.getInstance();
		
		List<ReplyViewDTO> viewdtoList = replies.get(postId);
		
		for(ReplyViewDTO viewdto: viewdtoList) {
			if(viewdto.getNickName().equals(nickName)) {
				int index = viewdtoList.indexOf(viewdto);
				viewdto.setReplyContent(newReplyContent);
				viewdtoList.set(index, viewdto);
				
				replyDao.updateReplyContent(replyId, memberId, newReplyContent);
			}
		}
		replies.put(postId, viewdtoList);
		return memberId;
	}
	
	public String updateReplyAnonymous(String postId, String replyId, String memberId, boolean newReplyAnonymous) {
		if(!nullCheck(new String[]{postId, replyId, memberId})) return null;
		
		String nickName = MemberDAO.getInstance().searchMemberNickName(memberId);
		ReplyDAO replyDao = ReplyDAO.getInstance();
		
		List<ReplyViewDTO> viewdtoList = replies.get(postId);
		
		for(ReplyViewDTO viewdto: viewdtoList) {
			if(viewdto.getNickName().equals(nickName)) {
				int index = viewdtoList.indexOf(viewdto);
				viewdto.setAnonymous(newReplyAnonymous);
				viewdtoList.set(index, viewdto);
				
				replyDao.updateReplyAnonymous(replyId, memberId, newReplyAnonymous);
			}
		}
		replies.put(postId, viewdtoList);
		return memberId;
	}
	
	public String updateReply(String postId, String replyId, String memberId, String newReplyContent, boolean newReplyAnonymous) {
		if(!nullCheck(new String[]{postId, replyId, memberId, newReplyContent})) return null;
		
		String nickName = MemberDAO.getInstance().searchMemberNickName(memberId);
		ReplyDAO replyDao = ReplyDAO.getInstance();
		
		List<ReplyViewDTO> viewdtoList = replies.get(postId);
		
		for(ReplyViewDTO viewdto: viewdtoList) {
			if(viewdto.getNickName().equals(nickName)) {
				int index = viewdtoList.indexOf(viewdto);
				viewdto.setReplyContent(newReplyContent);
				viewdto.setAnonymous(newReplyAnonymous);
				viewdtoList.set(index, viewdto);
				
				replyDao.updateReply(replyId, memberId, newReplyContent, newReplyAnonymous);
			}
		}
		replies.put(postId, viewdtoList);
		return memberId;
	}
	
	public boolean nullCheck(String[] str)
	{
		if(str == null){return false;}
		for(int i=0;i<str.length;i++)
		{
			if(str[i] == null )
			{
				return false;
			}
		}
		return true;
	}
	
}
