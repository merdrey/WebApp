const fromSelector = document.getElementById('fromSt');
const toSelector = document.getElementById('toSt');
const dateInput = document.getElementById('date');
const searchBtn = document.getElementById('searchBtn');
const schedTab = document.getElementById('schedule');
const vagonsTab = document.getElementById('vagonsTable');
const seatsTab = document.getElementById('seatsTable');
const passForm = document.getElementById('passengercard');
const loginDialog = document.getElementById('loginDialog');
const signForm = document.getElementById('sign');
const log = document.getElementById('log');
const reg = document.getElementById('reg');
const signBtn = document.getElementById('signBtn');
const phoneInp = document.getElementById('phoneInp');
const emailInp = document.getElementById('emailInp');
const ticketForm = document.getElementById('ticketForm');
const passDialog = document.getElementById('passDialog');
const passSelector = document.getElementById("passList");

let stations, vagons, seats, fromSt, toSt, date, sId, token, passengers;

function serializeData(data) {
    const { elements } = data;

    const formattedData = new FormData();

    Array.from(elements)
        .forEach((element) => {
            const {name, value} = element;
            if(value != "") {
                formattedData.append(name, value);
            }
        });

    return JSON.stringify(Object.fromEntries(formattedData));
}

async function loadStations() {
    let response = await fetch("http://localhost:8082/getAllStations", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
        }
    });

    stations = await response.json();
}

