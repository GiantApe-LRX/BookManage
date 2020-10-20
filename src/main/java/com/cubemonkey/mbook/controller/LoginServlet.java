package com.cubemonkey.mbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import com.cubemonkey.mbook.entity.User;
import com.cubemonkey.mbook.service.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserServiceImpl userServiceImpl = new UserServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		response.setContentType("text/html; charset=utf-8");
		if (user != null && user != "") {
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);			
		}else {
			request.getRequestDispatcher("/book?action=list").forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = new User(username, password);
		ServletContext servletContext = req.getServletContext();
		List<User> listUser = (List<User>) servletContext.getAttribute("users");
		resp.setContentType("text/html; charset=utf-8");
		if (userServiceImpl.login(listUser, user) == 1) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user.getUsername());
			resp.getWriter().println("{\"login\":\"success\"}");
			System.out.println("登陆成功");
		}else {
			resp.getWriter().println("{\"login\":\"fail\"}");
			System.out.println("登陆失败");
		}
		
	}
}
