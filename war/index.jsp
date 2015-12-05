<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="/favicon.ico">

    <title>ElMap</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body >
		<div style="display:block">
			<nav class="navbar navbar-default">
			  <div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
				  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				  </button>
				  <a class="navbar-brand">ElMap</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				  <ul class="nav navbar-nav">
					<li class="active"><a>Главная</a></li>
					<li><a href="trees.jsp">Список зарегистрированных деревьев</a></li>
					<!-- <li><a href="#" data-toggle="modal" data-target="#myModal">Новое дерево</a></li> -->
				  </ul>
				  <ul class="nav navbar-nav navbar-right">
				 	<li><a>Привет, <%= session.getAttribute("user") %></a></li>
				 	<li><a href="logout.jsp">Выйти</a></li>
				  </ul>
				</div><!-- /.navbar-collapse -->
			  </div><!-- /.container-fluid -->
			</nav>
		</div>

	<div id="map" class="container-fluid">

	</div>

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
			<div class="modal-content">
			  <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Регистрация нового дерева</h4>
			  </div>
			  <div class="modal-body">
				<form>
					<div class="form-group">
						<label for="exampleInputEmail1">Тип дерева</label>
						<select class="form-control">
						  <option>Дуб</option>
						  <option>Ясень</option>
						  <option>Ель</option>
						</select>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Радиус</label>
						<input type="number" class="form-control" id="" placeholder="Введите радиус в метрах">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Статус</label>
						<select class="form-control">
						  <option>Здоровое</option>
						  <option>Зараженное</option>
						</select>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Степень</label>
						<input type="number" class="form-control" id="" placeholder="Введите значение в процентах">
					</div>

				</form>
			  </div>
			  <div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
				<button type="button" class="btn btn-primary">Сохранить изменения</button>
			  </div>
			</div>
		  </div>
		</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="http://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script>


			ymaps.ready(init);
var myMap;

function init () {
    myMap = new ymaps.Map("map", {
        center: [59.9144, 30.3152], // СПБ
        zoom: 11
    }, {
        balloonMaxWidth: 200,
        searchControlProvider: 'yandex#search'
    });

    // Обработка события, возникающего при щелчке
    // левой кнопкой мыши в любой точке карты.
    // При возникновении такого события откроем балун.
    myMap.events.add('click', function (e) {
        if (!myMap.balloon.isOpen()) {
            var coords = e.get('coords');
            myMap.balloon.open(coords, {
                contentHeader:'Событие!',
                contentBody:
                    '<p>Координаты дерева: ' + [
                    coords[0].toPrecision(6),
                    coords[1].toPrecision(6)
                    ].join(', ') + '</p>',
                contentFooter:'<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">Новое дерево</button>'
            });
        }
        else {
            myMap.balloon.close();
        }
    });

    // Обработка события, возникающего при щелчке
    // правой кнопки мыши в любой точке карты.
    // При возникновении такого события покажем всплывающую подсказку
    // в точке щелчка.
    myMap.events.add('contextmenu', function (e) {
        myMap.hint.open(e.get('coords'), 'Кто-то щелкнул правой кнопкой');
    });

    // Скрываем хинт при открытии балуна.
    myMap.events.add('balloonopen', function (e) {
        myMap.hint.close();
    });
}


	</script>
  </body>
 </html>