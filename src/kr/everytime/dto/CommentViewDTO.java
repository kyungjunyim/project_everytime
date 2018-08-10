package kr.everytime.dto;

import java.sql.Timestamp;

public class CommentViewDTO {

	private String postId;
	private String replyId;
	private String commentId;
	private String commentContent;
	private Timestamp postDate;
	private String nickName;
	private boolean isAnonymous;
	public CommentViewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentViewDTO(String postId, String replyId, String commentId, String commentContent, 
			String nickName, Timestamp postDate, boolean isAnonymous) {
		super();
		this.postId = postId;
		this.replyId = replyId;
		this.commentId = commentId;
		this.commentContent = commentContent;
		this.postDate = postDate;
		this.nickName = nickName;
		this.isAnonymous = isAnonymous;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Timestamp getPostDate() {
		return postDate;
	}
	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public boolean isAnonymous() {
		return isAnonymous;
	}
	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public String toString() {
		return "CommentViewDTO [postId=" + postId + ", replyId=" + replyId + ", commentId=" + commentId
				+ ", commentContent=" + commentContent + ", postDate=" + postDate + ", nickName=" + nickName
				+ ", isAnonymous=" + isAnonymous + "]";
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentViewDTO other = (CommentViewDTO) obj;
		if (commentContent == null) {
			if (other.commentContent != null)
				return false;
		} else if (!commentContent.equals(other.commentContent))
			return false;
		if (commentId == null) {
			if (other.commentId != null)
				return false;
		} else if (!commentId.equals(other.commentId))
			return false;
		if (isAnonymous != other.isAnonymous)
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (postDate == null) {
			if (other.postDate != null)
				return false;
		} else if (!postDate.equals(other.postDate))
			return false;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (replyId == null) {
			if (other.replyId != null)
				return false;
		} else if (!replyId.equals(other.replyId))
			return false;
		return true;
	}
	
	
}
