package com.dev.elmap;

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
 
/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String userID = "admin";
    private final String password = "admin";
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
        // get request parameters for userID and password
        String user = request.getParameter("user");
        String pwd = request.getParameter("password");
         
        if(userID.equals(user) && password.equals(pwd)){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("rank", "admin");
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
            response.sendRedirect("/index.jsp");
        }else{
        	response.sendRedirect("/login.jsp");
        }
 
    }
 
}