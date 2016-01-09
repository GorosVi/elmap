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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.*;


	@SuppressWarnings("serial")
	public class UserWebApi extends HttpServlet {
		private static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		private static final Logger log = Logger.getLogger(UserWebApi.class.getName());

		public void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {

				
		}
	}
