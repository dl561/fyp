
function draw(){
	console.log("Drawing");
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	drawCourse();
	drawVehicles();
	drawHUD();
}

function drawCourse(){
	courseRectangles.forEach(drawCourseRectangle);
	courseArcs.forEach(drawCourseArc);
}

function drawCourseRectangle(rectangle){
	drawRectangle(rectangle.x, rectangle.y, rectangle.xSize, rectangle.ySize, rectangle.rotation, "#000000");
}

function drawCourseArc(arc){
	drawArc(arc.x, arc.y, arc.radius, arc.startAngle, arc.endAngle, arc.counterClockwise, arc.rotation);
}

function drawVehicles(){
	vehicleObjects.forEach(drawVehiclePieces);
}

function drawVehiclePieces(vehicle){
	drawRectangle(vehicle.location.x, vehicle.location.y, 4, 2, vehicle.directionOfTravel, "#00ffff");
}

function drawHUD(){
	
}

function rotationTest() {
	var x = 100;
	var y = 100;
	var w = 100;
	var h = 100;
	drawRectangle(x,y,w,h,rotationAmount);
}

function drawRectangle(x, y, width, height, degrees, colour){
    var radians = degrees * Math.PI / 180;
	var cx = x + width / 2;
	var cy = y + height / 2;
	var drawX = width / 2 * (-1);
	var drawY = height / 2 * (-1);
	
    ctx.translate(cx, cy);
    ctx.rotate(radians);
	ctx.fillStyle = colour;
    ctx.fillRect(drawX, drawY, width, height);
    ctx.rotate(radians * ( -1 ));
    ctx.translate(cx * (-1), cy * (-1));
}

function drawArc(x, y, radius, startAngle, endAngle, counterClockwise, rotation){
	ctx.beginPath();
	ctx.arc(x, y, radius, startAngle, endAngle, counterClockwise);
	ctx.strokeStyle = "#000000";
	ctx.lineWidth = 60;
	ctx.rotate(rotation * Math.PI / 180);
	ctx.stroke();
	ctx.closePath();	
	ctx.restore();
}