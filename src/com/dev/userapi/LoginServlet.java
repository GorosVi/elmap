package com.dev.userapi;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

	@SuppressWarnings("serial")
	public class LoginServlet extends HttpServlet {
		
		private static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		private static final Logger log = Logger.getLogger(LoginServlet.class.getName());
		private static UserService userService = UserServiceFactory.getUserService();
		
		public static boolean checkSession(HttpSession cs){
			if (cs == null || cs.getAttribute("userid") == null || userService == null)
				return false;
			User user = userService.getCurrentUser();
			if (user == null || user.getUserId() == null)
				return false;
			return user.getUserId().equals(cs.getAttribute("userid"));
		}
		
		public void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
			User user = userService.getCurrentUser();
			if ((user != null)&&(user.getUserId() != null)) {
				Entity savedUser = UserApi.getUserByID(user.getUserId());
				if (savedUser != null){
					savedUser.setProperty("lastlogin", new Date());
					log.info("User record updated: "+savedUser.getProperty("email")+" with userid = "+savedUser.getProperty("userid")+" and datastore key = "+savedUser.getKey()+" using LastLogin "+savedUser.getProperty("lastlogin"));
					ds.put(savedUser);
				} else {
					savedUser = UserApi.createUser(user.getUserId(), user.getNickname(), user.getEmail());
				}
				//setting session to expiry in 100 hours
				HttpSession session = req.getSession();
				session.setMaxInactiveInterval(100*60*60);
				session.setAttribute("username", savedUser.getProperty("name"));
				session.setAttribute("userkey", savedUser.getKey());				
				session.setAttribute("userid", savedUser.getProperty("userid"));
				
				resp.sendRedirect("/index.jsp");
			} else {
				resp.sendRedirect("/login.jsp");
			}
		}
	}
