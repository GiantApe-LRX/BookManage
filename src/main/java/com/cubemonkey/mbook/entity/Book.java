package com.cubemonkey.mbook.entity;

import java.math.BigDecimal;


/**
* @Description 书籍信息类
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月13日下午4:19:17
*
 */
public class Book {	
	private Integer id;
	private String name;
	private Integer categoryId;
	private BigDecimal price;
	private String picture;
	private String remarks;
	
	
	
	public Book() {
		super();
	}



	public Book(Integer id, String name, Integer categoryId, BigDecimal price, String picture, String remarks) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.price = price;
		this.picture = picture;
		this.remarks = remarks;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}



	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}



	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}



	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}



	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}



	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", price=" + price + ", picture="
				+ picture + ", remarks=" + remarks + "]";
	}
	
	
	
}
