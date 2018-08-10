package kr.everytime.dto;

import java.sql.Timestamp;

public class ReplyViewDTO {
	private String postId;
	private String replyId;
	private String nickName;
	private String replyContent;
	private Timestamp postDate;
	private boolean isAnonymous;
	
	public ReplyViewDTO() {
		super();
	}

	public ReplyViewDTO(String postId, String replyId, String nickName, String replyContent, Timestamp postDate,
			boolean isAnonymous) {
		super();
		this.postId = postId;
		this.replyId = replyId;
		this.nickName = nickName;
		this.replyContent = replyContent;
		this.postDate = postDate;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}

	public boolean isAnonymous() {
		return isAnonymous;
	}

	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	@Override
	public String toString() {
		return "ReplyViewDTO [postId=" + postId + ", replyId=" + replyId + ", nickName=" + nickName + ", replyContent="
				+ replyContent + ", postDate=" + postDate + ", isAnonymous=" + isAnonymous + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReplyViewDTO other = (ReplyViewDTO) obj;
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
		if (replyContent == null) {
			if (other.replyContent != null)
				return false;
		} else if (!replyContent.equals(other.replyContent))
			return false;
		if (replyId == null) {
			if (other.replyId != null)
				return false;
		} else if (!replyId.equals(other.replyId))
			return false;
		return true;
	}
	
	
}
