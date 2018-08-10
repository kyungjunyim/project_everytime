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
import kr.everytime.dto.MemberDTO;

public class CommentViewDAO {
	private static CommentViewDAO commentViewDAO;
	private Map<String, List<CommentViewDTO>> comments;
	static{
		commentViewDAO = new CommentViewDAO();
	}
	private CommentViewDAO(){
		comments = new HashMap<String, List<CommentViewDTO>>();
	}
	
	public static CommentViewDAO getInstance(){return commentViewDAO;}
	
	public String generateCommentId(String replyId)
	{
		if( !nullCheck(new String[]{replyId}) ){return null;}
		Iterator<String> keys = comments.keySet().iterator();
		List<CommentViewDTO> list = null;
		CommentViewDTO comment = null;
		while(keys.hasNext())
		{
			if(keys.next().equals(replyId))
			{
				list = comments.get(replyId);
				break;
			}
		}
		if(list != null) {
			int size = list.size();
			String commentId;
			comment = list.get(size - 1);
			commentId = comment.getCommentId();
			String[] div = commentId.split("-");
			int maxCnt = Integer.parseInt(div[3]) + 1;
			return div[0] + "-" + div[1] + "-" + div[2] + "-" + maxCnt;				
		}
		else {
			return replyId + "-" + "1";
		}
	}
	
	public Object[] getComment(String replyId)
	{
		if( !nullCheck(new String[]{replyId}) ){return null;}
		
		Iterator<String> keys = comments.keySet().iterator();
		while(keys.hasNext())
		{
			if(keys.next().equals(replyId))
			{
				return comments.get(replyId).toArray();
			}
		}
		return null;
	}
	
	public boolean loadCommentView() {
		try {
			Connection conn = ConnectDB.connect();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM comment_view ORDER BY post_date asc");
			ResultSet rs = pstmt.executeQuery();
			
			String postId = null;
			String replyId = null;
			String commentId= null;
			String commentContent = null;
			Timestamp postDate = null;
			String nickName = null;
			String anonymous = null;
			boolean isAnonymous = false;
			
			while(rs.next()) {
				boolean input = false;
				List<CommentViewDTO> list = new ArrayList<CommentViewDTO>();
				
				postId = rs.getString(1);
				replyId = rs.getString(2);
				commentId = rs.getString(3);
				commentContent = rs.getString(4);
				postDate = rs.getTimestamp(5);
				nickName = rs.getString(6);
				anonymous = rs.getString(7);
				
				if(anonymous.equals("1")) {
					isAnonymous = true;
				}
				
				CommentViewDTO dto = new CommentViewDTO(postId, replyId, commentId, commentContent, nickName, postDate, isAnonymous);
				Iterator<String> keys = comments.keySet().iterator();
				while(keys.hasNext()) {
					if(keys.next().equals(replyId)) {
						comments.get(replyId).add(dto);
						input = true;
						break;
					}
				}
				if(input == false) {
					list.add(dto);
					comments.put(replyId, list);
				}
			}
			
			return true;
		}
		catch(SQLException e) {
				e.printStackTrace();
		}
		
		return false;
	}
	
