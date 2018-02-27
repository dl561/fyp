var canvas = document.getElementById("simulationCanvas");
var ctx = canvas.getContext("2d");

var stopButton = document.getElementById("stop");
ctx.canvas.width = window.innerWidth;
ctx.canvas.height = window.innerHeight - stopButton.offsetHeight;

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

var hostIp = localStorage.getItem("hostIp", hostIp);
var simulationId = localStorage.getItem("simulationId", simulationId);
var localCarNumber = localStorage.getItem("localCarNumber", localCarNumber);
var audio = localStorage.getItem("audio", audio);

var isLocalDrivable

setHostIP(hostIp);

initImages();

function sendUpdate() {
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

function updateVehicleValues() {
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
}

function tick() {
  tickCount++;
  if (isLocalDrivable) {
    updateKeys();
    if (tickCount % 3) {
      updateTick();
    }
  }
  fetch();
  draw(zoom);
}

function updateTick() {
  updateVehicleValues();
  sendUpdate();
}

function beginTick() {
  document.title = "Simulation number: " + simulationId;
  initialFetch(function(computerCars) {
    isLocalDrivable = localCarNumber != -1 && computerCars[localCarNumber] == 0;
  });
  if (localCarNumber == -1) {
    zoom = 1;
  }
  playMusic(localStorage.getItem("sound"));
  setInterval(tick, 300);
}

setTimeout(beginTick, 1000);
