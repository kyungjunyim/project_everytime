package kr.everytime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.teamwith.db.ConnectDB;

public class CommentDAO {
	private static CommentDAO commentDAO;
	static{
		commentDAO = new CommentDAO();
	}
	
	public static CommentDAO getInstance() { return commentDAO;}
	
	private CommentDAO(){ }
	
	public String addComment(String commentId, String replyId, String postId, String memberId, String commentContent, Timestamp postDate, boolean isAnonymous )
	{
		if( !nullCheck(new String[]{ commentId, memberId, commentId, postId, commentContent}) ){return null;}
		String anonymous = "0";
		if(isAnonymous)
		{
			anonymous= "1";
		}
		else{
			;
		}
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = ConnectDB.connect();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO comment_tb VALUES(?,?,?,?,?,?,?)");
			pstmt.setString(1, commentId);
			pstmt.setString(2, replyId);
			pstmt.setString(3, postId);
			pstmt.setString(4, memberId);
			pstmt.setString(5, commentContent);
			pstmt.setTimestamp(6, postDate);
			pstmt.setString(7, anonymous);	
			pstmt.executeUpdate();
			return commentId;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String deleteComment(String commentId, String memberId)
	{
		if( !nullCheck(new String[]{ memberId, commentId}) ){return null;}
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = ConnectDB.connect();
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM comment_tb WHERE member_id = ? AND comment_id = ?");
		pstmt.setString(1, memberId);
		pstmt.setString(2, commentId);
		pstmt.executeUpdate();
		return commentId;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String updateCommentContent(String commentId, String memberId, String newCommentContent)
	{
		if( !nullCheck(new String[]{ memberId, commentId, newCommentContent}) ){return null;}
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = ConnectDB.connect();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE comment_tb SET comment_content = ? WHERE comment_id = ? AND member_id = ?"); 
			pstmt.setString(1, newCommentContent);
			pstmt.setString(2, commentId);
			pstmt.setString(3, memberId);
			pstmt.executeUpdate();
			return commentId;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String updateAnonymous(String commentId, String memberId, boolean newIsAnonymous )
	{
		if( !nullCheck(new String[]{ memberId, commentId}) ){return null;}
		String anonymous = "0";
		if(newIsAnonymous)
		{
			anonymous= "1";
		}
		else{
			;
		}
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = ConnectDB.connect();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE comment_tb SET is_anonymous=? WHERE comment_id = ? AND member_id = ?"); 
			pstmt.setString(1, anonymous);
			pstmt.setString(2, commentId);
			pstmt.setString(3, memberId);
			pstmt.executeUpdate();
			return commentId;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public String updateComment(String commentId, String memberId, String newCommentContent, boolean newIsAnonymous )
	{
		if( !nullCheck(new String[]{ memberId, commentId, newCommentContent}) ){return null;}
		String anonymous = "0";
		if(newIsAnonymous)
		{
			anonymous= "1";
		}
		else{
			;
		}
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = ConnectDB.connect();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE comment_tb SET comment_content = ?, is_anonymous=? WHERE comment_id = ? AND member_id = ?"); 
			pstmt.setString(1, newCommentContent);
			pstmt.setString(2, anonymous);
			pstmt.setString(3, commentId);
			pstmt.setString(4, memberId);
			pstmt.executeUpdate();
			return commentId;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
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
