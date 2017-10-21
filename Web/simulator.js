	var canvas = document.getElementById("simulationCanvas");
	var ctx = canvas.getContext("2d");
	
	var stopButton = document.getElementById("stop");
	ctx.canvas.width  = window.innerWidth;
	ctx.canvas.height = window.innerHeight - stopButton.offsetHeight;
	//TODO: See if you can get the height of the canvas to be a bit smaller
	
	var simulationId = localStorage.getItem("simulationId");
	
	var courseRectangles = [];
	var courseArcs = [];
	var vehicleObjects = [];
	var hUDObjects = [];
		
	function populateCourseObjects(course){
		courseRectangles = course.rectangles;
		courseArcs = course.arcs;
		console.log("Rectangle count = " + courseRectangles.length);
		console.log("Arc count = " + courseArcs.length);
	}
	
	function populateVehicles(vehicles){
		vehicleObjects = vehicles;
		console.log("Vehicle count = " + vehicleObjects.length);
	}
	
	function populateHUD(huds){
		hUDObjects = huds;
	}
	
	function populate(json){
		var obj = JSON.parse(json);
		console.log("Simulation ID = " + obj.id);
		console.log("Simulation running state = " + obj.running);
		populateCourseObjects(obj.course);
		populateVehicles(obj.vehicles);
		populateHUD(obj.hud);
	}
	
	function startSimulation() {
		console.log("Starting simulation");
		var client = new HttpClient();
	}
	
	function stopSimulation() {
		console.log("Stopping simulation");
	}
	
	function resetSimulation() {
		console.log("Resetting simulation");
		var simulationDataDto = new Object();
		var client = new HttpClient();
		client.put('http://localhost:8080/simulation', function(response) {
			console.log("did put");
		}, simulationDataDto);
	}
	
	function newSimulation() {
		console.log("Getting new simulation");
		var simulationDataDto = new Object();
		var client = new HttpClient();
		client.put('http://localhost:8080/simulation', function(response) {
			console.log("did put");
			simulationId = response.id;
		}, simulationDataDto);		
	}
	
	function fetchExample(){
		console.log("Fetching example");
		var client = new HttpClient();
		client.get('http://localhost:8080/example/', function(response){
			populate(response);
			draw();
		});
	}
	
	function fetch() {
		console.log("Fetching");
		var client = new HttpClient();
		client.get('http://localhost:8080/simulation/' + simulationId, function(response){
			populate(response);
			draw();
		});
	}
	
	simulationId = newSimulation();
	
	document.title = "Simulation number: " + localStorage.getItem("simulationId");

	setInterval(fetch, 30);