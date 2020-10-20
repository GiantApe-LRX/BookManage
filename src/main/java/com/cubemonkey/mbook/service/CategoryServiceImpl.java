package com.cubemonkey.mbook.service;

import java.util.List;

import com.cubemonkey.mbook.entity.Category;
import com.cubemonkey.mbook.utils.XmlDataSource;

/**
* @Description 书籍分类信息的服务类
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月13日下午5:42:45
*
 */
public class CategoryServiceImpl {
	
	/**
	 * 添加分类信息
	 * @param categoryId 分类信息的编号
	 * @param categoryName 分类信息的名字
	 */
	public void addCatgory(String categoryId, String categoryName) {
		if (categoryId != null && categoryName != null) {
			Category category = new Category(Integer.parseInt(categoryId), categoryName);
			XmlDataSource.appendCategory(category);
		}
	}
	
	/**
	 * 根据指定id删除分类信息
	 * @param categoryId
	 */
	public void deleteCatgory(String categoryId) {
		XmlDataSource.deleteCategory(categoryId);
	}
}
