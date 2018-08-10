package kr.everytime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.teamwith.db.ConnectDB;

public class ReplyDAO {
	private static ReplyDAO replyDAO;
	
	static{
		replyDAO = new ReplyDAO();
	}
	
	private ReplyDAO(){ }
	
	public static ReplyDAO getInstance() {
		return replyDAO;
	}
	
	public String addReply(String replyId, String postId, String memberId, String replyContent,
			Timestamp postDate, boolean isAnonymous) {
		Connection con = ConnectDB.connect();
		PreparedStatement pstmt = null; 
		
		if(con != null) {
			try {
				pstmt = con.prepareStatement("insert into reply_tb values(?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, replyId);
				pstmt.setString(2, postId);
				pstmt.setString(3, memberId);
				pstmt.setString(4, replyContent);
				pstmt.setTimestamp(5, postDate);
				if(isAnonymous) {
					pstmt.setString(6, "1");
				}
				else {
					pstmt.setString(6, "0");
				}
				pstmt.executeUpdate();
			}
			catch(SQLException e) {
				System.out.println("SQL 오류");
			}
			ConnectDB.close(con, pstmt);
		}
		return memberId;
	}
	
	public String deleteReply(String replyId, String memberId) {
		Connection con = ConnectDB.connect();
		PreparedStatement pstmt = null; 
		
		if(con != null) {
			try {
				pstmt = con.prepareStatement("delete from reply_tb where reply_id=? and member_id=?");
				pstmt.setString(1, replyId);
				pstmt.setString(2, memberId);
				pstmt.executeUpdate();
			}
			catch(SQLException e) {
				System.out.println("SQL 오류");
			}
			ConnectDB.close(con, pstmt);
		}
		return memberId;
	}
	
	public String updateReplyContent(String replyId, String memberId, String newReplyContent) {
		Connection con = ConnectDB.connect();
		PreparedStatement pstmt = null; 
		
		if(con != null) {
			try {
				pstmt = con.prepareStatement("update reply_tb set reply_content=? where reply_id=? and member_id=?");
				pstmt.setString(1, newReplyContent);
				pstmt.setString(2, replyId);
				pstmt.setString(3, memberId);
				pstmt.executeUpdate();
			}
			catch(SQLException e) {
				System.out.println("SQL 오류");
			}
			ConnectDB.close(con, pstmt);
		}
		return memberId;
	}
	
	public String updateReplyAnonymous(String replyId, String memberId, boolean newReplyAnonymous) {
		Connection con = ConnectDB.connect();
		PreparedStatement pstmt = null; 
		
		if(con != null) {
			try {
				pstmt = con.prepareStatement("update reply_tb set is_anonymous=? where reply_id=? and member_id=?");
				if(newReplyAnonymous) {
					pstmt.setString(1, "1");
				}
				else {
					pstmt.setString(1, "0");
				}
				
				pstmt.setString(2, replyId);
				pstmt.setString(3, memberId);
				pstmt.executeUpdate();
			}
			catch(SQLException e) {
				System.out.println("SQL 오류");
			}
			if(pstmt != null) {
				ConnectDB.close(con, pstmt);
			}
		}
		return memberId;
	}
	
	public String updateReply(String replyId, String memberId, String newReplyContent, boolean newReplyAnonymous) {
		Connection con = ConnectDB.connect();
		PreparedStatement pstmt = null; 
		
		if(con != null) {
			try {
				pstmt = con.prepareStatement("update reply_tb set reply_content=?, is_anonymous=? where reply_id=? and member_id=?");
				
				pstmt.setString(1, newReplyContent);

				if(newReplyAnonymous) {
					pstmt.setString(2, "1");
				}
				else {
					pstmt.setString(2, "0");
				}
				pstmt.setString(3, replyId);
				pstmt.setString(4, memberId);
				pstmt.executeUpdate();
			}
			catch(SQLException e) {
				System.out.println("SQL 오류");
			}
			if(pstmt != null) {
				ConnectDB.close(con, pstmt);
			}
		}
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
