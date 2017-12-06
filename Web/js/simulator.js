var canvas = document.getElementById("simulationCanvas");
var ctx = canvas.getContext("2d");

var stopButton = document.getElementById("stop");
ctx.canvas.width = window.innerWidth;
ctx.canvas.height = window.innerHeight - stopButton.offsetHeight;
//TODO: See if you can get the height of the canvas to be a bit smaller

var simulationId;
var tickCount = 0;

var courseRectangles = [];
var courseArcs = [];
var vehicleObjects = [];
var hUDObjects = [];

var accelerating = false;
var braking = false;
var left = false;
var right = false;

//TODO: make a better way of doing this
var acceleratorPedalDepthVariable = 0;
var brakePedalDepthVariable = 0;
var steeringAngle = 0;
var gearNumber = 1;

var lastGearChange = 0;

initImages();

var trackNumber1 = localStorage.getItem("trackNumber1");
var trackNumber2 = localStorage.getItem("trackNumber2");
var trackNumber;
if (trackNumber1) {
	trackNumber = 1;
} else if (trackNumber2) {
	trackNumber = 2;
}
localStorage.setItem("trackNumber", trackNumber);
var carCount = localStorage.getItem("carCount");

function populateCourseObjects(course) {
	courseRectangles = course.rectangles;
	courseArcs = course.arcs;
}

function populateVehicles(vehicles) {
	vehicleObjects = vehicles;
}

function populateHUD(huds) {
	hUDObjects = huds;
}

function populate(json) {
	var obj = JSON.parse(json);
	populateCourseObjects(obj.course);
	populateVehicles(obj.vehicles);
	populateHUD(obj.hud);
}

function startSimulation() {
	console.log("Starting simulation");
	doStartSimulation(function (response) {
		//Add something here!
	});
}

function stopSimulation() {
	console.log("Stopping simulation");
	doStopSimulation(function (response) {
		//Add something here!
	});
}

function resetSimulation() {
	console.log("Resetting simulation");
	doResetSimulation(function (response) {
		console.log("Reset simulation " + response.id);
	});
}

function newSimulation() {
	console.log("Getting new simulation");
	doNewSimulation(function (response) {
		var responseObj = JSON.parse(response);
		simulationId = responseObj.id;
	});
}

function newSimulationByOptions() {
	console.log("Getting new simulation by options");
	console.log("Track Number: " + trackNumber);
	console.log("Car count: " + carCount);
	doNewSimulationByOptions(function (response) {
		var responseObj = JSON.parse(response);
		simulationId = responseObj.id;
	}, trackNumber, getList(carCount));
	//Need to turn carCount into a list of car objects
}

function getList(carCount) {
	var list = [{
			vehicleType: "CAR",
			count: carCount
		}
	];
	return list;
}

function fetch() {
	//console.log("Fetching");
	doFetch(function (response) {
		populate(response);
	}, simulationId);
}

function fetchExample() {
	console.log("Fetching example");
	doFetchExample(function (response) {
		populate(response);
		draw();
	});
}

function sendUpdate() {
	if (accelerating) {
		acceleratorPedalDepthVariable += 10;
	} else {
		acceleratorPedalDepthVariable -= 10;
	}
	if (braking) {
		brakePedalDepthVariable += 10;
	} else {
		brakePedalDepthVariable -= 10;
	}

	if (acceleratorPedalDepthVariable > 100) {
		acceleratorPedalDepthVariable = 100;
	}
	if (acceleratorPedalDepthVariable < 0) {
		acceleratorPedalDepthVariable = 0;
	}
	if (brakePedalDepthVariable > 100) {
		brakePedalDepthVariable = 100;
	}
	if (brakePedalDepthVariable < 0) {
		brakePedalDepthVariable = 0;
	}

	if (left) {
		if (steeringAngle > -Math.PI / 4) {
			steeringAngle -= Math.PI / 32;
		}
	} else if (right) {
		if (steeringAngle < Math.PI / 4) {
			steeringAngle += Math.PI / 32;
		}
	} else {
		if (steeringAngle < 0) {
			steeringAngle += Math.PI / 32;
		} else if (steeringAngle > 0) {
			steeringAngle -= Math.PI / 32;
		}
	}

	var vehicleUpdateDto = new Object();
	vehicleUpdateDto.id = 0;
	vehicleUpdateDto.acceleratorPedalDepth = acceleratorPedalDepthVariable;
	vehicleUpdateDto.brakePedalDepth = brakePedalDepthVariable;
	vehicleUpdateDto.steeringWheelOrientation = steeringAngle;
	vehicleUpdateDto.gear = gearNumber;

	doUpdateVehicle(vehicleUpdateDto, simulationId, 0);
}

function updateKeys() {
	var keys = getKeys();
	if (keys[38]) {
		accelerating = true;
	} else {
		accelerating = false;
	}

	if (keys[40]) {
		braking = true;
	} else {
		braking = false;
	}

	if (keys[37]) {
		left = true;
	} else {
		left = false;
	}

	if (keys[39]) {
		right = true;
	} else {
		right = false;
	}

	if (keys[16]) {
		//gear up with shft
		if (gearNumber < 5 && tickCount - lastGearChange > 6) {
			gearNumber++;
			lastGearChange = tickCount;
		}
	}

	if (keys[17]) {
		//gear down with ctrl
		if (gearNumber > 0 && tickCount - lastGearChange > 6) {
			gearNumber--;
			lastGearChange = tickCount;
		}
	}
}

function tick() {
	tickCount++;
	if (tickCount % 3) {
		doUpdateTick();
	}
	fetch();
	updateKeys();
	draw(accelerating, braking, left, right, acceleratorPedalDepthVariable, brakePedalDepthVariable, steeringAngle, gearNumber);
}

function doUpdateTick() {
	sendUpdate();
}

function beginTick() {
	setInterval(tick, 30);
}
//CreateSimulationByOptions
newSimulationByOptions();
document.title = "Simulation number: " + simulationId;

setTimeout(beginTick, 1000);
