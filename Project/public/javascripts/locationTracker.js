		var geocoder;
		var map;
		var infowindow = new google.maps.InfoWindow();
		var marker;
		var tracker;

		function initialize() {
		  geocoder = new google.maps.Geocoder();
		  var latlng = new google.maps.LatLng(47.269258 ,11.4040792	);
		  var mapOptions = {
			zoom: 8,
			center: latlng,
			mapTypeId: 'roadmap'
		  }
		  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
		}

		function trackLocation() {
			tracker = navigator.geolocation.watchPosition(codeLatLng);
		}
			
		function codeLatLng(position) {
		  var lat = position.coords.latitude;
		  var lng = position.coords.longitude;
		  var latlng = new google.maps.LatLng(lat, lng);
		  geocoder.geocode({'latLng': latlng}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
			  if (results[0]) {
				map.setZoom(11);
				marker = new google.maps.Marker({
					position: latlng,
					map: map
				});
				var location = results[0].formatted_address;
				infowindow.setContent(location);
				infowindow.open(map, marker);
				document.getElementById("locationInfo").innerHTML=location;
			  } else {
				alert('No results found');
			  }
			} else {
			  alert('Geocoder failed due to: ' + status);
			}
		  });
		}

		google.maps.event.addDomListener(window, 'load', initialize);

		window.onload(trackLocation());