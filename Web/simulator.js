var canvas = document.getElementById("simulationCanvas");
var ctx = canvas.getContext("2d");

var stopButton = document.getElementById("stop");
ctx.canvas.width  = window.innerWidth;
ctx.canvas.height = window.innerHeight - stopButton.offsetHeight;
//TODO: See if you can get the height of the canvas to be a bit smaller

var simulationId;

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
	doStartSimulation(function(response){
		//Add something here!
	});
}

function stopSimulation() {
	console.log("Stopping simulation");
	doStopSimulation(function(response){
		//Add something here!
	});
}

function resetSimulation() {
	console.log("Resetting simulation");
	doResetSimulation(function(response){
		console.log("Reset simulation " + response.id);
	});
}

function newSimulation() {
	console.log("Getting new simulation");
	doNewSimulation(function (response){
		var responseObj = JSON.parse(response);
		simulationId = responseObj.id;
	});
}

function fetch(){
	console.log("Fetching");
	doFetch(function (response){
		populate(response);
		draw();
	}, simulationId);
}

function fetchExample(){
	console.log("Fetching example");
	doFetchExample(function (response){
		populate(response);
		draw();
	});
}

function sendKeysAndFetch() {
	var keys = getKeys();
	resetKeys();
	var accelerating;
	var braking;
	var left;
	var right;
	if(keys[38]){
		accelerating = true;
	}else if(keys[40]){
		braking = true;
	}
	if(keys[37]){
		left = true;
	}else if(keys[39]){
		right = true;
	}
	var vehicleUpdateDto = new Object();
	vehicleUpdateDto.id = 0;
	if(accelerating){
		vehicleUpdateDto.acceleratorPedalDepth = 100;
		vehicleUpdateDto.brakePedalDepth = 0;
	}else if(braking){
		vehicleUpdateDto.acceleratorPedalDepth = 0;
		vehicleUpdateDto.brakePedalDepth = 100;		
	}else{
		vehicleUpdateDto.acceleratorPedalDepth = 0;
		vehicleUpdateDto.brakePedalDepth = 0;
	}
	
	if(left){
		vehicleUpdateDto.steeringWheelOrientation = -360;
	}else if(right){
		vehicleUpdateDto.steeringWheelOrientation = 360;
	}else{
		vehicleUpdateDto.steeringWheelOrientation = 0;
	}
	
	doUpdateVehicle(vehicleUpdateDto, simulationId, 0);
	fetch();
}

newSimulation();
document.title = "Simulation number: " + localStorage.getItem("simulationId");

//setInterval(fetch, 30);
setInterval(sendKeysAndFetch, 3000);