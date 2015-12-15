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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class GetTrees extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "admin";
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		Query q = new Query("Tree");
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = Tree.ds.prepare(q);
		JSONArray ar = new JSONArray();
		
		for (Entity result : pq.asIterable()) {
			try {
				JSONObject obj = new JSONObject();
				obj.put("latitude", (String) result.getProperty("latitude"));
				obj.put("longitude", (String) result.getProperty("longitude"));
				obj.put("type", (String) result.getProperty("type"));
				obj.put("radius", (String) result.getProperty("radius"));
				obj.put("status", (String) result.getProperty("status"));
				obj.put("power", (String) result.getProperty("power"));
				obj.put("key", result.getKey().getId());
				ar.put(obj);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JSONObject res = new JSONObject();
		try {
			res.put("trees", ar);
			res.put("success", true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.getWriter().write(res.toString());
		
	}
}