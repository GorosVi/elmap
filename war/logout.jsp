<%@ page import="java.util.logging.Logger" %>
<%	Logger log = Logger.getLogger(this.getClass().getName());
	if (session != null){
		log.info("User "+session.getAttribute("username")+" with userid = "+session.getAttribute("userid")+" and datastore key = "+session.getAttribute("userkey")+" was logged out.");
		session.invalidate();
	}
	//UserService userService = UserServiceFactory.getUserService();
	//response.sendRedirect(userService.createLogoutURL("/login.jsp"));
	response.sendRedirect("/login.jsp");
%>