package kr.everytime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.teamwith.db.ConnectDB;

import kr.everytime.dto.MemberDTO;

public class MemberDAO {

	private static MemberDAO memberDAO;
	private Map<String, MemberDTO> members;
	
	static {
		memberDAO = new MemberDAO();
	}
	
	public MemberDAO() {
		members = new HashMap<String, MemberDTO>();
	}

	public static MemberDAO getInstance() {
		return memberDAO;
	}

	public boolean loadMember() {
		Connection con = ConnectDB.connect();
		MemberDTO member = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		boolean flag = true;
		try {
			pstmt = con.prepareStatement("select * from member_tb");
			res = pstmt.executeQuery();

			while (res.next()) {
				String memberId = res.getString(1);
				String memberPwd = res.getString(2);
				String memberName = res.getString(3);
				String memberNickName = res.getString(4);
				String memberMail = res.getString(5);

				member = new MemberDTO(memberId, memberPwd, memberName, memberNickName, memberMail);
				if (members.put(memberId, member) != null) {
					flag = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.close(con, pstmt, res);

		}
		return flag;
	}

	public Object[] getMembers(){
		List<MemberDTO> members = new ArrayList<MemberDTO>();
		
		Set<String> keySet = this.members.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			members.add(this.members.get(key));
		}
		
		return members.toArray();
	}
	
	public String addMember(String memberId, String pwd, String name, String nickName, String mail) {
		if(nullCheck(new String[] {memberId, pwd, name, nickName, mail})) {
			boolean flag = true;
			PreparedStatement pstmt = null;
			
			MemberDTO member = new MemberDTO(memberId, pwd, name, nickName, mail);
			MemberDTO inMember = null;
			
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				inMember = members.get(key);
				if (memberId.equals(inMember.getMemberId())) {
					flag = false;
				}
			}

			if (flag) {
				members.put(memberId, member);
				Connection con = ConnectDB.connect();
				try {
					String insertQuery = "INSERT INTO member_tb VALUES(?,?,?,?,?)";
					pstmt = con.prepareStatement(insertQuery);
					pstmt.setString(1, memberId);
					pstmt.setString(2, pwd);
					pstmt.setString(3, name);
					pstmt.setString(4, nickName);
					pstmt.setString(5, mail);
					pstmt.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					ConnectDB.close(con, pstmt);
				}

				return memberId;
			}

			else {
				return null;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private String addMember(MemberDTO member) {
		boolean flag = true;
		PreparedStatement pstmt = null;
		Set<String> keySet = members.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		MemberDTO memberInMap = null;
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			memberInMap = members.get(key);
			if (memberInMap.getMemberId().equals(member.getMemberId())) {
				flag = false;
			}

		}

		if (flag) {
			members.put(member.getMemberId(), member);

			Connection con = ConnectDB.connect();
			try {
				String insertQuery = "INSERT INTO member_tb VALUES(?,?,?,?,?)";
				pstmt = con.prepareStatement(insertQuery);
				// pstmt = con.prepareStatement("INSERT INTO member_tb
				// VALUES('"+memberId+"','"+pwd+"','"+name+"','"+nickName+"','"+mail+"')");
				pstmt.setString(1, member.getMemberId());
				pstmt.setString(2, member.getPwd());
				pstmt.setString(3, member.getName());
				pstmt.setString(4, member.getNickName());
				pstmt.setString(5, member.getMail());
				pstmt.executeQuery();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				ConnectDB.close(con, pstmt);
			}

			return member.getMemberId();
		} else {
			return null;
		}

	}

	@SuppressWarnings("unused")
	private String deleteMember(MemberDTO member) {
		boolean flag = true;
		PreparedStatement pstmt = null;
		Set<String> keySet = members.keySet();
		Iterator<String> keyIterator = keySet.iterator();

		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			MemberDTO memberInMap = members.get(key);
			if (member.equals(memberInMap)) {
				members.remove(key);

			} else {
				flag = false;
			}
		}

		if (flag) {
			// 삭제가 되었을 경우
			Connection con = ConnectDB.connect();
			String deleteQuery = "Delete * FROM member_tb WHERE member_id=?, pwd=?, name=?, nick_name=?, mail=?";
			try {
				pstmt = con.prepareStatement(deleteQuery);
				pstmt.setString(1, member.getMemberId());
				pstmt.setString(2, member.getPwd());
				pstmt.setString(3, member.getName());
				pstmt.setString(4, member.getNickName());
				pstmt.setString(5, member.getMail());
				pstmt.executeQuery();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				ConnectDB.close(con, pstmt);
			}
			return member.getMemberId();

		} else {
			return null;
		}

	}

	public String deleteMember(String id, String pwd) {
		if(nullCheck(new String[] {id, pwd})) {
			String key = null;
			boolean flag = false;
			PreparedStatement pstmt = null;
			
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			while (keyIterator.hasNext()) {
				key = keyIterator.next();
				memberInMap = members.get(key);
				if (memberInMap.getMemberId().equals(id) && memberInMap.getPwd().equals(pwd)) {
					flag = true;
					break;
				}
			}
			
			if(key != null) {
				members.remove(key);
			}

			if (flag) {
				Connection con = ConnectDB.connect();
				String deleteQuery = "Delete FROM member_tb WHERE member_id=?";
				try {
					pstmt = con.prepareStatement(deleteQuery);
					pstmt.setString(1, memberInMap.getMemberId());
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					ConnectDB.close(con, pstmt);
				}
				return memberInMap.getMemberId();

			} else {
				return null;
			}	
		}
		return null;
	}

	public String updatePwd(String memberId, String pwd, String newPwd) {
		if(nullCheck(new String[] {memberId, pwd, newPwd})) {
			boolean flag = false;
			PreparedStatement pstmt = null;
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if (memberInMap.getMemberId().equals(memberId) && memberInMap.getPwd().equals(pwd)) {
					memberInMap.setPwd(newPwd);			
					flag = true;
				}

			}
			
			if(flag){
				
				Connection con = ConnectDB.connect();
				String updateQuery ="UPDATE member_tb SET pwd=? where member_id=?";
				try{
					pstmt = con.prepareStatement(updateQuery);
					pstmt.setString(1, newPwd);
					pstmt.setString(2, memberId);
					pstmt.executeQuery();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					ConnectDB.close(con, pstmt);
				}
				return memberInMap.getMemberId();
				
				
			}else{
				return null;
			}
		}
		return null;
	}
	
