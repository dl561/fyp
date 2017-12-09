function showCarArea(carNumber) {
	for (i = 1; i < 7; i++) {
		hide("car" + i + "Tab");
	}
	show("car" + carNumber + "Tab");
}

function toggleHide(id) {
	var x = document.getElementById(id);
	if (x.style.display === "none") {
		x.style.display = "block";
	} else {
		x.style.display = "none";
	}
}

function hide(id) {
	var x = document.getElementById(id);
	x.style.display = "none";
}

function show(id) {
	var x = document.getElementById(id);
	x.style.display = "inline";
}

function hideCarButtons(range) {
	for (i = 1; i < 7; i++) {
		if (i <= range) {
			show("car" + i + "TabButton");
		} else {
			hide("car" + i + "TabButton");
		}
	}
}
