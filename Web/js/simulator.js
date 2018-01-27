var canvas = document.getElementById("simulationCanvas");
var ctx = canvas.getContext("2d");

var stopButton = document.getElementById("stop");
ctx.canvas.width = window.innerWidth;
ctx.canvas.height = window.innerHeight - stopButton.offsetHeight;

var simulationId;
var tickCount = 0;
var zoom = 2.5; //pixels per metre

var courseRectangles = [];
var courseArcs = [];
var vehicleObjects = [];
var hUDObjects = [];

var accelerating = false;
var braking = false;
var left = false;
var right = false;

var acceleratorPedalDepthVariable = 0;
var brakePedalDepthVariable = 0;
var steeringAngle = 0;
var gearNumber = 1;
var frontSlip = false;
var rearSlip = false;

var lastGearChange = 0;
var lastSteeringWheelUpdate = 0;

var localCarNumber = localStorage.getItem("localCarNumber");
setHostIP(localStorage.getItem("hostIP"));

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
var computerCars = localStorage.getItem("computerCars");

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
	}, trackNumber, getList());
}

function getList() {
	var carList = [];
	for (i = 0; i < carCount; i++) {
		var car = new Object();
		car.vehicleType = "CAR";
		if (computerCars[1 + (2 * i)] == "1") {
			car.isComputer = true;
		} else {
			car.isComputer = false;
		}
		carList.push(car);
	}
	console.log(carList);
	return carList;
}

function fetch() {
	doFetch(function (response) {
		populate(response);
	}, simulationId);
}

function fetchExample() {
	doFetchExample(function (response) {
		populate(response);
		draw();
	});
}

function sendUpdate() {
	if (localCarNumber != -1) {
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

		if (tickCount - lastSteeringWheelUpdate > 4) {
			if (left) {
				if (steeringAngle > -Math.PI / 6) {
					steeringAngle -= Math.PI / 32;
				}
			} else if (right) {
				if (steeringAngle < Math.PI / 6) {
					steeringAngle += Math.PI / 32;
				}
			} else {
				if (steeringAngle < 0) {
					steeringAngle += Math.PI / 32;
				} else if (steeringAngle > 0) {
					steeringAngle -= Math.PI / 32;
				}
			}
		}
		var vehicleUpdateDto = new Object();
		vehicleUpdateDto.id = localCarNumber;
		vehicleUpdateDto.acceleratorPedalDepth = acceleratorPedalDepthVariable;
		vehicleUpdateDto.brakePedalDepth = brakePedalDepthVariable;
		vehicleUpdateDto.steeringWheelOrientation = steeringAngle;
		vehicleUpdateDto.gear = gearNumber;
		vehicleUpdateDto.frontSlip = frontSlip;
		vehicleUpdateDto.rearSlip = rearSlip;

		doUpdateVehicle(vehicleUpdateDto, simulationId, localCarNumber);
	}
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

	if (keys[32]) {
		frontSlip = true;
		rearSlip = true;
	} else {
		frontSlip = false;
		rearSlip = false;
	}
}

function tick() {
	tickCount++;
	if (tickCount % 3) {
		doUpdateTick();
	}
	fetch();
	updateKeys();
	draw(accelerating, braking, left, right, acceleratorPedalDepthVariable, brakePedalDepthVariable, steeringAngle, gearNumber, zoom);
}

function doUpdateTick() {
	sendUpdate();
}

function beginTick() {
	document.title = "Simulation number: " + simulationId;
	playMusic(localStorage.getItem("sound"));
	setInterval(tick, 30);
}
//CreateSimulationByOptions
newSimulationByOptions();

setTimeout(beginTick, 1000);
