var simulationSearchResponse = [];

function createNewSimulation() {
  var finishedLabel = document.getElementById("finishedCreatingLabel");
  finishedLabel.hidden = true;
  //simulation wide variables
  var simulationName = document.getElementById("simNameBox").value;
  var hostIp = document.getElementById("ipAddressBox").value;
  var trackNumber = getTrackNumber();
  var numberOfLaps = document.getElementById("lapCount").value;
  var numberOfCars = document.getElementById("carCount").value;
  var computerCars = getComputerCars(numberOfCars);

  //local variables
  var localCarNumber = getLocalCarNumber();
  var audio = document.getElementById("soundCheckbox").checked;

  //validation
  if (trackNumber == -1) {
    return;
  }
  if (hostIp == "") {
    hostIp = "localhost";
  }

  //creation
  setHostIP(hostIp);
  newSimulationByOptions(simulationName, trackNumber, numberOfLaps, numberOfCars, computerCars, function(response) {
    var finishedLabel = document.getElementById("finishedCreatingLabel");
    finishedLabel.hidden = false;
  });
}

function searchForSimulationIds() {
  var hostIp = document.getElementById("ipAddressBoxForJoin").value;
  if (hostIp == "") {
    hostIp = "localhost";
  }
  searchForSimulations(hostIp);
}

function searchForSimulations(hostIp) {
  setHostIP(hostIp);
  doSearchSimulationIds(function(response) {
    var responseObj = JSON.parse(response);
    simulationSearchResponse = responseObj;
    setSimulationSearchResponse(simulationSearchResponse);
    updateSelect(responseObj);
  });
}

function joinSimulationNoArgs() {
  //Just need to get the hostIp, simulationId, localCarNumber and whether to have sound
  var hostIp = document.getElementById("ipAddressBoxForJoin").value;
  if (hostIp == "") {
    hostIp = "localhost";
  }
  var simulationIdSelect = document.getElementById("simulationSelect");
  var simulationId = simulationIdSelect.options[simulationIdSelect.selectedIndex].value;
  var localCarNumber = getLocalCarNumberFromVehiclesSelect();
  var audio = document.getElementById("audioCheckBox").checked;
  joinSimulation(hostIp, simulationId, localCarNumber, audio);
}

function joinSimulation(hostIp, simulationId, localCarNumber, audio) {
  localStorage.setItem("hostIp", hostIp);
  localStorage.setItem("simulationId", simulationId);
  localStorage.setItem("localCarNumber", localCarNumber);
  localStorage.setItem("audio", audio);
  setTimeout(moveToPage("web.html"), 2000);
}

function getTrackNumber() {
  var track1 = document.getElementById("trackNumber1").checked;
  var track2 = document.getElementById("trackNumber2").checked;

  if (track1) {
    return 1;
  } else if (track2) {
    return 2;
  } else {
    document.getElementById("trackNumberError").hidden = false;
    return -1;
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

function getComputerCars(carCount) {
  var computerCars = [];
  for (i = 0; i < carCount; i++) {
    if (document.getElementById("carComputer" + (i + 1)).checked) {
      computerCars[i] = 1;
    } else {
      computerCars[i] = 0;
    }
  }
  return computerCars;
}

function moveToPage(page) {
  console.log("Moving to " + page);
  window.location.replace(page);
  document.location.href = page;
}
