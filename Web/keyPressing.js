var keys = [];

window.addEventListener("keydown",
	function(e){
		keys[e.keyCode] = true;
	},false);
	
function getKeys(){
	return keys;
}

function resetKeys(){
	keys = [];
}