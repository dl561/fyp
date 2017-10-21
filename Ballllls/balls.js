	var canvas = document.getElementById("simulationCanvas");
	var ctx = canvas.getContext("2d");
	
	ctx.canvas.width  = window.innerWidth;
	ctx.canvas.height = window.innerHeight;
	
	var balls = [];
	
	function checkCollision(ball){
		if(ball.x >= canvas.width){
			ball.x = canvas.width -1;
			ball.dx = 0 - ball.dx;
		}
		if(ball.x <= 0) {
			ball.x = 1;
			ball.dx = 0 - ball.dx;
		}
		if(ball.y >= canvas.height){
			ball.y = canvas.height -1;
			ball.dy = 0 - ball.dy;
		}
		if(ball.y <= 0){
			ball.y = 1;
			ball.dy = 0 - ball.dy;
		}
	}
	
	function drawBall(ball) {
		ctx.beginPath();
		ctx.arc(ball.x, ball.y, 20, 0, Math.PI*2);
		ctx.fillStyle = "#0095DD";
		ctx.fill();
		ctx.closePath();
	}
	
	function incrementBall(ball) {
		ball.x += ball.dx;
		ball.y += ball.dy;
	}

	function getRandomInt(min, max) {
		return (Math.random() * (max - min + 1)) + min;
	}
	
	function createBall() {
		var ball = new Object();
		ball.x = 0;
		ball.y = 0;
		ball.dx = getRandomInt(1,30);
		ball.dy = getRandomInt(1,30);
		ball.colour = "#0095DD";
		return ball;
	}
	
	function createBalls(){
		for(i = 0; i < 10; i ++){
			balls.push(createBall());
		}
	}
	
	function draw() {
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		balls.forEach(checkCollision);
		balls.forEach(drawBall);
		balls.forEach(incrementBall);
	}
	
	createBalls();
setInterval(draw,30);