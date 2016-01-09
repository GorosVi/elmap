<%@page import="com.dev.userapi.LoginServlet"%>
<%@page import="com.google.appengine.api.users.UserService, com.google.appengine.api.users.UserServiceFactory" %>

<%	UserService userService = UserServiceFactory.getUserService();
	if (LoginServlet.checkSession(session))
		response.sendRedirect("/index.jsp");
%>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="shortcut icon" href="/favicon.ico">

		<title>ElMap - Авторизация</title>

		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/login.css" rel="stylesheet">

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
		<div class="container">
			<form class="form-signin" role="form" action="/login.jsp" method="get">
				<h2 class="form-signin-heading">Авторизация</h2>
				<h2></h2>
				<a class="btn btn-lg btn-primary btn-block" href="<%out.print(userService.createLoginURL("/login"));%>"> Sign in with Google </a>
			</form>
		</div>
	</body>
</html>