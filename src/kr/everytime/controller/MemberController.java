package kr.everytime.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.everytime.dao.MemberDAO;
import kr.everytime.dto.MemberDTO;
import kr.everytime.service.MemberService;

public class MemberController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String job = request.getParameter("job");
		MemberService memberService = MemberService.getInstance();

		switch (job) {
		case "regist":
			try {
				regist(request, response, memberService);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "login":
			try {
				login(request, response, memberService);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "logout":
			try {
				logout(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "checkPwd":
			checkPwd(request, response, memberService);
			break;
		case "findId":
			searchMemberId(request, response, memberService);
			break;
		case "findPwd":
			searchMemberPwd(request, response, memberService);
			break;
		case "setting":
			try {
				myInfo(request, response, memberService);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
			break;
		case "deleteMember":
			try {
				deleteMember(request, response, memberService);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "update":
			try {
				update(request, response, memberService);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
			break;
		case "realUpdate":
			try {
				realUpdate(request, response, memberService);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
			break;
		}

	}

	public void realUpdate(HttpServletRequest request, HttpServletResponse response, MemberService memberService)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		String generatedId = null;
		// 세션아이디/멤버아이디
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				generatedId = cookie.getValue();
				break;
			}
		}

		if (generatedId == null) {
			response.sendRedirect("login.html");
		}

		HttpSession session = request.getSession();
		String sessionId = (String) session.getId();
		// 세션아이디
		String[] aryGeneratedId = generatedId.split("/");

		MemberDAO memberDAO = MemberDAO.getInstance();
		MemberDTO member = (MemberDTO) memberDAO.searchMember(aryGeneratedId[1]);

		if (aryGeneratedId[0].equals(sessionId) && member.getPwd().equals(request.getParameter("pwd").toString())) {
			String id = request.getParameter("id").toString();
			String name = request.getParameter("name").toString();
			String nickName = request.getParameter("nickName").toString();
			String mail = request.getParameter("mail").toString();
			String pwd = request.getParameter("newPwd").toString();

			memberDAO.updateMember(id, request.getParameter("pwd").toString(), pwd, name, nickName, mail);
			request.setAttribute("member", member);
			ForwardController.forward(request, response, "setting.jsp");
		} else {
			response.sendRedirect("login.html");
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response, MemberService memberService)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		String generatedId = null;
		// 세션아이디/멤버아이디
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				generatedId = cookie.getValue();
				break;
			}
		}

		if (generatedId == null) {
			response.sendRedirect("login.html");
		}

		HttpSession session = request.getSession();
		String sessionId = (String) session.getId();
		// 세션아이디
		String[] aryGeneratedId = generatedId.split("/");

		if (aryGeneratedId[0].equals(sessionId)) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			MemberDTO member = (MemberDTO) memberDAO.searchMember(aryGeneratedId[1]);
			request.setAttribute("member", member);
			ForwardController.forward(request, response, "updateMemberInfo.jsp");
		} else {
			response.sendRedirect("login.html");
		}
	}

	public void deleteMember(HttpServletRequest request, HttpServletResponse response, MemberService memberService)
			throws IOException {
		Cookie[] cookies = request.getCookies();
		String generatedId = null;
		// 세션아이디/멤버아이디
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				generatedId = cookie.getValue();
				System.out.println(generatedId);
				break;
			}
		}

		if (generatedId == null) {
			response.sendRedirect("login.html");
		}

		HttpSession session = request.getSession();
		String sessionId = (String) session.getId();
		// 세션아이디
		String[] aryGeneratedId = generatedId.split("/");

		if (aryGeneratedId[0].equals(sessionId)) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			MemberDTO member = (MemberDTO) memberDAO.searchMember(aryGeneratedId[1]);
			memberDAO.deleteMember(member.getMemberId(), member.getPwd());
			System.out.println("삭제됨");
		}
		response.sendRedirect("login.html");
	}

	public void myInfo(HttpServletRequest request, HttpServletResponse response, MemberService memberService)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		String generatedId = null;
		// 세션아이디/멤버아이디
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("generatedId")) {
				generatedId = cookie.getValue();
				break;
			}
		}

		if (generatedId == null) {
			response.sendRedirect("login.html");
		}

		HttpSession session = request.getSession();
		String sessionId = (String) session.getId();
		// 세션아이디
		String[] aryGeneratedId = generatedId.split("/");

		if (aryGeneratedId[0].equals(sessionId)) {
			MemberDAO memberDAO = MemberDAO.getInstance();
			MemberDTO member = (MemberDTO) memberDAO.searchMember(aryGeneratedId[1]);
			request.setAttribute("member", member);
			ForwardController.forward(request, response, "setting.jsp");
		} else {
			response.sendRedirect("login.html");
		}
	}

	public void regist(HttpServletRequest request, HttpServletResponse response, MemberService memberService)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String nickName = request.getParameter("nickName");
		String mail = request.getParameter("mail");

		if (memberService.addMember(id, pwd, name, nickName, mail)) {
			ForwardController.forward(request, response, "registResult.jsp");
		} else {
			MemberDAO memberDAO = MemberDAO.getInstance();
			if (memberDAO.searchMember(id) != null) {
				request.setAttribute("idError", "아이디가 중복되었습니다");
			}
			if (memberDAO.searchId(mail) != null) {
				request.setAttribute("mailError", "이메일이 중복되었습니다");
			}
			if (checkNickName(nickName, memberDAO)) {
				request.setAttribute("nickNameError", "이메일이 중복되었습니다");
			}
			ForwardController.forward(request, response, "regist.html");
		}
	}

	public boolean checkNickName(String nickName, MemberDAO memberDAO) {
		Object[] members = memberDAO.getMembers();
		for (Object member : members) {
			if (((MemberDTO) member).getNickName().equals(nickName)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkPwd(HttpServletRequest request, HttpServletResponse response, MemberService memberService) {
		String pwd = request.getParameter("pwd");
		String memberId = request.getParameter("memberId");

		if (memberId == null) {
			String generatedId = null;
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("generatedId")) {
					generatedId = cookie.getValue();
					break;
				}
			}
			if (request.getSession().getId().equals(generatedId.split("/")[0])) {
				String id = generatedId.split("/")[1];
				if (memberService.searchMemberInfo(id, pwd) != null) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			if (memberService.searchMemberInfo(memberId, pwd) != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	public void login(HttpServletRequest request, HttpServletResponse response, MemberService memberService)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");

		if (memberService.login(id, pwd)) {
			HttpSession session = request.getSession();
			String sessionId = session.getId();
			String generatedId = sessionId + "/" + id;
			response.addCookie(new Cookie("generatedId", generatedId));
			request.setAttribute("pageNum", 1);
			request.removeAttribute("job");
			request.setAttribute("job", "postList");
			BoardListController boardListController = new BoardListController();
			boardListController.execute(request, response);
		} else {
			response.sendRedirect("failLogin.html");
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("login.html");
	}

	public void searchMemberId(HttpServletRequest request, HttpServletResponse response, MemberService memberService) {
		String mail = request.getParameter("idMail");
		String memberId = memberService.searchMemberId(mail);

		try {
			if (memberId != null) {
				request.setAttribute("memberId", memberId);
				ForwardController.forward(request, response, "findIdResult.jsp");
			} else {
				request.setAttribute("error", "id를 찾을 수 없습니다.");
				ForwardController.forward(request, response, "failFindMemberInfo.jsp");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchMemberPwd(HttpServletRequest request, HttpServletResponse response, MemberService memberService) {
		String id = request.getParameter("id");
		String mail = request.getParameter("pwdMail");

		String memberPwd = memberService.searchMemberPwd(id, mail);

		try {
			if (memberPwd != null) {
				request.setAttribute("memberPwd", memberPwd);
				ForwardController.forward(request, response, "findPwdResult.jsp");
			} else {
				request.setAttribute("error", "비밀번호를 찾을 수 없습니다.");
				ForwardController.forward(request, response, "failFindMemberInfo.jsp");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
