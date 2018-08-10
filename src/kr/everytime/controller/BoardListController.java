package kr.everytime.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.everytime.dao.PostViewDAO;
import kr.everytime.service.PostService;

public class BoardListController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String job2 = null;

		String job = request.getParameter("job");

		switch (job) {
		case "postList":
			postList(request, response);
			return;
		}

		if (request.getAttribute("job") != null) {
			job2 = request.getAttribute("job").toString();
			switch (job2) {
			case "postList":
				postList(request, response);
				return;
			}
		}
	}

	private void postList(HttpServletRequest request, HttpServletResponse response) {
		List<String[]> objList = null;
		if (request.getAttribute("searchList") != null) {
			objList = (List<String[]>) request.getAttribute("searchList");

			for (String[] obj : objList) {
				for (int i = 0; i < 7; i++) {
					System.out.println(obj[i]);
				}
			}
		}

		PostService ps = PostService.getInstance();

		int pageNum = 0;

		if (request.getAttribute("pageNum") != null && request.getParameter("job").toString().equals("login")) {
			pageNum = Integer.parseInt(request.getAttribute("pageNum").toString());
		} else if (request.getAttribute("pageNum") != null
				&& request.getParameter("job").toString().equals("writePost")) {
			pageNum = Integer.parseInt(request.getAttribute("pageNum").toString());
		} else if (request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum").toString());
		}

		int num = PostViewDAO.getInstance().getPosts().length;

		int totalPage = num / 10;
		if (num % 10 != 0) {
			totalPage += 1;
		}
		List<String[]> posts = null;
		if (objList != null) {
			posts = objList;
		} else {
			posts = ps.getPosts(pageNum);
		}

		if (posts == null) {
			return;
		}

		request.setAttribute("posts", posts);
		request.setAttribute("postsLength", posts.size() - 1);
		request.setAttribute("totalPage", totalPage);

		try {
			ForwardController.forward(request, response, "main.jsp");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
