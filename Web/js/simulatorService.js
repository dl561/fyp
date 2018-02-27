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

function initialPopulate(json) {
  var obj = JSON.parse(json);
  numberOfLaps = obj.numberOfLaps;
  trackNumber = obj.trackNumber + 1;
	console.log("numberOfLaps: " + numberOfLaps);
	console.log("trackNumber: " + trackNumber);
}

function startSimulation() {
  console.log("Starting simulation");
  doStartSimulation(function(response) {
    //Add something here!
  }, simulationId);
}

function stopSimulation() {
  console.log("Stopping simulation");
  doStopSimulation(function(response) {
    //Add something here!
  }, simulationId);
}

function resetSimulation() {
  console.log("Resetting simulation");
  doResetSimulation(function(response) {
    console.log("Reset simulation " + response.id);
  }, simulationId);
}

function newSimulation() {
  console.log("Getting new simulation");
  doNewSimulation(function(response) {
    var responseObj = JSON.parse(response);
    simulationId = responseObj.id;
  });
}

function newSimulationByOptions(name, trackNumber, numberOfLaps, carCount, computerCars, callBackFunction) {
  console.log("Creating a new simulation");
  var carList = getList(carCount, computerCars);
  doNewSimulationByOptions(function(response) {
    var responseObj = JSON.parse(response);
    simulationId = responseObj.id;
  }, name, trackNumber, carList, numberOfLaps);
  callBackFunction();
}

function getList(carCount, computerCars) {
  var carList = [];
  for (i = 0; i < carCount; i++) {
    var car = new Object();
    car.vehicleType = "CAR";
    if (computerCars[i] == 1) {
      car.computer = true;
    } else {
      car.computer = false;
    }
    carList.push(car);
  }
  return carList;
}

function fetch() {
  doFetch(function(response) {
    populate(response);
  }, simulationId);
}

function initialFetch(callBackFunction) {
  doFetch(function(response) {
    initialPopulate(response);
    callBackFunction(getComputerCarsFromSimulationJSON(response));
  }, simulationId);
}

function fetchExample() {
  doFetchExample(function(response) {
    populate(response);
    draw();
  });
}

function getComputerCarsFromSimulationJSON(response) {
  var obj = JSON.parse(response);
  var computerCars = [];
  obj.vehicles.forEach(function(vehicle) {
    computerCars.push(vehicle.computer);
  });
  return computerCars;
}
