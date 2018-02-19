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
	}, simulationId);
}

function stopSimulation() {
	console.log("Stopping simulation");
	doStopSimulation(function (response) {
		//Add something here!
	}, simulationId);
}

function resetSimulation() {
	console.log("Resetting simulation");
	doResetSimulation(function (response) {
		console.log("Reset simulation " + response.id);
	}, simulationId);
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
	}, trackNumber, getList(), numberOfLaps);
}

function getList() {
	var carList = [];
	for (i = 0; i < carCount; i++) {
		var car = new Object();
		car.vehicleType = "CAR";
		if (computerCars[i] == "1") {
			car.computer = true;
		} else {
			car.computer = false;
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