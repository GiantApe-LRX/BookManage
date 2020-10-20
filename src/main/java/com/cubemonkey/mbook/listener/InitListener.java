package com.cubemonkey.mbook.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cubemonkey.mbook.entity.User;
import com.cubemonkey.mbook.utils.XmlDataSource;

public class InitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		List<User> users = XmlDataSource.getUsers();
		ServletContext servletContext = sce.getServletContext();
		servletContext.setAttribute("users", users);
		System.out.println(users);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
