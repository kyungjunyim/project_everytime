package kr.everytime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.teamwith.db.ConnectDB;

public class PostDAO {
	private static PostDAO postDAO;
	
	static {
		postDAO = new PostDAO();
	}
	
	private PostDAO() {}
	
	public static PostDAO getInstance() {
		return postDAO;
	}
	
	public String addPost(String postId, String memberId, String postTitle, String postContent, Timestamp postDate, boolean isAnonymous) {
		if(nullCheck(new String[]{postId, memberId, postTitle, postContent})) {
			Connection con = ConnectDB.connect();
			PreparedStatement pstmt = null;
			int result = 0;
			
			try {
				pstmt = con.prepareStatement("insert into post_tb values(?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, postId);
				pstmt.setString(2, memberId);
				pstmt.setString(3, postTitle);
				pstmt.setString(4, postContent);
				pstmt.setTimestamp(5, postDate);
				if(isAnonymous) {
					pstmt.setString(6, "1");
				}
				else {
					pstmt.setString(6, "0");
				}
				result = pstmt.executeUpdate();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			ConnectDB.close(con, pstmt);
			
			if(result == 1) {
				return memberId;
			}
			else {
				return null;
			}
		}
		return null;
	}
	
	public String deletePost(String postId, String memberId) {
		if(nullCheck(new String[]{postId, memberId})) {
			Connection con = ConnectDB.connect();
			PreparedStatement pstmt = null;
			ResultSet res = null;
			int result = 0;
			
			try {
				pstmt = con.prepareStatement("select member_id from post_tb where post_id like ?");
				pstmt.setString(1, postId);
				res = pstmt.executeQuery();
				if(res.next()) {
					String id = res.getString(1);
					if(memberId.equals(id)) {
						pstmt = con.prepareStatement("delete from post_tb where post_id like ?");
						pstmt.setString(0, postId);
						result = pstmt.executeUpdate();
					}
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			ConnectDB.close(con, pstmt, res);
			
			if(result == 1) {
				return memberId;
			}
			else {
				return null;
			}
		}
		return null;
	}
	
	public String updatePostTitle(String postId, String memberId, String newPostTitle) {
		if(nullCheck(new String[]{postId, memberId, newPostTitle})) {
			Connection con = ConnectDB.connect();
			PreparedStatement pstmt = null;
			ResultSet res = null;
			int result = 0;
			
			try {
				pstmt = con.prepareStatement("select member_id from post_tb where post_id like ?");
				pstmt.setString(1, postId);
				res = pstmt.executeQuery();
				if(res.next()) {
					String id = res.getString(1);
					if(memberId.equals(id)) {
						pstmt = con.prepareStatement("update post_tb set post_title = ? where post_id like ?");
						pstmt.setString(1, newPostTitle);
						pstmt.setString(2, postId);
						result = pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ConnectDB.close(con, pstmt, res);
			
			if(result == 1) {
				return memberId;
			}
			else {
				return null;
			}
		}
		return null;
	}
	
	public String updatePostContent(String postId, String memberId, String newPostContent) {
		if(nullCheck(new String[]{postId, memberId, newPostContent})) {
			Connection con = ConnectDB.connect();
			PreparedStatement pstmt = null;
			ResultSet res = null;
			int result = 0;
			
			try {
				pstmt = con.prepareStatement("select member_id from post_tb where post_id like ?");
				pstmt.setString(1, postId);
				res = pstmt.executeQuery();
				if(res.next()) {
					String id = res.getString(1);
					if(memberId.equals(id)) {
						pstmt = con.prepareStatement("update post_tb set post_content = ? where post_id like ?");
						pstmt.setString(1, newPostContent);
						pstmt.setString(2, postId);
						result = pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ConnectDB.close(con, pstmt, res);
			
			if(result == 1) {
				return memberId;
			}
			else {
				return null;
			}
		}
		return null;
	}
	
	public String updatePostAnonymous(String postId, String memberId, boolean newAnonymous) {
		if(nullCheck(new String[]{postId, memberId})) {
			Connection con = ConnectDB.connect();
			PreparedStatement pstmt = null;
			ResultSet res = null;
			int result = 0;
			
			try {
				pstmt = con.prepareStatement("select member_id from post_tb where post_id like ?");
				pstmt.setString(1, postId);
				res = pstmt.executeQuery();
				if(res.next()) {
					String id = res.getString(1);
					if(memberId.equals(id)) {
						if(newAnonymous) {
							pstmt = con.prepareStatement("update post_tb set is_anonymous = '1' where post_id like ?");
							pstmt.setString(1, postId);
							result = pstmt.executeUpdate();
						}
						else {
							pstmt = con.prepareStatement("update post_tb set is_anonymous = '0' where post_id like ?");
							pstmt.setString(1, postId);
							result = pstmt.executeUpdate();
						}
					}
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			ConnectDB.close(con, pstmt, res);
			
			if(result == 1) {
				return memberId;
			}
			else {
				return null;
			}
		}
		return null;
	}
	
	public String updatePost(String postId, String memberId, String newPostTitle, String newPostContent, boolean newPostAnonymous) {
		if(nullCheck(new String[]{postId, memberId, newPostTitle, newPostContent})) {
			Connection con = ConnectDB.connect();
			PreparedStatement pstmt = null;
			ResultSet res = null;
			int result = 0;
			
			try {
				pstmt = con.prepareStatement("select member_id from post_tb where post_id like ?");
				pstmt.setString(1, postId);
				res = pstmt.executeQuery();
				if(res.next()) {
					String id = res.getString(1);
					if(memberId.equals(id)) {
						if(newPostAnonymous) {
							pstmt = con.prepareStatement("update post_tb set is_anonymous = '1', post_title = ?, post_content = ? where post_id like ?");
							pstmt.setString(1, newPostTitle);
							pstmt.setString(2, newPostContent);
							pstmt.setString(3, postId);
							result = pstmt.executeUpdate();
						}
						else {
							pstmt = con.prepareStatement("update post_tb set is_anonymous = '0', post_title = ?, post_content = ? where post_id like ?");
							pstmt.setString(1, newPostTitle);
							pstmt.setString(2, newPostContent);
							pstmt.setString(3, postId);
							result = pstmt.executeUpdate();
						}
					}
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			ConnectDB.close(con, pstmt, res);
			
			if(result == 1) {
				return memberId;
			}
			else {
				return null;
			}
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
