var HttpClient = function() {
	this.get = function(url, callBackFunction) {
		console.log("GET to " + url);
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() { 
			if (xhr.readyState == 4 && xhr.status == 200)
				callBackFunction(xhr.responseText);
		}
		xhr.open("GET", url, true);
		xhr.send();
	}
	this.put = function(url, callBackFunction, data) {
		console.log("PUT to " + url);
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
	this.post = function(url, callBackFunction, data) {
		console.log("POST to " + url);
		var json = JSON.stringify(data);
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200)
				callBackFunction(xhr.responseText);
		}
		xhr.open("POST", url, true);
		xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
		xhr.send(json);
	}
}

function doResetSimulation(callBackFunction) {
	var simulationDataDto = new Object();
	var client = new HttpClient();
	client.put('http://localhost:8080/simulation', callBackFunction, simulationDataDto);
}

function doStartSimulation(callBackFunction, simulationId){
	var client = new HttpClient();
	client.get('http://localhost:8080/simulation/' + simulationId + '/start', callBackFunction);
}

function doStopSimulation(callBackFunction){
	var client = new HttpClient();
	client.get('http://localhost:8080/simulation/' + simulationId + '/stop', callBackFunction);
}
	
function doNewSimulation(callBackFunction) {
	var simulationDataDto = new Object();
	var client = new HttpClient();
	client.put('http://localhost:8080/simulation', callBackFunction, simulationDataDto);		
}

function doFetchExample(callBackFunction){
	var client = new HttpClient();
	client.get('http://localhost:8080/example/', callBackFunction);
}

function doFetch(callBackFunction, simulationId) {
	var client = new HttpClient();
	client.get("http://localhost:8080/simulation/" + simulationId, callBackFunction);
}

function doUpdateVehicle(vehicleUpdateDto, simulationId, vehicleId) {
	var client = new HttpClient();
	client.post("http://localhost:8080/simulation/" + simulationId + "/vehicle/" + vehicleId, function(response) {
		console.log("Did post to " + 'http://localhost:8080/simulation/' + simulationId + '/vehicle/' + vehicleId);
	}, vehicleUpdateDto);
}