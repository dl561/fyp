var keys = [];

window.addEventListener("keydown",
	function (e) {
	keys[e.keyCode] = true;
}, false);

window.addEventListener("keyup",
	function (e) {
	keys[e.keyCode] = false;
}, false);

function getKeys() {
	return keys;
}

function resetKeys() {
	keys = [];
}
