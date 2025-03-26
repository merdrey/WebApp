const landlist = document.getElementById('landlist');
const trainSelector = document.getElementById('train');
const vagonSelector = document.getElementById('vagon');
const checkbox = document.getElementById('seatStatus');
const searchBtn = document.getElementById('searchLandList');

let trainList, vagonList, seatList, trId, vId, token = sessionStorage.getItem('token');

async function loadTrains() {
    let response = await fetch("http://localhost:8082/train/getAllTrains", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    trainList = await response.json();
}

async function loadVagons() {
    let response = await fetch("http://localhost:8082/vagon/getVagonsForTrain/" + trId, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    vagonList = await response.json();
}

async function loadSeats() {
    let response = await fetch("http://localhost:8082/seatCard/getAllSeatCards", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    seatList = await response.json();
}

function buildTrainSelect() {
    trainList.forEach((train) => {
        let option = document.createElement('option');
        option.innerHTML = train.id;
        option.value = train.id;
        trainSelector.appendChild(option);
    });
}

function buildVagonSelect() {
    if(vagonSelector.hasChildNodes()) {
        vagonSelector.innerHTML = '';
    }
    vagonList.forEach((vagon) => {
        let option = document.createElement('option');
        option.innerHTML = vagon.number;
        option.value = vagon.id;
        vagonSelector.appendChild(option);
    });
}

function buildLandList(list) {
    if(landlist.hasChildNodes()) {
        landlist.innerHTML = '';
    }

    let thead = landlist.createTHead();
    let row = thead.insertRow();
    let cellNumber = row.insertCell();
    let cellPrice = row.insertCell();
    let cellStatus = row.insertCell();

    cellNumber.innerText = "№";
    cellPrice.innerText = "Стоимость, Р";
    cellStatus.innerText = "Занято?";

    let tbody = landlist.createTBody();

    list.forEach((seat) => {
        const {number, isBusy, price} = seat;

        row = tbody.insertRow();
        cellNumber = row.insertCell();
        cellPrice = row.insertCell();
        cellStatus = row.insertCell();

        cellNumber.innerText = number;
        cellPrice.innerText = price;
        cellStatus.innerText = isBusy == true ? "да" : "нет";
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    await loadTrains();
    await loadSeats();

    buildTrainSelect();

    trId = trainSelector.value;
    
    await loadVagons();
    
    buildVagonSelect();
});

trainSelector.addEventListener('input', async () => {
    trId = trainSelector.value;

    await loadVagons();

    buildVagonSelect();
    vagonSelector.disabled = false;
});

vagonSelector.addEventListener('input', () => {
    vId = vagonSelector.value;
    searchBtn.disabled = false;
});

searchBtn.addEventListener('click', () => {
    let searchList = [];
    seatList.forEach((seat) => {
        if(checkbox.checked == true) {
            if(seat.vagonId == vId && seat.isBusy == false) {
                searchList.push(seat);
            }
        }
        else {
            if(seat.vagonId == vId) {
                searchList.push(seat);
            }
        }
    });
    buildLandList(searchList);
    vagonSelector.disabled = false;
    searchBtn.disabled = false;
});