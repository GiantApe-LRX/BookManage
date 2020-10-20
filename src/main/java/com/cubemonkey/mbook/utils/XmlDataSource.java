package com.cubemonkey.mbook.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.cubemonkey.mbook.entity.Book;
import com.cubemonkey.mbook.entity.Category;
import com.cubemonkey.mbook.entity.User;
import com.sun.javafx.sg.prism.web.NGWebView;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;

/**
* 读取xml中的数据
* @Description
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月13日下午12:49:32
*
 */
public class XmlDataSource {
	private static List<User> userData = new ArrayList<User>();//用于存储用户的信息
	private static String userFile;//用于存储用户信息的文件名
	private static List<Category> categoryData = new ArrayList<Category>();//用于存储书籍分类的信息
	private static String categoryFile;//用于存储书籍分类信息的文件名
	private static List<Book> bookData = new ArrayList<Book>();//用于存储书籍的信息
	private static String bookFile;//用于存储书籍信息的文件名
	
	static {
		userFile = XmlDataSource.class.getResource("/user.xml").getPath();
		userReload();
		categoryFile = XmlDataSource.class.getResource("/category.xml").getPath();
		categoryReload();
		bookFile = XmlDataSource.class.getResource("/book.xml").getPath();
		bookReload();
		System.out.println(bookFile);
	}	

	/**
	 * 初始化用户数据
	 */
	public static void userReload() {
		URLDecoder decoder = new URLDecoder();
		try {
			userFile = decoder.decode(userFile, "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(userFile);
			List<Node> nodes = document.selectNodes("/root/user");
			userData.clear();
			for(Node node: nodes) {
				Element element = (Element)node;
				String username = element.elementText("username");
				String password = element.elementText("password");
				User user = new User(username, password);
				userData.add(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * 获取用户列表
	 * @return List<User> 用户列表
	 */
	public static List<User> getUsers(){
		return userData;
	}
	
	/**
	 * 初始化书籍分类信息
	 */
	public static void categoryReload() {
		URLDecoder decoder = new URLDecoder();
		try {
			categoryFile = decoder.decode(categoryFile, "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(categoryFile);
			List<Node> nodes = document.selectNodes("/root/category");
			categoryData.clear();
			for(Node node: nodes) {
				Element element = (Element) node;
				String id = element.attributeValue("id");
				String name = element.elementText("name");
				Category category = new Category(Integer.parseInt(id), name);
				categoryData.add(category);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取分类列表
	 * @return List<Category> 分类列表
	 */
	public static List<Category> getCategories(){
		return categoryData;
	}
	
	/**
	 * 添加分类
	 * @param category 待添加的分类
	 */
	public static void appendCategory(Category category) {
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(categoryFile);
			Element root = document.getRootElement();
			Element element = root.addElement("category");
			element.addAttribute("id", category.getId().toString());
			element.addElement("name").setText(category.getName());
			writer = new OutputStreamWriter(new FileOutputStream(categoryFile), "UTF-8");
			document.write(writer);
			System.out.println(categoryFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			categoryReload();
		}
	}
	
	/**
	 * 根据id删除指定分类信息
	 * @param categoryId 待删除分类的id
	 */
	public static void deleteCategory(String categoryId) {
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(categoryFile);
			System.out.println(categoryFile);
			List<Node> nodes = document.selectNodes("/root/category[@id="+categoryId+"]");
			if (nodes.size() == 0) {
				throw new RuntimeException("id="+categoryId+"编号分类不存在");
			}
			Element node = (Element) nodes.get(0);
			Element parent = node.getParent();
			parent.remove(node);
			writer = new OutputStreamWriter(new FileOutputStream(categoryFile), "UTF-8");
			document.write(writer);
			System.out.println("删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			categoryReload();
		}
	}
	
	/**
	 * 初始化书籍信息
	 */
	public static void bookReload() {
		URLDecoder decoder = new URLDecoder();
		try {
			bookFile = decoder.decode(bookFile, "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(bookFile);
			Element root = document.getRootElement();
			List<Node> nodes = root.selectNodes("/root/book");
			bookData.clear();
			for(Node node: nodes) {
				Element element = (Element) node;
				String id = element.attributeValue("id");
				String name = element.elementText("name");
				String categoryId = element.elementText("categoryId");
				String price = element.elementText("price");
				String picture = element.elementText("picture");
				String remarks = element.elementText("remarks");
				Book book = new Book(Integer.parseInt(id), name, Integer.parseInt(categoryId), new BigDecimal(price), picture, remarks);
				bookData.add(book);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取书籍信息
	 * @return List<Book> 书籍信息
	 */
	public static List<Book> getBooks(){
		return bookData;
	}
	
	/**
	 * 添加书籍信息
	 * @param book 待添加的书籍信息
	 */
	public static void appendBook(Book book) {
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(bookFile);
			Element root = document.getRootElement();
			Element element = root.addElement("book");
			element.addAttribute("id", book.getId().toString());
			element.addElement("name").setText(book.getName());;
			element.addElement("categoryId").setText(book.getCategoryId().toString());
			element.addElement("price").setText(book.getPrice().toString());
			element.addElement("picture").setText(book.getPicture());
			element.addElement("remarks").setText(book.getRemarks());
			writer = new OutputStreamWriter(new FileOutputStream(bookFile), "UTF-8");
			document.write(writer);
			System.out.println("添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bookReload();
		}
	}
	/**
	 * 根据id删除指定书籍信息
	 * @param categoryId 待删除书籍的id
	 */
	public static void deleteBook(String bookId) {
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(bookFile);
			List<Node> nodes = document.selectNodes("/root/book[@id="+bookId+"]");
			if (nodes.size() == 0) {
				throw new RuntimeException("找不到id为"+bookId+"的书籍");
			}
			Element element = (Element) nodes.get(0);
			Element parent = element.getParent();
			parent.remove(element);
			writer = new OutputStreamWriter(new FileOutputStream(bookFile), "UTF-8");
			document.write(writer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bookReload();
		}
	}
	/**
	 * 更新书籍信息
	 * @param book 待更新的书籍信息
	 */
	public static void update(Book book) {
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(bookFile);
			List<Node> nodes = document.selectNodes("/root/book[@id="+book.getId()+"]");
			if (nodes.size() == 0) {
				throw new RuntimeException("未找到id为"+book.getId()+"的书籍");
			}
			Element element = (Element) nodes.get(0);
			element.element("name").setText(book.getName());
			element.element("categoryId").setText(book.getCategoryId().toString());
			element.element("price").setText(book.getPrice().toString());
			element.element("picture").setText(book.getPicture());
			element.element("remarks").setText(book.getRemarks());
			
			writer = new OutputStreamWriter(new FileOutputStream(bookFile), "UTF-8");
			document.write(writer);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bookReload();
		}
	}
	public static void main(String[] args) {
//		new XmlDataSource();
//		XmlDataSource.appendCategory(new Category(3, "人文类"));
//		System.out.println(XmlDataSource.getCategories());
//		XmlDataSource.deleteCategory("1");
//		System.out.println(Integer.parseInt("001"));
//		Category category = new Category(1, "人文类");
//		appendBook(new Book(2, "人工智能基础", 1, new BigDecimal(30), "/upload/01.jpg", "人工智能的描述"));
//		deleteBook("2");
		update(new Book(2, "数据结构", 1, new BigDecimal(40), "/upload/01.jpg", "数据结构的描述"));
		System.out.println(getBooks());
	}
}

