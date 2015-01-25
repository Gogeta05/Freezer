var geocoder = new google.maps.Geocoder();
var tracker;

//request position once
function getPosition() {
	navigator.geolocation.getCurrentPosition(getPostal, errorHandler);
}
//request position in intervals
function start() {
	tracker = navigator.geolocation.watchPosition(getPostal, errorHandler);
}
//stop the requesting in intervals
function stop() {
	navigator.geolocation.clearWatch(tracker);
}

function errorHandler(error) {
	switch(error.code) {
		case error.PERMISSION_DENIED:
			alert("User denied the request for Geolocation.");
			break;
		case error.POSITION_UNAVAILABLE:
			alert("Location information is unavailable.");
			break;
		case error.TIMEOUT:
			alert("The request to get user location timed out.");
			break;
		case error.UNKNOWN_ERROR:
			//try again (could end in endless loop, needs some savior)
			getPosition();
			break;
	}
}

function getPostal(position) {
	var lat = position.coords.latitude;
	var lng = position.coords.longitude;
	var subsequent = 0;
	var postal = "";
	var latlng = new google.maps.LatLng(lat, lng);
	geocoder.geocode({'latLng': latlng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			if (results[0]) {
				
				var location = results[0].formatted_address;
				//location = location.replace(/\s/g, "");
				
				//extract the postal code
				for (var x = 0; x < location.length; x++) {
					var c = location.charAt(x);
					if (c >= '0' && c <= '9') {
						subsequent += 1;
						postal += c;
					} else if ( (c == ',' || c == ' ') && subsequent == 4){
						break;
					} else {
						subsequent = 0;
						postal = "";
					}
				}
				
				//Do something with the postal code
				document.getElementById("locationInfo").value=parseInt(postal);
				
			} else {
				alert('No results found');
			}	
			
		//try again (could end in endless loop, needs some savior) if unknown_error
		} else {
			getPosition();
		}
	});
}

window.onload(getPosition());