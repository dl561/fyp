var HttpClient = function() {
	this.get = function(url, callBackFunction) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() { 
			if (xhr.readyState == 4 && xhr.status == 200)
				callBackFunction(xhr.responseText);
		}
		xhr.open("GET", url, true);
		xhr.send();
	}
	this.put = function(url, callBackFunction, data) {
		var json = JSON.stringify(data);
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200)
				callBackFunction(xhr.responseText);
		}
		xhr.open("PUT", url, true);
		xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
		xhr.send(json);
	}
}