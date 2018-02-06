function submitFunction() {
	var carCount = document.getElementById("carCount").value;
	var numberOfLaps = document.getElementById("lapCount").value;
	var trackNumber1 = document.getElementById("trackNumber1").checked;
	var trackNumber2 = document.getElementById("trackNumber2").checked;
	var sound = document.getElementById("soundCheckbox").checked;
	var hostIp = document.getElementById("ipAddressBox").value;
	if (hostIp == "") {
		hostIp = "localhost";
	}
	var localCarNumber = getLocalCarNumber();
	var computerCars = [];
	for (i = 1; i <= carCount; i++) {
		if (document.getElementById("carComputer" + i).checked) {
			computerCars[i] = 1;
		} else {
			computerCars[i] = 0;
		}
	}
	if (!trackNumber1 && !trackNumber2) {
		var errorLabel = document.getElementById("trackNumberError");
		errorLabel.hidden = false;
		return;
	} else {
		var buttons = document.getElementById("submit");
		var newUrl = "web.html";
		localStorage.setItem("carCount", carCount);
		localStorage.setItem("trackNumber1", trackNumber1);
		localStorage.setItem("trackNumber2", trackNumber2);
		localStorage.setItem("sound", sound);
		localStorage.setItem("hostIP", hostIp);
		localStorage.setItem("localCarNumber", localCarNumber);
		localStorage.setItem("computerCars", computerCars);
		localStorage.setItem("numberOfLaps", numberOfLaps);
		window.location.replace(newUrl);
		document.location.href = newUrl;
	}
}

function updateRange() {
	var range = document.getElementById("carCount").value;
	var label = document.getElementById("carCountLabel");
	label.innerHTML = range;
	hideCarButtons(range);
	range = document.getElementById("lapCount").value;
	label = document.getElementById("lapCountLabel");
	label.innerHTML = range;
}

function checkSingleLocal(carNumber) {
	if (document.getElementById("carLocal" + carNumber).checked) {
		for (i = 1; i < 7; i++) {
			if (i != carNumber) {
				var x = document.getElementById("carLocal" + i);
				x.checked = false;
			}
		}
	}
}

function getLocalCarNumber() {
	var localCar = -1;
	for (i = 1; i <= document.getElementById("carCount").value; i++) {
		if (document.getElementById("carLocal" + i).checked) {
			localCar = i - 1;
		}
	}
	return localCar;
}
