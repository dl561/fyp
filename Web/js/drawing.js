var track1;
var track2;

var car0;
var car1;
var car2;
var car3;
var car4;
var car5;

var arrowKeys;
var upArrow;
var downArrow;
var leftArrow;
var rightArrow;

var emptyAccBar;
var accBar10;
var accBar20;
var accBar30;
var accBar40;
var accBar50;
var accBar60;
var accBar70;
var accBar80;
var accBar90;
var accBar100;

var emptyBrkBar;
var brkBar10;
var brkBar20;
var brkBar30;
var brkBar40;
var brkBar50;
var brkBar60;
var brkBar70;
var brkBar80;
var brkBar90;
var brkBar100;

var steeringWheel;

var gear0;
var gear1;
var gear2;
var gear3;
var gear4;
var gear5;

var speedometer;
var needle;

const STR_WHEEL_X = 1168;
const STR_WHEEL_Y = 10;

const GEAR_X = 1180;
const GEAR_Y = 130;

const ACC_BAR_X = 1168;
const ACC_BAR_Y = 240;

const BRK_BAR_X = 1218;
const BRK_BAR_Y = 240;

const ARR_KEY_X = 1168;
const ARR_KEY_Y = 496;

const SPEEDOMETER_X = 1168;
const SPEEDOMETER_Y = 460;

const NEEDLE_X = 1168;
const NEEDLE_Y = 460;

var zoom = 1;

function initImages() {
	track1 = new Image();
	track1.src = "img/track0.png";
	track2 = new Image();
	track2.src = "img/track1.png";

	car0 = new Image();
	car0.src = "img/car0.png";
	car1 = new Image();
	car1.src = "img/car1.png";
	car2 = new Image();
	car2.src = "img/car2.png";
	car3 = new Image();
	car3.src = "img/car3.png";
	car4 = new Image();
	car4.src = "img/car4.png";
	car5 = new Image();
	car5.src = "img/car5.png";

	arrowKeys = new Image();
	arrowKeys.src = "img/arrowKeys.png";
	upArrow = new Image();
	upArrow.src = "img/upArrow.png";
	downArrow = new Image();
	downArrow.src = "img/downArrow.png";
	leftArrow = new Image();
	leftArrow.src = "img/leftArrow.png";
	rightArrow = new Image();
	rightArrow.src = "img/rightArrow.png";

	emptyAccBar = new Image();
	emptyAccBar.src = "img/emptyAccBar.png";
	accBar10 = new Image();
	accBar10.src = "img/10AccBar.png";
	accBar20 = new Image();
	accBar20.src = "img/20AccBar.png";
	accBar30 = new Image();
	accBar30.src = "img/30AccBar.png";
	accBar40 = new Image();
	accBar40.src = "img/40AccBar.png";
	accBar50 = new Image();
	accBar50.src = "img/50AccBar.png";
	accBar60 = new Image();
	accBar60.src = "img/60AccBar.png";
	accBar70 = new Image();
	accBar70.src = "img/70AccBar.png";
	accBar80 = new Image();
	accBar80.src = "img/80AccBar.png";
	accBar90 = new Image();
	accBar90.src = "img/90AccBar.png";
	accBar100 = new Image();
	accBar100.src = "img/100AccBar.png";

	emptyBrkBar = new Image();
	emptyBrkBar.src = "img/emptyBrkBar.png";
	brkBar10 = new Image();
	brkBar10.src = "img/10BrkBar.png";
	brkBar20 = new Image();
	brkBar20.src = "img/20BrkBar.png";
	brkBar30 = new Image();
	brkBar30.src = "img/30BrkBar.png";
	brkBar40 = new Image();
	brkBar40.src = "img/40BrkBar.png";
	brkBar50 = new Image();
	brkBar50.src = "img/50BrkBar.png";
	brkBar60 = new Image();
	brkBar60.src = "img/60BrkBar.png";
	brkBar70 = new Image();
	brkBar70.src = "img/70BrkBar.png";
	brkBar80 = new Image();
	brkBar80.src = "img/80BrkBar.png";
	brkBar90 = new Image();
	brkBar90.src = "img/90BrkBar.png";
	brkBar100 = new Image();
	brkBar100.src = "img/100BrkBar.png";

	steeringWheel = new Image();
	steeringWheel.src = "img/steeringWheel.png";

	gear0 = new Image();
	gear0.src = "img/gear0.png";
	gear1 = new Image();
	gear1.src = "img/gear1.png";
	gear2 = new Image();
	gear2.src = "img/gear2.png";
	gear3 = new Image();
	gear3.src = "img/gear3.png";
	gear4 = new Image();
	gear4.src = "img/gear4.png";
	gear5 = new Image();
	gear5.src = "img/gear5.png";

	speedometer = new Image();
	speedometer.src = "img/speedometer.png";
	needle = new Image();
	needle.src = "img/speedoDial.png";
}

