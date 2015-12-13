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

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class AddNewTree extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "admin";
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String type = request.getParameter("type");
		String radius = request.getParameter("radius");
		String status = request.getParameter("status");
		String power = request.getParameter("power");
		boolean flag = Tree.createTree("1", latitude, longitude, type, radius, status, power);
		JSONObject  json = new JSONObject();
		try { 
			json.put("success", true);
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