	public String updateName(String memberId, String pwd, String newName) {
		if(nullCheck(new String[] {memberId, pwd, newName})) {
			boolean flag = false;
			PreparedStatement pstmt = null;
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if (memberInMap.getMemberId().equals(memberId) && memberInMap.getPwd().equals(pwd)) {
					memberInMap.setName(newName);		
					flag = true;
				}

			}
			
			if(flag){
				
				Connection con = ConnectDB.connect();
				String updateQuery ="UPDATE member_tb SET name=? where member_id=?";
				try{
					pstmt = con.prepareStatement(updateQuery);
					pstmt.setString(1, newName);
					pstmt.setString(2, memberId);
					pstmt.executeQuery();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					ConnectDB.close(con, pstmt);
				}
				return memberInMap.getMemberId();
				
				
			}else{
				return null;
			}
		}
		return null;
	}
	
	public String updateNickName(String memberId, String pwd, String newNick) {
		if(nullCheck(new String[] {memberId, pwd, newNick})) {
			boolean flag = false;
			PreparedStatement pstmt = null;
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if (memberInMap.getMemberId().equals(memberId) && memberInMap.getPwd().equals(pwd)) {
					memberInMap.setNickName(newNick);				
					flag = true;
				}

			}
			
			if(flag){
				
				Connection con = ConnectDB.connect();
				String updateQuery ="UPDATE member_tb SET nick_name=? where member_id=?";
				try{
					pstmt = con.prepareStatement(updateQuery);
					pstmt.setString(1, newNick);
					pstmt.setString(2, memberId);
					pstmt.executeQuery();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					ConnectDB.close(con, pstmt);
				}
				return memberInMap.getMemberId();
				
				
			}else{
				return null;
			}
		}
		return null;
	}
	
	public String updateMail(String memberId, String pwd, String newMail) {
		if(nullCheck(new String[] {memberId, pwd, newMail})) {
			boolean flag = false;
			PreparedStatement pstmt = null;
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if (memberInMap.getMemberId().equals(memberId) && memberInMap.getPwd().equals(pwd)) {
					memberInMap.setMail(newMail);				
					flag = true;
				}

			}
			
			if(flag){
				
				Connection con = ConnectDB.connect();
				String updateQuery ="UPDATE member_tb SET mail=? where member_id=?";
				try{
					pstmt = con.prepareStatement(updateQuery);
					pstmt.setString(1, newMail);
					pstmt.setString(2, memberId);
					pstmt.executeQuery();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					ConnectDB.close(con, pstmt);
				}
				return memberInMap.getMemberId();
				
				
			}else{
				return null;
			}
		}
		return null;
	}
	
	public String updateMember(String memberId, String pwd, String newPwd, String newName, String newNickName, String newMail) {
		if(nullCheck(new String[] {memberId, pwd, newPwd, newName, newNickName, newMail})) {
			boolean flag = false;
			PreparedStatement pstmt = null;
			
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if (memberInMap.getMemberId().equals(memberId) && memberInMap.getPwd().equals(pwd)) {
					memberInMap.setPwd(newPwd);
					memberInMap.setName(newName);
					memberInMap.setNickName(newNickName);
					memberInMap.setMail(newMail);				
					flag = true;
				}
			}
			
			if(flag){
				Connection con = ConnectDB.connect();
				String updateQuery ="UPDATE member_tb SET pwd=?, name=?, nick_name=?, mail=? where member_id=?";
				try{
					pstmt = con.prepareStatement(updateQuery);
					pstmt.setString(1, newPwd);
					pstmt.setString(2, newName);
					pstmt.setString(3, newNickName);
					pstmt.setString(4, newMail);
					pstmt.setString(5, memberId);
					pstmt.executeUpdate();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					ConnectDB.close(con, pstmt);
				}
				return memberInMap.getMemberId();
			}else{
				return null;
			}
		}
		return null;
	}
	
	public String[] searchMemberInfo(String id, String pwd){
		if(nullCheck(new String[] {id, pwd})) {
			String[] aryMem = null;
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			
			
			while(keyIterator.hasNext()){
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if(memberInMap.equals(id) && memberInMap.equals(pwd)){
					aryMem[aryMem.length] = memberInMap.getMemberId();
				}
			}
			return aryMem;
		}
		return null;
	}
	
	
	public String searchId(String mail){
		if(nullCheck(new String[] {mail})) {
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			String memberId=null;
			while(keyIterator.hasNext()){
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if(memberInMap.getMail().equals(mail)){
					memberId = memberInMap.getMemberId();
				}
			}
			return memberId;
		}
		return null;
	}
	
	public String searchPwd(String memberId, String mail){
		if(nullCheck(new String[] {memberId, mail})) {
			MemberDTO member = members.get(memberId);
			if(member != null && member.getMail().equals(mail)) {
				return member.getPwd();
			}
			return null;
		}
		return null;
	}
	
	public String searchMemberNickName(String memberId) {
		if(nullCheck(new String[] {memberId})) {
			String nickName = null;

			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO member = null;
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				member = members.get(key);
				if (memberId.equals(member.getMemberId())) {
					nickName = member.getNickName();
					break;
				}
			}
			return nickName;
		}
		return null;
	}
	
	public Object searchMember(String memberId) {
		if(nullCheck(new String[] {memberId})) {
			return members.get(memberId);
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private MemberDTO searchMember(String id, String pwd){
		if(nullCheck(new String[] {id, pwd})) {
			Set<String> keySet = members.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			MemberDTO memberInMap = null;
			MemberDTO returnMember = null;
			while(keyIterator.hasNext()){
				String key = keyIterator.next();
				memberInMap = members.get(key);
				if(memberInMap.getMemberId().equals(id) && memberInMap.getPwd().equals(pwd)){
					returnMember = memberInMap;
				}
			}
		
			return returnMember;
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
