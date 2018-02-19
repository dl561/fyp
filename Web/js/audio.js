var music = new sound("audio/music.mp3");

function playMusic(sound) {
	if (sound == "true") {
		music.play();
	} else {
		music.stop();
	}
}

function sound(src) {
	this.sound = document.createElement("audio");
	this.sound.src = src;
	this.sound.setAttribute("preload", "auto");
	this.sound.setAttribute("controls", "none");
	this.sound.style.display = "none";
	document.body.appendChild(this.sound);
	this.play = function () {
		this.sound.play();
	}
	this.stop = function () {
		this.sound.pause();
	}
}
