function submitFunction(){
	var carCount = document.getElementById("carCount").value;
	var trackNumber1 = document.getElementById("trackNumber1").checked;
	var trackNumber2 = document.getElementById("trackNumber2").checked;
	if(!trackNumber1&&!trackNumber2){
		var errorLabel = document.getElementById("trackNumberError");
		errorLabel.hidden = false;
		return;
	}else{
		var buttons = document.getElementById("submit");
		var newUrl = "web.html";
		localStorage.setItem("carCount", carCount);
		localStorage.setItem("trackNumber1", trackNumber1);
		localStorage.setItem("trackNumber2", trackNumber2);
		window.location.replace(newUrl);
		document.location.href = newUrl;
	}
}

function updateRange() {
	var range = document.getElementById("carCount").value;
	var label = document.getElementById("carCountLabel");
	label.innerHTML = range;
}