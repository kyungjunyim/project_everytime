package kr.everytime.service;

import java.util.ArrayList;
import java.util.List;

import kr.everytime.dao.PostViewDAO;
import kr.everytime.dto.PostViewDTO;

public class PostService {
	private static PostService postService;
	private PostViewDAO postViewDAO;

	static {
		postService = new PostService();
	}

	private PostService() {
		postViewDAO = PostViewDAO.getInstance();
	}

	public static PostService getInstance() {
		return postService;
	}

	public boolean addPost(String postTitle, String postContent, String memberId, boolean isAnonymous) {
		PostViewDAO postViewDao = PostViewDAO.getInstance();
		String res = postViewDao.addPost(postTitle, postContent, memberId, isAnonymous);
		if (res == null)
			return false;
		return true;
	}

	public boolean deletePost(String postId, String memberId) {
		PostViewDAO postViewDao = PostViewDAO.getInstance();
		String res = postViewDao.deletePost(postId, memberId);
		if (res == null)
			return false;
		return true;
	}

	public boolean updatePost(String postId, String memberId, String newPostTitle, String newPostContent,
			boolean newAnonymous) {
		PostViewDAO pvd = PostViewDAO.getInstance();
		String resultId = pvd.updatePost(postId, memberId, newPostTitle, newPostContent, newAnonymous);
		if (resultId != null) {
			return true;
		} else {
			return false;
		}

	}

	public List<String[]> getPosts(int pageNum) {
		// 2
		PostViewDAO pv = PostViewDAO.getInstance();

		Object[] posts = pv.getPosts();
		int num = pageNum * 10;
		// 20
		if (posts == null) {
			return null;
		}
		List<String[]> list = new ArrayList<String[]>();

		for (int i = num - 10; i < num; i++)
		// 10 19
		{
			if (posts[i] == null) {
				if (num < posts.length) {
					num += 1;
				}
				continue;
			}
			String[] postInfo = new String[7];
			postInfo[0] = ((PostViewDTO) posts[i]).getPostId();
			postInfo[1] = ((PostViewDTO) posts[i]).getPostTitle();
			postInfo[2] = ((PostViewDTO) posts[i]).getPostContent();
			postInfo[3] = ((PostViewDTO) posts[i]).getPostDate().toString();
			postInfo[4] = ((PostViewDTO) posts[i]).getNickName();
			postInfo[5] = "" + ((PostViewDTO) posts[i]).getRelplyCnt();
			postInfo[6] = "" + ((PostViewDTO) posts[i]).isAnonymous();
			list.add(postInfo);
		}

		return list;
	}

	public String[] getPostDetail(String postId) {
		Object[] objs = postViewDAO.getPosts();
		String[] str = new String[7];
		for (int i = 0; i < objs.length; i++) {
			if (((PostViewDTO) objs[i]).getPostId().equals(postId)) {
				str[0] = ((PostViewDTO) objs[i]).getPostId();
				str[1] = ((PostViewDTO) objs[i]).getPostTitle();
				str[2] = ((PostViewDTO) objs[i]).getPostDate().toString();
				str[3] = ((PostViewDTO) objs[i]).getPostContent();
				str[4] = ((PostViewDTO) objs[i]).getNickName();
				str[5] = "" + ((PostViewDTO) objs[i]).getRelplyCnt();
				str[6] = "" + ((PostViewDTO) objs[i]).isAnonymous();
				break;
			}
		}

		return str;
	}

	public List<String[]> searchPost(String keyword) {

		Object[] posts = postViewDAO.searchPost(keyword); // PostViewDTO[] е╦ют
		List<String[]> postList = new ArrayList<String[]>();

		for (int index = 0; index < posts.length; index++) {

			PostViewDTO post = (PostViewDTO) posts[index];
			String[] str = new String[7];

			str[0] = post.getPostId();
			str[1] = post.getPostTitle();
			str[2] = post.getPostDate().toString();
			str[3] = post.getPostContent();
			str[4] = post.getNickName();
			str[5] = post.getRelplyCnt() + "";
			boolean anonymous = post.isAnonymous();
			if (anonymous) {
				str[6] = "1";
			} else {
				str[6] = "0";
			}
			postList.add(index, str);
		}
		return postList;

	}

	public List<String[]> searchPostTitle(String keyword) {
		Object[] posts = postViewDAO.searchPostTitle(keyword);
		List<String[]> postList = new ArrayList<String[]>();

		for (int index = 0; index < posts.length; index++) {

			PostViewDTO post = (PostViewDTO) posts[index];
			String[] str = new String[7];

			str[0] = post.getPostId();
			str[1] = post.getPostTitle();
			str[2] = post.getPostDate().toString();
			str[3] = post.getPostContent();
			str[4] = post.getNickName();
			str[5] = post.getRelplyCnt() + "";
			boolean anonymous = post.isAnonymous();
			if (anonymous) {
				str[6] = "1";
			} else {
				str[6] = "0";
			}
			postList.add(str);
		}
		
		return postList;
	}

	public List<String[]> searchPostContent(String keyword) {
		Object[] posts = postViewDAO.searchPostContent(keyword);
		List<String[]> postList = new ArrayList<String[]>();

		for (int index = 0; index < posts.length; index++) {

			PostViewDTO post = (PostViewDTO) posts[index];
			String[] str = new String[7];

			str[0] = post.getPostId();
			str[1] = post.getPostTitle();
			str[2] = post.getPostDate().toString();
			str[3] = post.getPostContent();
			str[4] = post.getNickName();
			str[5] = post.getRelplyCnt() + "";
			boolean anonymous = post.isAnonymous();
			if (anonymous) {
				str[6] = "1";
			} else {
				str[6] = "0";
			}
			postList.add(index, str);
		}
		return postList;
	}

	/*
	 * public boolean updatePost(String postId, String newPostTitle, String
	 * newPostContent, boolean newAnonymous) {
	 * 
	 * }
	 * 
	 * public List<String[]> searchPost(String keyword) {
	 * 
	 * }
	 * 
	 * public List<String[]> searchPostTitle(String keyword) {
	 * 
	 * }
	 * 
	 * public List<String[]> searchPostContent(String keyword) {
	 * 
	 * }
	 */
	/*
	 * public String[] getPostDetail(String postId) {
	 * 
	 * }
	 */
}
