const list = document.getElementById('trainList');
const trainForm = document.getElementById('train');
const deleteTrBtn = document.getElementById('deleteTrBtn');
const updateTrBtn = document.getElementById('updateTrBtn');
const routeSelector = document.getElementById('routeSelector');
const vagonForm = document.getElementById('createVagon');
const closeFrmBtn = document.getElementById('closeForm');
const seatDialog = document.getElementById('seatDialog');
const deleteVBtn = document.getElementById('deleteVBtn');
const closeSeatFormBtn = document.getElementById('closeSeatForm');
const seatForm = document.getElementById('seat');
const updateSBtn = document.getElementById('updateSBtn');
const deleteSBtn = document.getElementById('deleteSBtn');
const seatsTable = document.getElementById('seatsTable');

let trainsList, routesList, vagonsList, seatsList, trId, vId, sId, token;

function buildTrainList() {
    if(list.hasChildNodes()) {
        list.innerHTML = '';
    }

    trainsList.forEach((train) => {
        let li = document.createElement('li');
        li.innerText = "Поезд №" + train.id;
        list.appendChild(li);
    });
}

function buildRouteSelect(entries) {
    entries.forEach((entry) => {
        let option = document.createElement('option');
        option.innerHTML = entry.id;
        option.value = entry.id;
        routeSelector.appendChild(option);
    });
}

async function buildSeatsTable() {
    await loadSeats();

    if(seatsTable.hasChildNodes()) {
        seatsTable.innerHTML = '';
    }

    let thead = seatsTable.createTHead();
    let row = thead.insertRow();
    let cellId = row.insertCell();
    let cellNumber = row.insertCell();
    let cellPrice = row.insertCell();
    let cellStatus = row.insertCell();

    cellId.classList.add('hidden');
    cellNumber.innerText = "№";
    cellPrice.innerText = "Стоимость, Р";
    cellStatus.innerText = "Занято?";

    let tbody = seatsTable.createTBody();

    seatsList.forEach((seat) => {
        const {id, number, isBusy, price} = seat;

        row = tbody.insertRow();
        cellId = row.insertCell();
        cellNumber = row.insertCell();
        cellPrice = row.insertCell();
        cellStatus = row.insertCell();

        cellId.innerText = id;
        cellId.classList.add('hidden');
        cellNumber.innerText = number;
        cellPrice.innerText = price;
        cellStatus.innerText = isBusy == true ? "да" : "нет";
    });
}

function serializeData(data) {
    const { elements } = data;

    const formattedData = new FormData();

    Array.from(elements)
        .forEach((element) => {
            const {name, value} = element;
            if(element.type == 'checkbox') {
                formattedData.append(name, element.checked)
            }
            else if(value != "") {
                formattedData.append(name, value);
            }
        });

    return JSON.stringify(Object.fromEntries(formattedData));
}

function refreshLines(lines) {
    lines.forEach((line) => {
        line.classList.remove('active');
        child = line.querySelector(".vagons");
        if(child != undefined) {
            line.removeChild(child);
        }
    });
}

function refreshRows(rows) {
    rows.forEach((row) => {
        row.classList.remove('selected');
    });
}

async function buildVagonList(node) {
    await loadVagons();

    let span = document.createElement('span');
    span.classList.add('vagons');

    vagonsList.forEach((vagon) => {
        let vgnDiv = document.createElement('div');
        vgnDiv.innerHTML = vagon.number;
        vgnDiv.classList.add('vagon');
        vgnDiv.setAttribute('title', vagon.type);
        vgnDiv.setAttribute('data-id', vagon.id)
        span.appendChild(vgnDiv);
    });

    let addVgnBtn = document.createElement('button');
    addVgnBtn.classList.add('add-vagon');
    addVgnBtn.setAttribute('title', 'Добавить вагон');

    span.appendChild(addVgnBtn);

    node.appendChild(span);
}

function parseTrainToForm() {
    trainsList.forEach((train) => {        
        if(train.id == trId) {
            document.getElementById('trType').value = train.type == "Скоростной" ? 1 : (train.type == "Фирменный" ? 3 : 2);
            routeSelector.value = train.routeId; 
        }
    });
}

function parseSeatToForm(row) {
    document.getElementById('seatPrice').value = row[2].innerText;
    if(row[3].innerText == 'да') {
        document.getElementById('seatStatus').checked = true;
    }
    else {
        document.getElementById('seatStatus').checked = false;
    }
}

async function updateTrainsList() {
    await loadTrains();
    buildTrainList();
}

function updateVagonsList() {
    let vagonNumber = 1;
    vagonsList.forEach(async (vagon) => {
        if(vagon.id != vId) {
            vagon.number = vagonNumber;
            vagonNumber += 1;
            await updateVagon(vagon);
        }
    });
}

function updateSeatsList() {
    let seatNumber = 1;
    seatsList.forEach(async (seat) => {
        if(seat.id != sId) {
            seat.number = seatNumber;
            seatNumber += 1;
            await updateSeatCard(seat);
        }
    });
}

