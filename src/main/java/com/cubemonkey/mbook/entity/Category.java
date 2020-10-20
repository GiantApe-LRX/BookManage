package com.cubemonkey.mbook.entity;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

/**
 * 描述书籍的分类
* @Description
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月13日下午4:28:21
*
 */
public class Category {
	private Integer id;
	private String name;
	public Category() {
		super();
	}
	public Category(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
}
