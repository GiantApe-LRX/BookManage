package com.cubemonkey.mbook.service;

import java.util.ArrayList;
import java.util.List;

import com.cubemonkey.mbook.entity.Book;
import com.cubemonkey.mbook.entity.Category;
import com.cubemonkey.mbook.utils.XmlDataSource;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
* @Description 书籍信息的服务类
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月15日下午6:52:24
*
 */
public class BookServiceImpl {
	/**
	 * 获取书籍列表
	 * @return List<Book> 书籍列表 
	 */
	public static List<Book> getBooks(){
		return XmlDataSource.getBooks();
	}
	/**
	 * 添加书籍信息
	 * @param book 待添加的书籍
	 */
	public void addBook(Book book) {
		XmlDataSource.appendBook(book);
	}
	/**
	 * 删除书籍信息
	 * @param bookId 待删除的书籍id
	 */
	public void updateBook(Book book, String isPicModified) {
		Book oldBook = getBooksById(book.getId().toString());
		oldBook.setName(book.getName());
		oldBook.setCategoryId(book.getCategoryId());
		oldBook.setPrice(book.getPrice());
		oldBook.setRemarks(book.getRemarks());
		if ("1".equals(isPicModified)) {
			oldBook.setPicture(book.getPicture());
		}
		XmlDataSource.update(oldBook);
	}
	/**
	 * 更新书籍信息
	 * @param book 待更新的书籍信息
	 */
	public void deleteBook(String bookId) {
		XmlDataSource.deleteBook(bookId);
	}
	
	/**
	 * 根据书籍id查询书籍
	 * @param bookID 待查询的书籍id
	 * @return Book 指定的书籍信息
	 */
	public Book getBooksById(String bookID) {
		int id = Integer.parseInt(bookID);
		List<Book> books = getBooks();
		for(Book book: books) {
			if (book.getId() == id) {
				return book;
			}
		}
		return null;
	}
	
	/**
	 * 根据书籍分类查询书籍信息
	 * @param categoryName 书籍分类的类名
	 * @return List<Book> 书籍信息
	 */
	public List<Book> getBooksByCategoryName(String categoryName) {
		List<Book> categoryBook = new ArrayList<Book>();
		List<Book> books = getBooks();
		List<Category> categories = XmlDataSource.getCategories();
		categoryName = categoryName.trim();
		Integer categoryId = null;
		if (categoryName == null || "".equals(categoryName)) {
			return books;
		}
		for(Category category:categories) {
			if (categoryName.equals(category.getName())) {
				categoryId = category.getId();
			}
		}
		if (categoryId == null) {
			throw new RuntimeException("未找到该分类信息");
		}
				for(Book book:books) {
					if (book.getCategoryId() == categoryId) {
						categoryBook.add(book);
					}
				}
		return categoryBook;
	}
}