async function loadVagons() {
    let response = await fetch("http://localhost:8082/vagon/getVagonsForTrain/" + trId, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    vagonsList = await response.json();
}

async function updateVagon(data) {
    await fetch("http://localhost:8082/vagon/updateVagon/" + data.id, {
        method: 'PATCH',
        body: JSON.stringify(data),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
}

async function loadSeats() {
    let response = await fetch("http://localhost:8082/seatCard/getSeatsForVagon/" + vId, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    seatsList = await response.json();
}

async function createSeat(data) {
    let response = await fetch("http://localhost:8082/seatCard/createSeatCard", {
        method: 'POST',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);
}

async function loadTrains() {
    let response = await fetch("http://localhost:8082/train/getAllTrains", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    trainsList = await response.json();
}

async function createTrain(data) {
    let response = await fetch("http://localhost:8082/train/createTrain", {
        method: 'POST',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);    
}

async function createVagon(data) {
    let response = await fetch("http://localhost:8082/vagon/createVagon", {
        method: 'POST',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    })

    alert(response.status);
}

async function loadRoutes() {
    let response = await fetch("http://localhost:8082/route/getAllRoutes", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    routesList = await response.json();
}

document.addEventListener('DOMContentLoaded', async () => {
    token = sessionStorage.getItem("token");
    await updateTrainsList();
    await loadRoutes();

    buildRouteSelect(routesList);
});

trainForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    let data = serializeData(e.target);  
    await createTrain(data);     
    await updateTrainsList();
    trainForm.reset();
})

seatForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    let data = JSON.parse(serializeData(e.target));
    data.number = seatsList.length + 1;
    data.vagonId = vId;
    data = JSON.stringify(data);
    
    await createSeat(data);
    await buildSeatsTable();
    seatForm.reset();
});

list.addEventListener('click', (e) => {
    if(e.target.nodeName == 'LI') {
        refreshLines(list.querySelectorAll('li'));
        e.target.classList.add('active');
        updateTrBtn.disabled = false;
        deleteTrBtn.disabled = false;
        trId = e.target.innerHTML.split("№")[1];
        parseTrainToForm();
        buildVagonList(e.target);
    }
    else if(e.target.nodeName == 'BUTTON') {
        vagonForm.showModal();
    }
    else if(e.target.nodeName == 'DIV') {
        vId = e.target.getAttribute('data-id');
        
        seatDialog.showModal();
        buildSeatsTable();
    }
});

updateTrBtn.addEventListener('click', async () => {
    let data = serializeData(trainForm);

    let response = await fetch("http://localhost:8082/train/updateTrain/" + trId, {
        method: 'PATCH',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
    
    alert(response.status);

    trainForm.reset();
    updateTrBtn.disabled = true;
    deleteTrBtn.disabled = true;
    await updateTrainsList();
});

deleteTrBtn.addEventListener('click', async () => {
    let response = await fetch("http://localhost:8082/train/deleteTrain/" + trId, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);

    updateTrBtn.disabled = true;
    deleteTrBtn.disabled = true;
    await updateTrainsList();
});

deleteVBtn.addEventListener('click', async () => {
    let response = await fetch("http://localhost:8082/vagon/deleteVagon/" + vId, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);

    seatDialog.close();
    refreshLines(list.querySelectorAll('li'));
    updateVagonsList();
});

deleteSBtn.addEventListener('click', async () => {
    let response = await fetch("http://localhost:8082/seatCard/deleteSeatCard/" + sId, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);
    
    updateSeatsList();
    updateSBtn.disabled = true;
    deleteSBtn.disabled = true;
    await buildSeatsTable();
});

function updateSeat() {
    let data = JSON.parse(serializeData(seatForm));

    seatsList.forEach((seat) => {
        if(seat.id == sId) {
            seat.price = data.price;
            seat.isBusy = data.isBusy;
            data = seat;
        }
    });
    
    return data;
}

async function updateSeatCard(data) {
    await fetch("http://localhost:8082/seatCard/updateSeatCard/" + data.id, {
        method: 'PATCH',
        body: JSON.stringify(data),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
}

updateSBtn.addEventListener('click', async () => {
    let data = updateSeat();
    await updateSeatCard(data);
    updateSBtn.disabled = true;
    deleteSBtn.disabled = true;
    await buildSeatsTable();
    seatForm.reset();
})

closeFrmBtn.addEventListener('click', () => {
    vagonForm.close();
});

closeSeatFormBtn.addEventListener('click', () => {
    seatDialog.close();
});

vagonForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    let data = JSON.parse(serializeData(e.target));
    data.trainNum = trId;
    data.number = vagonsList.length + 1;
    data = JSON.stringify(data);
    
    await createVagon(data);

    vagonForm.close();
    refreshLines(list.querySelectorAll('li'));
});

seatsTable.addEventListener('click', async (e) => {
    let row = e.target.parentElement;
    let tbody = row.parentElement;
    if(e.target.nodeName == 'TD' && tbody.nodeName == 'TBODY') {
        refreshRows(Array.from(tbody.children));
        row.classList.add('selected');
        updateSBtn.disabled = false;
        deleteSBtn.disabled = false;
        sId = row.querySelector('td').innerText;
        parseSeatToForm(Array.from(row.children));
    }
});