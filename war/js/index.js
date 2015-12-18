ymaps.ready(init);
var myMap;
var latitude, longitude;
function init () {

	$(window).resize(function() {
		$("#map").css("height", $(window).height()-52);
		});
	
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
						latitude = coords[0].toPrecision(6);
						longitude = coords[1].toPrecision(6);
						
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
		$.get("/gettrees", function(response){
			 if(response.success){
				 $(".tableTrees tbody").html("");
				 response.trees.forEach(function(item, i, arr) {
					 myMap.geoObjects.add(new ymaps.Placemark([item.latitude, item.longitude], {
				            balloonContent: item.type + ' <strong>'+item.status+' '+ item.power + '% </strong>'
				        }, {
				            preset: 'islands#dotIcon',
				            iconColor: '#3b5998'
				        }));					 
					 /* var row = "<tr><td>"+(i+1)+"</td><td>"+ 
					  item.latitude +" "+ item.longitude + "</td><td>"+
					  item.type + "</td><td>"+
					  item.radius + "</td><td>" + 
					  item.status + "</td><td>" + 
					  item.power + " %</td></tr>";
					  $(".tableTrees tbody").append(row);	*/					  
				 }); 
			 }		            
	        }, 'json');	
		$("#addTree").click(function(){
			$.post( "/addtree", {'latitude': latitude,
								 'longitude': longitude, 
								 'type': $("#treeType").val(),
								 'radius': $("#radius").val(),
								 'status': $("#status").val(),
								 'power': $("#power").val()}, function( response ) {
	            if(response.success) {
	                alert("Запись добавлена");
	                /*myMap.geoObjects.add(new ymaps.Placemark([latitude, longitude], {
			            balloonContent: item.type + ' <strong>'+$("#status").val()+' '+ $("#power").val() + '% </strong>'
			        }, {
			            preset: 'islands#dotIcon',
			            iconColor: '#3b5998'
			        }));*/	
	                $('#myModal').modal('hide');
	            }

	        }, 'json');
		});	
		
		
		
}
$(document).ready(function(){
	$('#status').change(function(){
		if($(this).val()=="Без признаков поражения"){
			$("#powerArea").css( "display", "none" );
		}else{
			$("#powerArea").css( "display", "block" );
		}
			
	});
});

