package com.cubemonkey.mbook.service;

import java.util.List;

import com.cubemonkey.mbook.entity.User;
import com.cubemonkey.mbook.utils.XmlDataSource;
/**
* @Description 用户服务类
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月13日下午1:49:38
*
 */
public class UserServiceImpl {
	/**
	 * 判断是否登录成功
	 * @param listUser 所有的用户信息
	 * @param user 待验证的用户信息
	 * @return 若验证通过返回1，反之返回0
	 */
	public int login(List<User> listUser, User user) {
		for(User check: listUser) {
			if (check.getUsername().equals(user.getUsername())
				&&check.getPassword().equals(user.getPassword())) {
				return 1;
			}
		}
		return 0;
	}
}
