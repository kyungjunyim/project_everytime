package kr.everytime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.teamwith.db.ConnectDB;

import kr.everytime.dto.CommentViewDTO;
import kr.everytime.dto.PostViewDTO;
import kr.everytime.dto.ReplyViewDTO;

public class PostViewDAO {
	private static PostViewDAO postViewDAO;
	private List<PostViewDTO> posts;
	
	static {
		postViewDAO = new PostViewDAO();
	}
	
	private PostViewDAO() {
		posts = new ArrayList<PostViewDTO>();
	}
	
	public static PostViewDAO getInstance() {
		return postViewDAO;
	}
	
	private String generatePostId() {
		if(posts.size() >= 1) {
			PostViewDTO lastPostView = posts.get(0);
			String lastPostId = lastPostView.getPostId();
			int lastPostIdCnt = Integer.parseInt(lastPostId.split("-")[1]);
			
			return "post-" + ++lastPostIdCnt;
		}
		else {
			return "post-1";
		}
	}
	
	public boolean loadPostView() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		con = ConnectDB.connect();
		
		try {
			pstmt = con.prepareStatement("select * from post_view ORDER BY post_date asc");
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String postId = res.getString(1);
				String postTitle = res.getString(2);
				Timestamp postDate = res.getTimestamp(3);
				String postContent = res.getString(4);
				String nickName = res.getString(5);
				int replyCnt = res.getInt(6);
				boolean isAnonymous = false;
				if(res.getString(7).equals("1")) {
					isAnonymous = true;
				}
				PostViewDTO postViewDTO = new PostViewDTO(postId, postTitle, postDate, postContent, nickName, replyCnt, isAnonymous);
				posts.add(0, postViewDTO);
			}
			
