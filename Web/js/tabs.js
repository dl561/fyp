var simulationSearchResponse = [];

function setSimulationSearchResponse(simSearchResponse) {
  simulationSearchResponse = simSearchResponse;
}

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

/*
Taken from TODO:#####first thing that comes up on google for html tabs
*/
function openPage(pageName, elmnt, color) {
  // Hide all elements with class="tabcontent" by default */
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Remove the background color of all tablinks/buttons
  tablinks = document.getElementsByClassName("tablink");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].style.backgroundColor = "";
  }

  // Show the specific tab content
  document.getElementById(pageName).style.display = "block";

  // Add the specific color to the button used to open the tab content
  elmnt.style.backgroundColor = color;
}

function updateSelect(selectIds) {
  var selectElement = document.getElementById("simulationSelect");

  while (selectElement.hasChildNodes()) {
    selectElement.removeChild(selectElement.firstChild);
  }
  selectIds.forEach(updateSingleSelect);
}

function updateSingleSelect(selectId) {
  var selectElement = document.getElementById("simulationSelect");

  var opt = document.createElement("option");
  opt.value = selectId.id;
  opt.innerHTML = selectId.name;

  selectElement.appendChild(opt);
}


function updateVehiclesSelect() {
  var selectElement = document.getElementById("vehiclesSelect");
  while (selectElement.hasChildNodes()) {
    selectElement.removeChild(selectElement.firstChild);
  }
  var defaultOpt = document.createElement("option");
  defaultOpt.value = -1;
  defaultOpt.innerHTML = "None selected";
  selectElement.appendChild(defaultOpt);

  var simulationSelectElements = document.getElementById("simulationSelect");
  var simulationId = simulationSelectElements.options[simulationSelectElements.selectedIndex].value;
  for (i = 0; i < simulationSearchResponse.length; i++) {
    if (simulationSearchResponse[i].id == simulationId) {
      for (j = 0; j < simulationSearchResponse[i].vehicles.length; j++) {
        var vehicle = simulationSearchResponse[i].vehicles[j];
        var opt = document.createElement("option");
        opt.value = vehicle.id;
        var text;
        if (vehicle.computer) {
          text = "Computer car " + (vehicle.id + 1);
        } else {
          text = "Car " + (vehicle.id + 1);
        }
        opt.innerHTML = text;
        selectElement.appendChild(opt);
      }
    }
  }
}

function getLocalCarNumberFromVehiclesSelect() {
  var vehicleSelect = document.getElementById("vehiclesSelect");
	var selectedId = vehicleSelect.options[vehicleSelect.selectedIndex].value;
	return selectedId;
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultTab").click();