function draw(accelerating, braking, left, right, accDepth, brkDepth, strAngle, gear, zoom) {
	this.zoom = zoom;
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	drawTrackImage(localStorage.getItem("trackNumber"));
	drawVehicles();
	if (localCarNumber != -1) {
		//Local car should have camera follow and extra HUD items
		//drawArrowKeys(accelerating, braking, left, right);
		drawAccBar(accDepth);
		drawBrkBar(brkDepth);
		drawSteerAngle(strAngle);
		drawGear(gear);
	}

	drawHUD();
}

function drawSpeedometer(vehicle) {
	var speed = vehicle.speed;
	//speed = speed * 3600 / 1609;
	speed *= 10;
	var dialAngle = (-0.75 * Math.PI) + speed / 180 / 1.75 * Math.PI;
	drawNonZoomedImage(SPEEDOMETER_X, SPEEDOMETER_Y, 0, speedometer, 1);
	drawNonZoomedImage(NEEDLE_X, NEEDLE_Y, dialAngle, needle, 1);
}

function drawTrackImage(trackId) {
	var trackX = 0;
	var trackY = 0;

	switch (trackId) {
	case "1":
		drawImage(trackX, trackY, 0, track1, zoom);
		break;
		drawImage(trackX, trackY, 0, track1, zoom);
	case "2":
		break;
	}
}

function drawVehicle(vehicle) {
	var normalisedDirection = vehicle.directionOfTravel + (Math.PI / 2);
	switch (vehicle.vehicleType) {
	case "CAR":
		switch (vehicle.id) {
		case 0:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car0, 1);
			break;
		case 1:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car1, 1);
			break;
		case 2:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car2, 1);
			break;
		case 3:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car3, 1);
			break;
		case 4:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car4, 1);
			break;
		case 5:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car5, 1);
			break;
		}
		break;
	}
	if (vehicle.id == localCarNumber) {
		drawVariables(vehicle);
		drawSpeedometer(vehicle);
	}
}

function drawVariables(vehicle) {
	drawText(0, 35, 0, "angularAcceleration: " + vehicle.angularAcceleration);
	drawText(0, 50, 0, "angularVelocity: " + vehicle.angularVelocity);
	drawText(0, 65, 0, "wr xVelocity: " + vehicle.xvelocity);
	drawText(0, 80, 0, "wr yVelocity: " + vehicle.yvelocity);
	drawText(0, 95, 0, "vr xVelocity: " + vehicle.vehicleReferenceVelocity.x);
	drawText(0, 110, 0, "vr yVelocity: " + vehicle.vehicleReferenceVelocity.y);
	drawText(0, 125, 0, "wr x: " + vehicle.location.x);
	drawText(0, 140, 0, "wr y: " + vehicle.location.y);
	drawText(0, 155, 0, "waypoint: " + vehicle.waypointNumber);
	drawText(0, 170, 0, "lap: " + vehicle.lap);
	drawText(0, 185, 0, "position: " + vehicle.position);
	drawText(0, 200, 0, "speed: " + vehicle.speed);
	drawText(0, 215, 0, "RPM: " + vehicle.rpm);
}

function drawArrowKeys(accelerating, braking, left, right) {
	drawNonZoomedImage(ARR_KEY_X, ARR_KEY_Y, 0, arrowKeys, 1);
	if (accelerating) {
		drawNonZoomedImage(ARR_KEY_X, ARR_KEY_Y, 0, upArrow, 1);
	}
	if (braking) {
		drawNonZoomedImage(ARR_KEY_X, ARR_KEY_Y, 0, downArrow, 1);
	}
	if (left) {
		drawNonZoomedImage(ARR_KEY_X, ARR_KEY_Y, 0, leftArrow, 1);
	}
	if (right) {
		drawNonZoomedImage(ARR_KEY_X, ARR_KEY_Y, 0, rightArrow, 1);
	}
}

function drawAccBar(accDepth) {
	switch (accDepth) {
	case 0:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, emptyAccBar, 1);
		break;
	case 10:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar10, 1);
		break;
	case 20:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar20, 1);
		break;
	case 30:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar30, 1);
		break;
	case 40:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar40, 1);
		break;
	case 50:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar50, 1);
		break;
	case 60:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar60, 1);
		break;
	case 70:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar70, 1);
		break;
	case 80:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar80, 1);
		break;
	case 90:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar90, 1);
		break;
	case 100:
		drawNonZoomedImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar100, 1);
		break;
	}
}

function drawBrkBar(brkDepth) {
	switch (brkDepth) {
	case 0:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, emptyBrkBar, 1);
		break;
	case 10:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar10, 1);
		break;
	case 20:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar20, 1);
		break;
	case 30:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar30, 1);
		break;
	case 40:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar40, 1);
		break;
	case 50:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar50, 1);
		break;
	case 60:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar60, 1);
		break;
	case 70:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar70, 1);
		break;
	case 80:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar80, 1);
		break;
	case 90:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar90, 1);
		break;
	case 100:
		drawNonZoomedImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar100, 1);
		break;
	}
}

