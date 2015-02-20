package com.ishare.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ishare.service.impl.UserServiceImpl;
import com.ishare.utils.MD5;

public class UserController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String query = request.getParameter("query");
		if(query != null){
			String pkMember = request.getParameter("pkMember");
			int totalSize = UserServiceImpl.getInstance().queryMinePicCount(pkMember);
			int page = request.getParameter("page") != null ? Integer.parseInt( request.getParameter("page")) : 1;
			int number = 12;
			int totalPage = totalSize / number;
			if(totalSize % number != 0){
				totalPage = totalPage + 1;
			}
			List<Map<String,Object>> resList = UserServiceImpl.getInstance().queryMinePic(pkMember, page, number);
			request.setAttribute("pkMember", pkMember);
			request.setAttribute("totalSize", totalSize);
			request.setAttribute("page", page);
			request.setAttribute("number", number);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("resList", resList);
			request.getRequestDispatcher("picShare.jsp").forward(request, response);
		}else {
			String userName = request.getParameter("account");
			String passWord = request.getParameter("password");
			if(userName == null || passWord == null || userName.trim().equals("") || passWord.trim().equals("")){
				request.setAttribute("error", "用户名密码不能为空!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else {
				passWord = MD5.MD5(passWord);
				
				Map<String,Object> userMap = UserServiceImpl.getInstance().login(userName, passWord);
				if(userMap != null){
					String pkMember = userMap.get("PK_MEMBER").toString();
					int totalSize = UserServiceImpl.getInstance().queryMinePicCount(null);
					int page = 1;
					int number = 12;
					int totalPage = totalSize / number;
					if(totalSize % number != 0){
						totalPage = totalPage + 1;
					}
					List<Map<String,Object>> resList = UserServiceImpl.getInstance().queryMinePic(null, page, number);
					request.setAttribute("pkMember", pkMember);
					request.setAttribute("totalSize", totalSize);
					request.setAttribute("page", page);
					request.setAttribute("number", number);
					request.setAttribute("totalPage", totalPage);
					request.setAttribute("resList", resList);
					request.getRequestDispatcher("picShare.jsp").forward(request, response);
				}else {
					request.setAttribute("error", "用户名或是密码错误!");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			}
		}
		
	}

}
