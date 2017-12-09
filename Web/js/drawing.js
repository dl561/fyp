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

const STR_WHEEL_X = 5840;
const STR_WHEEL_Y = 50;

const GEAR_X = 5900;
const GEAR_Y = 650;

const ACC_BAR_X = 5840;
const ACC_BAR_Y = 1200;

const BRK_BAR_X = 6090;
const BRK_BAR_Y = 1200;

const ARR_KEY_X = 5840;
const ARR_KEY_Y = 2480;

const SPEEDOMETER_X = 5840;
const SPEEDOMETER_Y = 2300;

const NEEDLE_X = 5840;
const NEEDLE_Y = 2300;

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

function draw(accelerating, braking, left, right, accDepth, brkDepth, strAngle, gear) {
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	//drawCourse();
	drawTrackImage(localStorage.getItem("trackNumber"));
	drawVehicles();
	drawHUD();
	//drawArrowKeys(accelerating, braking, left, right);
	drawAccBar(accDepth);
	drawBrkBar(brkDepth);
	drawSteerAngle(strAngle);
	drawGear(gear);
}

function drawSpeedometer(vehicle) {
	var speed = vehicle.speed;
	var dialAngle = (-0.75 * Math.PI) + speed / 180 / 1.75 * Math.PI;
	drawImage(SPEEDOMETER_X, SPEEDOMETER_Y, 0, speedometer);
	drawImage(NEEDLE_X, NEEDLE_Y, dialAngle, needle);
}

function drawTrackImage(trackId) {
	switch (trackId) {
	case "1":
		drawImage(0, 0, 0, track1);
		break;
	case "2":
		drawImage(0, 0, 0, track1);
		break;
	}
}

function drawVehicle(vehicle) {
	var normalisedDirection = vehicle.directionOfTravel + (Math.PI / 2);
	switch (vehicle.vehicleType) {
	case "CAR":
		switch (vehicle.id) {
		case 0:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car0);
			break;
		case 1:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car1);
			break;
		case 2:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car2);
			break;
		case 3:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car3);
			break;
		case 4:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car4);
			break;
		case 5:
			drawImage(vehicle.location.x, vehicle.location.y, normalisedDirection, car5);
			break;
		}
		break;
	}
	if(vehicle.id==localCarNumber){
		drawVariables(vehicle);
		drawSpeedometer(vehicle);
	}
}

function drawVariables(vehicle) {
	drawText(0, 50, 0, "angularVelocity: " + vehicle.angularVelocity);
	drawText(0, 65, 0, "wr xVelocity: " + vehicle.xvelocity);
	drawText(0, 80, 0, "wr yVelocity: " + vehicle.yvelocity);
	drawText(0, 95, 0, "vr xVelocity: " + vehicle.vehicleReferenceVelocity.x);
	drawText(0, 110, 0, "vr yVelocity: " + vehicle.vehicleReferenceVelocity.y);
}

function drawArrowKeys(accelerating, braking, left, right) {
	drawImage(ARR_KEY_X, ARR_KEY_Y, 0, arrowKeys);
	if (accelerating) {
		drawImage(ARR_KEY_X, ARR_KEY_Y, 0, upArrow);
	}
	if (braking) {
		drawImage(ARR_KEY_X, ARR_KEY_Y, 0, downArrow);
	}
	if (left) {
		drawImage(ARR_KEY_X, ARR_KEY_Y, 0, leftArrow);
	}
	if (right) {
		drawImage(ARR_KEY_X, ARR_KEY_Y, 0, rightArrow);
	}
}

function drawAccBar(accDepth) {
	switch (accDepth) {
	case 0:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, emptyAccBar);
		break;
	case 10:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar10);
		break;
	case 20:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar20);
		break;
	case 30:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar30);
		break;
	case 40:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar40);
		break;
	case 50:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar50);
		break;
	case 60:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar60);
		break;
	case 70:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar70);
		break;
	case 80:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar80);
		break;
	case 90:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar90);
		break;
	case 100:
		drawImage(ACC_BAR_X, ACC_BAR_Y, 0, accBar100);
		break;
	}
}

function drawBrkBar(brkDepth) {
	switch (brkDepth) {
	case 0:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, emptyBrkBar);
		break;
	case 10:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar10);
		break;
	case 20:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar20);
		break;
	case 30:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar30);
		break;
	case 40:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar40);
		break;
	case 50:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar50);
		break;
	case 60:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar60);
		break;
	case 70:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar70);
		break;
	case 80:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar80);
		break;
	case 90:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar90);
		break;
	case 100:
		drawImage(BRK_BAR_X, BRK_BAR_Y, 0, brkBar100);
		break;
	}
}

function drawSteerAngle(strAngle) {
	drawImage(STR_WHEEL_X, STR_WHEEL_Y, strAngle, steeringWheel);
}

function drawGear(gear) {
	switch (gear) {
	case 0:
		drawImage(GEAR_X, GEAR_Y, 0, gear0);
		break;
	case 1:
		drawImage(GEAR_X, GEAR_Y, 0, gear1);
		break;
	case 2:
		drawImage(GEAR_X, GEAR_Y, 0, gear2);
		break;
	case 3:
		drawImage(GEAR_X, GEAR_Y, 0, gear3);
		break;
	case 4:
		drawImage(GEAR_X, GEAR_Y, 0, gear4);
		break;
	case 5:
		drawImage(GEAR_X, GEAR_Y, 0, gear5);
		break;

	}
}

function drawImage(x, y, radians, img) {
	var normalisedX = x / 5;
	var normalisedY = y / 5;
	var cx = normalisedX + img.width / 2;
	var cy = normalisedY + img.height / 2;
	var drawX = img.width / 2 * (-1);
	var drawY = img.height / 2 * (-1);

	ctx.translate(cx, cy);
	ctx.rotate(radians);
	ctx.drawImage(img, drawX, drawY);
	ctx.rotate(radians * (-1));
	ctx.translate(cx * (-1), cy * (-1));
}

function drawCourse() {
	courseRectangles.forEach(drawCourseRectangle);
	courseArcs.forEach(drawCourseArc);
}

function drawCourseRectangle(rectangle) {
	drawRectangle(rectangle.x, rectangle.y, rectangle.xSize, rectangle.ySize, rectangle.rotation, "#000000");
}

function drawCourseArc(arc) {
	drawArc(arc.x, arc.y, arc.radius, arc.startAngle, arc.endAngle, arc.counterClockwise, arc.rotation);
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
	var normalisedX = x / 10;
	var normalisedY = y / 10;
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

function drawArc(x, y, radius, startAngle, endAngle, counterClockwise, rotation) {
	var normalisedX = x / 10;
	var normalisedY = y / 10;
	ctx.beginPath();
	ctx.arc(normalisedX, normalisedY, radius, startAngle, endAngle, counterClockwise);
	ctx.strokeStyle = "#000000";
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
