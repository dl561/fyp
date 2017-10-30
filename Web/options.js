function submitFunction(){
	var simulationId = document.getElementById("simulationId").value;
	var vehicleNumber = document.getElementById("vehicleNumber").value;
	var trackNumber
	var buttons = document.getElementById("submit");
	buttons.value = simulationId;
	var newUrl = "web.html";
	localStorage.setItem("simulationId",simulationId);
	window.location.replace(newUrl);
	document.location.href = newUrl;
}