	public String addComment(String postId, String replyId, String memberId, String commentContent, boolean isAnonymous) {
		if(!nullCheck(new String[]{postId, replyId, memberId, commentContent})){
			return null;
		}
		
		String commentId = null; 
		commentId = generateCommentId(replyId);
		Timestamp postDate = Timestamp.valueOf(LocalDateTime.now());
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		String nickName = memberDAO.searchMemberNickName(memberId);
		
		CommentViewDTO dto = new CommentViewDTO(postId, replyId, commentId, commentContent, nickName, postDate, isAnonymous);
		List<CommentViewDTO> list = comments.get(replyId);
		if(list == null) {
			List<CommentViewDTO> tempList = new ArrayList<CommentViewDTO>();
			tempList.add(dto);
			comments.put(replyId, tempList);
		}
		else {
			list.add(dto);
		}
		if(CommentDAO.getInstance().addComment(commentId, replyId, postId, memberId, commentContent, postDate, isAnonymous) == null) {
			return null;
		}
		return commentId;
	}
	
		
	public String deleteComment(String replyId, String commentId, String memberId)
	{
		if( !nullCheck(new String[]{replyId, memberId, commentId}) ){return null;}
			
		String nickName = null;

		MemberDAO members = MemberDAO.getInstance();
		Object[] objs = members.getMembers();
		MemberDTO member = null;
		
		for(int i=0;i<objs.length;i++)
		{
			member = (MemberDTO)objs[i];
			if(member.getMemberId().equals(memberId))
			{
				nickName = member.getNickName();
				break;
			}
		}
		
		MemberDTO inputMember = (MemberDTO)members.searchMember(memberId);
		CommentViewDTO obj=null;
		List<CommentViewDTO> list=null;
		
		if( nickName.equals(inputMember.getNickName()) )
		{
			list = comments.get(replyId);
		}
		if(list != null)
		{
			for(int i=0;i<list.size();i++)
			{
				obj = list.get(i);
				if(obj.getCommentId().equals(commentId))
				{
					if(CommentDAO.getInstance().deleteComment(commentId, memberId) != null)
						comments.remove(replyId);
				}
			}
		}
			

		//nickName = ((MemberDTO)(MemberDAO.getInstance().searchMember(memberId))).getNickName();
		return commentId;
	}
	public String updateComment(String commentId, String memberId, String newCommentContent, boolean newCommentAnonymous)
	{
		if( !nullCheck(new String[]{newCommentContent, memberId, commentId}) ){return null;}
		String nickName = null;

		MemberDAO members = MemberDAO.getInstance();
		Object[] objs = members.getMembers();
		MemberDTO member = null;
		String[] str = commentId.split("-");
		String replyId = str[0]+"-"+str[1]+"-"+str[2];
		
		for(int i=0;i<objs.length;i++)
		{
			member = (MemberDTO)objs[i];
			if(member.getMemberId().equals(memberId))
			{
				nickName = member.getNickName();
				break;
			}
		}
		MemberDTO inputMember = (MemberDTO)members.searchMember(memberId);
		CommentViewDTO obj=null;
		List<CommentViewDTO> list = null;
		if(nickName.equals(inputMember.getNickName()))
		{
			list = comments.get(replyId);
		}
		if(list != null)
		{
			for(int i=0;i<list.size();i++)
			{
				obj = list.get(i);
				if(obj.getCommentId().equals(commentId))
				{
					if(CommentDAO.getInstance().updateComment(commentId, memberId, newCommentContent, newCommentAnonymous) != null)
					{
						obj.setCommentContent(newCommentContent);
						obj.setAnonymous(newCommentAnonymous);
					}
				}
			}
		}
		

	return commentId;
	}
	public String updateCommentContent(String commentId, String memberId, String newCommentContent)
	{
		if( !nullCheck(new String[]{newCommentContent, memberId, commentId}) ){return null;}
		String nickName = "닉네임";

			MemberDAO members = MemberDAO.getInstance();
			Object[] objs = members.getMembers();
			MemberDTO member = null;
			String[] str = commentId.split("-");
			String replyId = str[0]+"-"+str[1]+"-"+str[2];
			
			for(int i=0;i<objs.length;i++)
			{
				member = (MemberDTO)objs[i];
				if(member.getMemberId().equals(memberId))
				{
					nickName = member.getNickName();
					break;
				}
			}
			MemberDTO inputMember = (MemberDTO)members.searchMember(memberId);
			CommentViewDTO obj=null;
			List<CommentViewDTO> list=null;
			
			if(nickName.equals(inputMember.getNickName()))
			{
				list = comments.get(replyId);
			}
			if(list != null)
			{
				for(int i=0;i<list.size();i++)
				{
					obj = list.get(i);
					if(obj.getCommentId().equals(commentId))
					{
						if(CommentDAO.getInstance().updateCommentContent(commentId, memberId, newCommentContent) != null)
						{
							obj.setCommentContent(newCommentContent);
						}
					}
				}
			}
			

		return commentId;
	}
	
	
	public String updateCommentAnonymous(String commentId, String memberId, boolean newCommentAnonymous)
	{
		if( !nullCheck(new String[]{ memberId, commentId}) ){return null;}
		String nickName = "닉네임";

			MemberDAO members = MemberDAO.getInstance();
			Object[] objs = members.getMembers();
			MemberDTO member = null;
			String[] str = commentId.split("-");
			String replyId = str[0]+"-"+str[1]+"-"+str[2];
			
			for(int i=0;i<objs.length;i++)
			{
				member = (MemberDTO)objs[i];
				if(member.getMemberId().equals(memberId))
				{
					nickName = member.getNickName();
					break;
				}
			}
			MemberDTO inputMember = (MemberDTO)members.searchMember(memberId);
			CommentViewDTO obj=null;
			List<CommentViewDTO> list = null;
			
			if(nickName.equals(inputMember.getNickName()))
			{
				list = comments.get(replyId);
			}
			if(list != null)
			{
				for(int i=0;i<list.size();i++)
				{
					obj = list.get(i);
					if(obj.getCommentId().equals(commentId))
					{
						if(CommentDAO.getInstance().updateAnonymous(commentId, memberId, newCommentAnonymous) != null)
						{
							obj.setAnonymous(newCommentAnonymous);
						}
					}
				}
			}
			
		return commentId;
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
