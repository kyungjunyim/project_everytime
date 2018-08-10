package kr.everytime.service;

import kr.everytime.dao.MemberDAO;
import kr.everytime.dto.MemberDTO;

public class MemberService {
	private static MemberService memberService;
	private MemberDAO memberDAO;
	
	static {
		memberService = new MemberService();
	}
	
	private MemberService() {
		memberDAO = MemberDAO.getInstance();
	}
	
	public static MemberService getInstance() {
		return memberService;
	}
	
	public boolean addMember(String id, String pwd, String name, String nickName, String mail) {
		String result = memberDAO.addMember(id, pwd, name, nickName, mail);
		
		if(result == null) return false;
		return true;
	}
	
	public boolean deleteMember(String id, String pwd) {
		return true;
	}
	
	public boolean updateMember(String id, String pwd, String newPwd, String newName, String newNickName, String newMail) {
		return true;
	}
	
	public String[] searchMemberInfo(String id, String pwd) {
		return null;
	}
	
	public String searchMemberId(String mail) {
		return memberDAO.searchId(mail);
	}
	
	public String searchMemberPwd(String id, String mail) {
		return memberDAO.searchPwd(id, mail);
	}
	
	public String generateTempPwd() {
		return null;
	}
	
	public boolean login(String id, String pwd) {
		MemberDTO member = (MemberDTO)memberDAO.searchMember(id);
		if(member == null) return false;
		
		if(member.getPwd().equals(pwd)) {
			return true;
		}
		return false;
	}
	
	public boolean logout(String id) {
		return true;
	}
}
