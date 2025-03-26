const boardstate = document.getElementById('boardstate');
const trainSelector = document.getElementById('train');
const vagonSelector = document.getElementById('vagon');
const searchBtn = document.getElementById('search');

let trainList, vagonList, ticketList, trId, vId, token;

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

async function loadBS() {
    let response = await fetch("http://localhost:8082/ticket/getBS/" + trId + "/" + vId, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    ticketList = await response.json();
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

function buildBoardState() {
    if(boardstate.hasChildNodes()) {
        boardstate.innerHTML = '';
    }

    let thead = boardstate.createTHead();
    let row = thead.insertRow();
    let cellNumber = row.insertCell();
    let cellName = row.insertCell();
    let cellData = row.insertCell();
    let cellPhone = row.insertCell();

    cellNumber.innerText = "№ места";
    cellName.innerText = "Пассажир";
    cellData.innerText = "Данные";
    cellPhone.innerText = "Телефон";

    let tbody = boardstate.createTBody();

    ticketList.forEach((ticket) => {
        row = tbody.insertRow();
        cellNumber = row.insertCell();
        cellName = row.insertCell();
        cellData = row.insertCell();
        cellPhone = row.insertCell();

        cellNumber.innerText = ticket[0];
        cellName.innerText = ticket[1];
        cellData.innerText = ticket[2];
        cellPhone.innerText = ticket[3];
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    token = sessionStorage.getItem("token");
    await loadTrains();

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

searchBtn.addEventListener('click', async () => {
    await loadBS()
    buildBoardState();
    vagonSelector.disabled = false;
    searchBtn.disabled = false;
});