async function loadPassengers() {
    let response = await fetch("http://localhost:8082/passenger/getPassengerByUser/" + window.sessionStorage.getItem('userId'), {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    passengers = await response.json();
}

async function loadVagons(id) {
    let response = await fetch("http://localhost:8082/vagon/getVagonsAndSeatsForTrain/" + id, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    vagons = await response.json();
}

async function loadSeats(id) {
    let response = await fetch("http://localhost:8082/seatCard/getSeatsForVagon/" + id, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    seats = await response.json();
}

async function createPassenger(data) {    
    let response = await fetch("http://localhost:8082/passenger/createPassenger", {
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

async function createTicket(data) {
    let response = await fetch("http://localhost:8082/ticket/createTicket", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);
}

function buildSched(list) {
    if(schedTab.hasChildNodes()) {
        schedTab.innerHTML = '';
    }

    let thead = schedTab.createTHead();
    let row = thead.insertRow();
    let cellTrainId = row.insertCell();
    let cellTrainType = row.insertCell();
    let cellRouteId = row.insertCell();
    let cellDepSt = row.insertCell();
    let cellDepDate = row.insertCell();
    let cellDepTime = row.insertCell();
    let cellArrSt = row.insertCell();
    let cellArrDate = row.insertCell();
    let cellArrTime = row.insertCell();
    let cellFreeSeats = row.insertCell();
    let cellMinPrice = row.insertCell();

    cellTrainId.innerText = "№ поезда";
    cellTrainType.innerText = "Тип поезда";
    cellRouteId.innerText = "№ маршрута";
    cellDepSt.innerText = "Станция отправления";
    cellDepDate.innerText = "Дата отправления";
    cellDepTime.innerText = "Время отправления";
    cellArrSt.innerText = "Станция прибытия";
    cellArrDate.innerText = "Дата прибытия";
    cellArrTime.innerText = "Время прибытия";
    cellFreeSeats.innerText = "Кол-во свободных мест";
    cellMinPrice.innerText = "Мин. цена, Р";

    let tbody = schedTab.createTBody();

    list.forEach((elem) => {
        row = tbody.insertRow();
        cellTrainId = row.insertCell();
        cellTrainType = row.insertCell();
        cellRouteId = row.insertCell();
        cellDepSt = row.insertCell();
        cellDepDate = row.insertCell();
        cellDepTime = row.insertCell();
        cellArrSt = row.insertCell();
        cellArrDate = row.insertCell();
        cellArrTime = row.insertCell();
        cellFreeSeats = row.insertCell();
        cellMinPrice = row.insertCell();

        cellTrainId.innerText = elem[0];
        cellTrainType.innerText = elem[1];
        cellRouteId.innerText = elem[2];
        cellDepSt.innerText = elem[3];
        cellDepDate.innerText = elem[4];
        cellDepTime.innerText = elem[5];
        cellArrSt.innerText = elem[6];
        cellArrDate.innerText = elem[7];
        cellArrTime.innerText = elem[8];
        cellFreeSeats.innerText = elem[9];
        cellMinPrice.innerText = elem[10];
    });
}

function buildVagonsTable() {
    if(vagonsTab.hasChildNodes()) {
        vagonsTab.innerHTML = '';
    }

    let thead = vagonsTab.createTHead();
    let row = thead.insertRow();
    let cellVagonId = row.insertCell();
    let cellVagonNum = row.insertCell();
    let cellVagonType = row.insertCell();
    let cellFreeSeats = row.insertCell();
    let cellMinPrice = row.insertCell();

    cellVagonId.classList.add('hidden');
    cellVagonNum.innerText = "№ вагона";
    cellVagonType.innerText = "Тип вагона";
    cellFreeSeats.innerText = "Кол-во свободных мест";
    cellMinPrice.innerText = "Мин. цена, Р";

    let tbody = vagonsTab.createTBody();

    vagons.forEach((vagon) => {
        row = tbody.insertRow();
        cellVagonId = row.insertCell();
        cellVagonNum = row.insertCell();
        cellVagonType = row.insertCell();
        cellFreeSeats = row.insertCell();
        cellMinPrice = row.insertCell();

        cellVagonId.innerText = vagon[0];
        cellVagonId.classList.add('hidden');
        cellVagonNum.innerText = vagon[1];
        cellVagonType.innerText = vagon[2];
        cellFreeSeats.innerText = vagon[3];
        cellMinPrice.innerText = vagon[4];
    });
}

function buildSeatsTable() {
    if(seatsTab.hasChildNodes()) {
        seatsTab.innerHTML = '';
    }

    let thead = seatsTab.createTHead();
    let row = thead.insertRow();
    let cellSeatId = row.insertCell();
    let cellNumber = row.insertCell();
    let cellPrice = row.insertCell();
    let cellStatus = row.insertCell();

    cellSeatId.classList.add('hidden');
    cellNumber.innerText = "№";
    cellPrice.innerText = "Стоимость, Р";
    cellStatus.innerText = "Занято?";

    let tbody = seatsTab.createTBody();

    seats.forEach((seat) => {
        const {id, number, isBusy, price} = seat;

        row = tbody.insertRow();
        cellSeatId = row.insertCell();
        cellNumber = row.insertCell();
        cellPrice = row.insertCell();
        cellStatus = row.insertCell();
        
        cellSeatId.innerText = id;
        cellSeatId.classList.add('hidden');
        cellNumber.innerText = number;
        cellPrice.innerText = price;
        cellStatus.innerText = isBusy == true ? "да" : "нет";

        if (isBusy) {
            row.classList.add("busy");
        }
    });
}

function buildStSelect(selector) {
    stations.forEach((station) => {
        let option = document.createElement('option');
        option.innerHTML = station.nameSt;
        option.value = station.id;
        selector.appendChild(option);
    });
}

function buildPassSelect() {
    if (passSelector.hasChildNodes()) {
        passSelector.innerHTML = '';
    }

    passengers.forEach((passenger) => {
        let option = document.createElement('option');
        option.innerText = passenger.name;
        option.value = passenger.id;
        
        passSelector.appendChild(option);
    });
}

function refreshRows(rows) {
    rows.forEach((row) => {
        row.classList.remove('selected');
    });
}

async function updateSeat() {
    let data;
    seats.forEach((seat) => {
        if(seat.id == sId) {
            seat.isBusy = true;
            data = seat;
        }
    });

    await fetch("http://localhost:8082/seatCard/updateSeatCard/" + sId, {
        method: 'PATCH',
        body: JSON.stringify(data),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
}

function refreshNav() {
    token = window.sessionStorage.getItem('token');
    let data = parseJwt(token);
    document.querySelectorAll("header nav ul li.hidden a").forEach((elem) => {
        if(data.role == "ROLE_ADMIN") {
            if(elem.innerText != "Личный кабинет") {
                elem.parentElement.classList.remove('hidden');
            }
        }
        if(data.role == "ROLE_USER") {
            if(elem.innerText == "Личный кабинет") {
                elem.parentElement.classList.remove('hidden');
            }
        }
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    if(window.sessionStorage.getItem('token') == null) {
        loginDialog.showModal();
    } 
    else {
        refreshNav();
    }

    await loadStations();
    
    buildStSelect(fromSelector);
    buildStSelect(toSelector);
});

log.addEventListener('click', () => {
    reg.classList.remove('active');
    log.classList.add('active');
    phoneInp.classList.add('hidden');
    emailInp.classList.add('hidden');

    phoneInp.querySelector('input').required = false;
    emailInp.querySelector('input').required = false;

    signBtn.innerText = 'Вход';
});

reg.addEventListener('click', () => {
    reg.classList.add('active');
    log.classList.remove('active');
    phoneInp.classList.remove('hidden');
    emailInp.classList.remove('hidden');

    phoneInp.querySelector('input').required = true;
    emailInp.querySelector('input').required = true;

    signBtn.innerText = 'Регистрация';
});

signForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    let data = serializeData(e.target);
    let status
    if(signBtn.innerText == 'Вход') {
        status = await signIn(data);
    }
    else {
        status = await signUp(data);
    }

    if(status == '200') {
        loginDialog.close();
        window.sessionStorage.setItem('token', token.token);
        window.sessionStorage.setItem('userId', parseJwt(token.token).id);
        refreshNav();
    }
});

async function signIn(data) {
    let response = await fetch("http://localhost:8082/auth/signIn", {
        method: 'POST',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
        }
    });
    token = await response.json();
    return response.status;
}

async function signUp(data) {
    let response = await fetch("http://localhost:8082/auth/signUp", {
        method: 'POST',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
        }
    });
    token = await response.json();
    return response.status;
}

function parseJwt (token) {
    return JSON.parse(atob(token.split('.')[1]));
}

document.getElementById('addPass').addEventListener('click', () => {
    passDialog.showModal();
});

document.getElementById('buyTicket').addEventListener('click', async () => {
    updateSeat();
    let obj = {
        "passengerId": passSelector.value,
        "seatId": sId,
        "arrStation": toSelector.value,
        "depStation": fromSelector.value
    };    
    await createTicket(obj);
    ticketForm.classList.add('hidden');
    seatsTab.innerHTML = '';
    vagonsTab.innerHTML = '';
    schedTab.innerHTML = '';
});

searchBtn.addEventListener('click', async () => {
    fromSt = fromSelector.value;
    toSt = toSelector.value;
    date = dateInput.value;
    token = window.sessionStorage.getItem('token');
    
    if(fromSt != toSt && date != "") {
        let ans = await fetch("http://localhost:8082/train/getTrainsBySt/" + fromSt + "/" + toSt + "/" + date, {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': "http://localhost:8082",
                Authorization: 'Bearer ' + token
            }
        });
        buildSched(await ans.json()); 
    }
    else if(date == ""){
        alert("Укажите дату отправки.");
    }
    else if(fromSt == toSt) {
        alert("Станции отправки и прибытия совпадают.");
    }
});

schedTab.addEventListener('click', async (e) => {
    let row = e.target.parentElement;
    let tbody = row.parentElement;
    if(e.target.nodeName == 'TD' && tbody.nodeName == 'TBODY') {
        refreshRows(Array.from(tbody.children));
        row.classList.add('selected');
        await loadVagons(row.querySelector("td").innerText);
        buildVagonsTable();
    }
});

vagonsTab.addEventListener('click', async (e) => {
    let row = e.target.parentElement;
    let tbody = row.parentElement;
    if(e.target.nodeName == 'TD' && tbody.nodeName == 'TBODY') {
        refreshRows(Array.from(tbody.children));
        row.classList.add('selected');
        await loadSeats(row.querySelector("td").innerText);        
        buildSeatsTable();
    }
});

seatsTab.addEventListener('click', async (e) => {
    let row = e.target.parentElement;
    let tbody = row.parentElement;
    if(e.target.nodeName == 'TD' && tbody.nodeName == 'TBODY' && parseJwt(token).role != "ROLE_ADMIN" && !row.classList.contains("busy")) {
        refreshRows(Array.from(tbody.children));
        row.classList.add('selected');
        ticketForm.classList.remove('hidden')
        sId = row.querySelector("td").innerText;
        if (!passSelector.hasChildNodes()) 
        {
            await loadPassengers();
        }
        buildPassSelect();
    }
});

passForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    let data = JSON.parse(serializeData(e.target));
    data.uId = window.sessionStorage.getItem('userId');
    await createPassenger(JSON.stringify(data));
    await loadPassengers();
    buildPassSelect();
    passForm.reset();
    passDialog.close();
})