			ConnectDB.close(con, pstmt, res);
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}
	
	public Object[] getPosts() {
		return posts.toArray();
	}
	
	public String addPost(String postTitle, String postContent, String memberId, boolean isAnonymous) {
		if(nullCheck(new String[]{postTitle, postContent, memberId})) {
			String postId = generatePostId();
			Timestamp postDate = Timestamp.valueOf(LocalDateTime.now());

			MemberDAO memberDAO = MemberDAO.getInstance();
			String nickName = memberDAO.searchMemberNickName(memberId);
			
			PostViewDTO postViewDTO = new PostViewDTO(postId, postTitle, postDate, postContent, nickName, 0, isAnonymous);
			posts.add(0, postViewDTO);
			
			PostDAO postDAO = PostDAO.getInstance();
			return postDAO.addPost(postId, memberId, postTitle, postContent, postDate, isAnonymous);
		}
		return null;
	}
	
	public String deletePost(String postId, String memberId) {
		if(nullCheck(new String[] {postId, memberId})) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostId().equals(postId) && posts.get(i).getNickName().equals(memberDAO.searchMemberNickName(memberId))) {
					posts.remove(i);
					
					ReplyViewDAO replyViewDAO = ReplyViewDAO.getInstance();
					CommentViewDAO commentViewDAO = CommentViewDAO.getInstance();
					
					Object[] replies = replyViewDAO.getReplies(postId);
					for(int j = 0; j < replies.length; j++) {
						String replyId = ((ReplyViewDTO)replies[i]).getReplyId();
						Object[] comments = commentViewDAO.getComment(replyId);
						for(int k = 0; k < comments.length; k++) {
							String commentId = ((CommentViewDTO)comments[k]).getCommentId();
							commentViewDAO.deleteComment(replyId, commentId, memberId);
						}
						replyViewDAO.deleteReply(postId, replyId, memberId);
					}
					
					PostDAO postDAO = PostDAO.getInstance();
					postDAO.deletePost(postId, memberId);
					return memberId;
				}
			}
		}
		return null;
	}
	
	public String updatePostTitle(String postId, String memberId, String newPostTitle) {
		if(nullCheck(new String[]{postId, memberId, newPostTitle})) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostId().equals(postId) && posts.get(i).getNickName().equals(memberDAO.searchMemberNickName(memberId))) {
					posts.get(i).setPostTitle(newPostTitle);
					
					PostDAO postDAO = PostDAO.getInstance();
					postDAO.updatePostTitle(postId, memberId, newPostTitle);
					
					return memberId;
				}
			}
			return null;
		}
		return null;
	}

	public String updatePostContent(String postId, String memberId, String newPostContent) {
		if(nullCheck(new String[]{postId, memberId, newPostContent})) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostId().equals(postId) && posts.get(i).getNickName().equals(memberDAO.searchMemberNickName(memberId))) {
					posts.get(i).setPostContent(newPostContent);
					
					PostDAO postDAO = PostDAO.getInstance();
					postDAO.updatePostContent(postId, memberId, newPostContent);
					
					return memberId;
				}
			}
			return null;
		}
		return null;
	}
	
	public String updatePostAnonymous(String postId, String memberId, boolean newAnonymous) {
		if(nullCheck(new String[]{postId, memberId})) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostId().equals(postId) && posts.get(i).getNickName().equals(memberDAO.searchMemberNickName(memberId))) {
					posts.get(i).setAnonymous(newAnonymous);
					
					PostDAO postDAO = PostDAO.getInstance();
					postDAO.updatePostAnonymous(postId, memberId, newAnonymous);
					
					return memberId;
				}
			}
			return null;
		}
		return null;
	}
	
	public String updatePost(String postId, String memberId, String newPostTitle, String newPostContent, boolean newPostAnonymous) {
		if(nullCheck(new String[]{postId, memberId, newPostTitle, newPostContent})) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostId().equals(postId) && posts.get(i).getNickName().equals(memberDAO.searchMemberNickName(memberId))) {
					posts.get(i).setPostTitle(newPostTitle);
					posts.get(i).setPostContent(newPostContent);
					posts.get(i).setAnonymous(newPostAnonymous);
					
					PostDAO postDAO = PostDAO.getInstance();
					postDAO.updatePost(postId, memberId, newPostTitle, newPostContent, newPostAnonymous);
					
					return memberId;
				}
			}
			
			return null;
		}
		return null;
	}
	
	public Object[] searchPost(String keyword) {
		if(nullCheck(new String[]{keyword})) {
			List<PostViewDTO> searchedPostViewDTO = new ArrayList<PostViewDTO>();
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostTitle().indexOf(keyword) != -1) {
					searchedPostViewDTO.add(posts.get(i));
					continue;
				}
				else if(posts.get(i).getPostContent().indexOf(keyword) != -1) {
					searchedPostViewDTO.add(posts.get(i));
					continue;
				}
			}
			return searchedPostViewDTO.toArray();	
		}
		return null;
	}
	
	public Object[] searchPostTitle(String keyword) {
		if(nullCheck(new String[]{keyword})) {
			List<PostViewDTO> searchedPostViewDTO = new ArrayList<PostViewDTO>();
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostTitle().indexOf(keyword) != -1) {
					searchedPostViewDTO.add(posts.get(i));
				}
			}
			return searchedPostViewDTO.toArray();
		}
		return null;
	}
	
	public Object[] searchPostContent(String keyword) {
		if(nullCheck(new String[]{keyword})) {
			List<PostViewDTO> searchedPostViewDTO = new ArrayList<PostViewDTO>();
			for(int i = 0; i < posts.size(); i++) {
				if(posts.get(i).getPostContent().indexOf(keyword) != -1) {
					searchedPostViewDTO.add(posts.get(i));
				}
			}
			
			return searchedPostViewDTO.toArray();
		}
		return null;
	}
	
	public boolean nullCheck(String[] str) {
		if(str == null) {
			return false;
		}
		for(int i = 0; i < str.length; i++) {
			if(str[i] == null) {
				return false;
			}
		}
		return true;
	}
}
