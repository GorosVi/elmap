<%@ page contentType="text/html; charset=UTF-8" %>
<% if (session.getAttribute("user") != null)
	response.sendRedirect("/index.jsp");
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="shortcut icon" href="/favicon.ico">

		<title>Login to ElMap</title>

		<!-- Bootstrap core CSS -->
		<link href="css/bootstrap.min.css" rel="stylesheet">

		<!-- Custom styles for this template -->
		<link href="css/login.css" rel="stylesheet">

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>

		<div class="container">

			<form class="form-signin" role="form" action="/credcheck.jsp" method="post">
				<h2 class="form-signin-heading">Авторизация</h2>
				<input type="text" class="form-control" name="user" placeholder="Введите логин" required autofocus>
				<input type="password" class="form-control" name="password" placeholder="Введите пароль" required>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
			</form>

		</div> <!-- /container -->
	</body>
</html>