function drawSteerAngle(strAngle) {
	drawNonZoomedImage(STR_WHEEL_X, STR_WHEEL_Y, strAngle, steeringWheel, 1);
}

function drawGear(gear) {
	switch (gear) {
	case 0:
		drawNonZoomedImage(GEAR_X, GEAR_Y, 0, gear0, 1);
		break;
	case 1:
		drawNonZoomedImage(GEAR_X, GEAR_Y, 0, gear1, 1);
		break;
	case 2:
		drawNonZoomedImage(GEAR_X, GEAR_Y, 0, gear2, 1);
		break;
	case 3:
		drawNonZoomedImage(GEAR_X, GEAR_Y, 0, gear3, 1);
		break;
	case 4:
		drawNonZoomedImage(GEAR_X, GEAR_Y, 0, gear4, 1);
		break;
	case 5:
		drawNonZoomedImage(GEAR_X, GEAR_Y, 0, gear5, 1);
		break;

	}
}

function drawImage(x, y, radians, img) {
	drawImage(x, y, radians, img, 1);
}

function drawImage(x, y, radians, img, zoomMultiplier) {
	var zoomedX = x * zoom;
	var zoomedY = y * zoom;
	var zoomedLocalX = 0;
	var zoomedLocalY = 0;
	if (localCarNumber != -1) {
		zoomedLocalX = vehicleObjects[localCarNumber].location.x * zoom;
		zoomedLocalY = vehicleObjects[localCarNumber].location.y * zoom;
	}
	var zoomedWidth = window.innerWidth;
	var zoomedHeight = window.innerHeight;
	var translatedX;
	var translatedY;
	if (localCarNumber != -1) {
		translatedX = zoomedX - zoomedLocalX + (zoomedWidth / 2);
		translatedY = zoomedY - zoomedLocalY + (zoomedHeight / 2);
	}
	console.log(x + "," + y + " -> " + translatedX + "," + translatedY + ".");
	drawNonZoomedImage(translatedX, translatedY, radians, img, zoomMultiplier);
}

function drawNonZoomedImage(x, y, radians, img) {
	drawNonZoomedImage(x, y, radians, img, 1);
}

function drawNonZoomedImage(x, y, radians, img, zoomMultiplier) {
	var width = img.width * zoomMultiplier;
	var height = img.height * zoomMultiplier;
	var cx = x + width / 2;
	var cy = y + height / 2;
	var drawX = width / 2 * (-1);
	var drawY = height / 2 * (-1);

	ctx.translate(cx, cy);
	ctx.rotate(radians);
	ctx.drawImage(img, drawX, drawY, width, height);
	ctx.rotate(radians * (-1));
	ctx.translate(cx * (-1), cy * (-1));
}

function drawCourse() {
	courseRectangles.forEach(drawCourseRectangle);
	courseArcs.forEach(drawCourseArc);
}

function drawCourseRectangle(rectangle) {
	drawRectangle(rectangle.x, rectangle.y, rectangle.xSize, rectangle.ySize, rectangle.rotation, rectangle.hexColour);
}

function drawCourseArc(arc) {
	drawArc(arc.x, arc.y, arc.radius, arc.startAngle, arc.endAngle, arc.counterClockwise, arc.rotation, arc.hexColour);
}

function drawVehicles() {
	vehicleObjects.forEach(drawVehicle);
}

function drawVehiclePieces(vehicle) {
	drawRectangle(vehicle.location.x, vehicle.location.y, 20, 10, vehicle.directionOfTravel, "#00ffff");
}

function drawHUD() {}

function rotationTest() {
	var x = 100;
	var y = 100;
	var w = 100;
	var h = 100;
	drawRectangle(x, y, w, h, rotationAmount);
}

function drawRectangle(x, y, width, height, degrees, colour) {
	var normalisedX = x * zoom;
	var normalisedY = y * zoom;
	var radians = degrees * Math.PI / 180;
	var cx = normalisedX + width / 2;
	var cy = normalisedY + height / 2;
	var drawX = width / 2 * (-1);
	var drawY = height / 2 * (-1);

	ctx.translate(cx, cy);
	ctx.rotate(radians);
	ctx.fillStyle = colour;
	ctx.fillRect(drawX, drawY, width, height);
	ctx.rotate(radians * (-1));
	ctx.translate(cx * (-1), cy * (-1));
}

function drawArc(x, y, radius, startAngle, endAngle, counterClockwise, rotation, colour) {
	var normalisedX = x * zoom;
	var normalisedY = y * zoom;
	ctx.beginPath();
	ctx.arc(normalisedX, normalisedY, radius, startAngle, endAngle, counterClockwise);
	ctx.strokeStyle = colour;
	ctx.lineWidth = 60;
	ctx.rotate(rotation * Math.PI / 180);
	ctx.stroke();
	ctx.closePath();
	ctx.restore();
}

function drawText(x, y, rotation, text) {
	ctx.font = "15px Arial";
	ctx.fillText(text, x, y);
}
