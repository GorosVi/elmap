package com.dev.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class DeleteTree extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "admin";
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		JSONObject  json = new JSONObject();
		Tree.ds.delete(KeyFactory.createKey("Tree", Long.parseLong(id)));
		try { 
			json.put("success", true);
			json.put("key_id", id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(json.toString());
	
	}

	/*protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


	}*/

}