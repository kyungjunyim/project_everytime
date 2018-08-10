package kr.everytime.dto;

public class MemberDTO {
	private String memberId;
	private String pwd;
	private String name;
	private String nickName;
	private String mail;

	public MemberDTO(){	}

	public MemberDTO(String memberId, String pwd, String name, String nickName, String mail){
		this.memberId = memberId;
		this.pwd = pwd;
		this.name = name;
		this.nickName = nickName;
		this.mail = mail;
	}
	
	public void setMemberId(String memberId){
		this.memberId = memberId;
	}
	
	public String getMemberId(){
		return memberId;
	}
	
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	
	public String getPwd(){
		return pwd;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	
	public String getNickName(){
		return nickName;
	}
	
	public void setMail(String mail){
		this.mail = mail;
	}
	
	public String getMail(){
		return mail;
	}

	
	public String toString() {
		return "MemberDTO [memberId=" + memberId + ", pwd=" + pwd + ", name=" + name + ", nickName=" + nickName
				+ ", mail=" + mail + "]";
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MemberDTO)) {
			return false;
		}
		MemberDTO other = (MemberDTO) obj;
		if (mail == null) {
			if (other.mail != null) {
				return false;
			}
		} else if (!mail.equals(other.mail)) {
			return false;
		}
		if (memberId == null) {
			if (other.memberId != null) {
				return false;
			}
		} else if (!memberId.equals(other.memberId)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (nickName == null) {
			if (other.nickName != null) {
				return false;
			}
		} else if (!nickName.equals(other.nickName)) {
			return false;
		}
		if (pwd == null) {
			if (other.pwd != null) {
				return false;
			}
		} else if (!pwd.equals(other.pwd)) {
			return false;
		}
		return true;
	}
	
}
