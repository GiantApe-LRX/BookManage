package com.cubemonkey.mbook.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cubemonkey.mbook.entity.Category;
import com.cubemonkey.mbook.service.CategoryServiceImpl;
import com.cubemonkey.mbook.utils.XmlDataSource;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();
	private XmlDataSource xmlDataSource = new XmlDataSource();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		response.setContentType("text/html; charset=utf-8");
		//通过反射获取到method对应的方法，并执行
		try {
			Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * 展示分类信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categories = XmlDataSource.getCategories();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/WEB-INF/jsp/categoryList.jsp").forward(request, response);;
	}
	
	/**
	 * 打开分类添加页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void appendPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/addCategory.jsp").forward(request, response);
	}

	/**
	 * 添加分类信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId = request.getParameter("categoryId");
		String categoryName = request.getParameter("categoryName");
		categoryServiceImpl.addCatgory(categoryId, categoryName);
		response.sendRedirect("/category?action=list");
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId = request.getParameter("categoryId");
		try {
			categoryServiceImpl.deleteCatgory(categoryId);
			response.getWriter().println("{\"result\":\"ok\"}");
		}catch (Exception e) {
			response.getWriter().println("{\"result\":\""+e.getMessage()+"\"}");
		}
	}

}