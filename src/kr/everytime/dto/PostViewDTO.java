package kr.everytime.dto;

import java.sql.Timestamp;

public class PostViewDTO {
	private String postId;
	private String postTitle;
	private Timestamp postDate;
	private String postContent;
	private String nickName;
	private int relplyCnt;
	private boolean isAnonymous;
	public PostViewDTO() {
		super();
	}
	public PostViewDTO(String postId, String postTitle, Timestamp postDate, String postContent, String nickName,
			int relplyCnt, boolean isAnonymous) {
		super();
		this.postId = postId;
		this.postTitle = postTitle;
		this.postDate = postDate;
		this.postContent = postContent;
		this.nickName = nickName;
		this.relplyCnt = relplyCnt;
		this.isAnonymous = isAnonymous;
	}
	public String getPostId() {
		return postId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public Timestamp getPostDate() {
		return postDate;
	}
	public String getPostContent() {
		return postContent;
	}
	public String getNickName() {
		return nickName;
	}
	public int getRelplyCnt() {
		return relplyCnt;
	}
	public boolean isAnonymous() {
		return isAnonymous;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setRelplyCnt(int relplyCnt) {
		this.relplyCnt = relplyCnt;
	}
	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public String toString() {
		return "PostViewDTO [postId=" + postId + ", postTitle=" + postTitle + ", postDate=" + postDate
				+ ", postContent=" + postContent + ", nickName=" + nickName + ", relplyCnt=" + relplyCnt
				+ ", isAnonymous=" + isAnonymous + "]";
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostViewDTO other = (PostViewDTO) obj;
		if (isAnonymous != other.isAnonymous)
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (postContent == null) {
			if (other.postContent != null)
				return false;
		} else if (!postContent.equals(other.postContent))
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
		if (postTitle == null) {
			if (other.postTitle != null)
				return false;
		} else if (!postTitle.equals(other.postTitle))
			return false;
		if (relplyCnt != other.relplyCnt)
			return false;
		return true;
	}

}
