package com.cubemonkey.mbook.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.cubemonkey.mbook.entity.Book;
import com.cubemonkey.mbook.entity.Category;
import com.cubemonkey.mbook.service.BookServiceImpl;
import com.cubemonkey.mbook.utils.XmlDataSource;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/book")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookServiceImpl bookServiceImpl = new BookServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		response.setContentType("text/html; charset=utf-8");
		List<Category> categories = XmlDataSource.getCategories();
		request.setAttribute("categories", categories);
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
	 * 显示书籍列表
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> books = bookServiceImpl.getBooks();
		request.setAttribute("books", books);
		request.getRequestDispatcher("/WEB-INF/jsp/bookList.jsp").forward(request, response);;
	}
	
	/**
	 * 显示书籍添加信息页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void appendPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/jsp/addBook.jsp").forward(request, response);;
	}
	
	/**
	 * 显示修改书籍页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void updatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = bookServiceImpl.getBooksById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/jsp/updateBook.jsp").forward(request, response);;
	}
	
	/**
	 * 添加书籍信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		/**
		 * FileItemFactory 用于将前端表单的数据转化为一个个FileItem对象
		 * ServletFileUpload 则是为FileUpload组件提供Java Web的HTTP请求解析
		 */
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		Book newBook = new Book();
		try {
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);
			for(FileItem fileItem: fileItems ) {
				//普通表单项
				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName();
					switch(fieldName) {
						case "bookId": 
							newBook.setId(Integer.parseInt(fileItem.getString("UTF-8")));
							break;
						case "bookName":
							newBook.setName(fileItem.getString("UTF-8"));
							break;
						case "categoryId":
							newBook.setCategoryId(Integer.parseInt(fileItem.getString("UTF-8")));
							break;
						case "bookPrice":
							newBook.setPrice(new BigDecimal(fileItem.getString("UTF-8")));
							break;
						case "remarks":
							newBook.setRemarks(fileItem.getString("UTF-8"));
							break;
					}
				}else {
					//获取文件路径
					String path = request.getServletContext().getRealPath("/upload");
					//设置文件名
					String fileName = UUID.randomUUID().toString();
					//设置文件后缀
					String suffix = fileItem.getName().substring(fileItem.getName().lastIndexOf("."), fileItem.getName().length());
					fileItem.write(new File(path, fileName+suffix));
					newBook.setPicture("/upload/" + fileName + suffix);
				}
			}
			bookServiceImpl.addBook(newBook);
			response.sendRedirect("/book?action=list");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 删除书籍信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		try {			
			bookServiceImpl.deleteBook(bookId);
			response.getWriter().println("{\"result\":\"ok\"}");
		} catch (Exception e) {
			response.getWriter().println("{\"result\":\""+e.getMessage()+"\"}");
			// TODO: handle exception
		}
	}
	
	/**
	 * 更改数据信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		/**
		 * FileItemFactory 用于将前端表单的数据转化为一个个FileItem对象
		 * ServletFileUpload 则是为FileUpload组件提供Java Web的HTTP请求解析
		 */
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		Book newBook = new Book();
		String isPicModified = "0";
		try {
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);
			for(FileItem fileItem: fileItems ) {
				//普通表单项
				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName();
					switch(fieldName) {
						case "isPicModified":
							isPicModified = fileItem.getString("UTF-8");
							break;
						case "bookId": 
							newBook.setId(Integer.parseInt(fileItem.getString("UTF-8")));
							break;
						case "bookName":
							newBook.setName(fileItem.getString("UTF-8"));
							break;
						case "categoryId":
							newBook.setCategoryId(Integer.parseInt(fileItem.getString("UTF-8")));
							break;
						case "bookPrice":
							newBook.setPrice(new BigDecimal(fileItem.getString("UTF-8")));
							break;
						case "remarks":
							newBook.setRemarks(fileItem.getString("UTF-8"));
							break;
					}
				}else {
					if (fileItem.getName() != null && fileItem.getName().length() > 3) {
						//获取文件路径
						String path = request.getServletContext().getRealPath("/upload");
						//设置文件名
						String fileName = UUID.randomUUID().toString();
						//设置文件后缀
						System.out.println(fileItem.getName());
						String suffix = fileItem.getName().substring(fileItem.getName().lastIndexOf("."), fileItem.getName().length());
						fileItem.write(new File(path, fileName+suffix));
						newBook.setPicture("/upload/" + fileName + suffix);
					}
				}
			}
			bookServiceImpl.updateBook(newBook, isPicModified);
			response.sendRedirect("/book?action=list");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void listByCategoryId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryName = request.getParameter("categoryName");
		List<Book> books = bookServiceImpl.getBooksByCategoryName(categoryName);
		request.setAttribute("books", books);
		System.out.println(JSON.toJSONString(books));
		response.getWriter().println(JSON.toJSONString(books));
		
	}
		
}
