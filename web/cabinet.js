const passTab = document.getElementById('passengers');
const retBtn = document.getElementById('tRet');
const delBtn = document.getElementById('passDel');

let token = window.sessionStorage.getItem('token'), uId = window.sessionStorage.getItem('userId');
let passList, tId, pId, sId;

document.addEventListener('DOMContentLoaded', async () => {
    
    let response = await fetch("http://localhost:8082/passenger/getPassengersForUser/" + uId, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    passList = await response.json();

    buildTable();
});

function buildTable() {
    if(passTab.hasChildNodes()) {
        passTab.innerHTML = '';
    }

    let thead = passTab.createTHead();
    let row = thead.insertRow();
    let cellPassId = row.insertCell();
    let cellTicketId = row.insertCell();
    let cellSeatId = row.insertCell();
    let cellName = row.insertCell();
    let cellDate = row.insertCell();
    let cellSeatNum = row.insertCell();
    let cellVagonNum = row.insertCell();
    let cellTrainId = row.insertCell();
    let cellRouteId = row.insertCell();
    let cellDepSt = row.insertCell();
    let cellDepDate = row.insertCell();
    let cellDepTime = row.insertCell();
    let cellArrSt = row.insertCell();
    let cellArrDate = row.insertCell();
    let cellArrTime = row.insertCell();

    cellPassId.classList.add('hidden');
    cellTicketId.classList.add('hidden');
    cellSeatId.classList.add('hidden');
    cellName.innerText = "Пассажир";
    cellDate.innerText = "Дата рождения";
    cellSeatNum.innerText = "№ места";
    cellVagonNum.innerText = "№ вагона";
    cellTrainId.innerText = "№ поезда";
    cellRouteId.innerText = "№ маршрута";
    cellDepSt.innerText = "Станция отправления";
    cellDepDate.innerText = "Дата отправления";
    cellDepTime.innerText = "Время отправления";
    cellArrSt.innerText = "Станция прибытия";
    cellArrDate.innerText = "Дата прибытия";
    cellArrTime.innerText = "Станция прибытия";

    let tbody = passTab.createTBody();

    passList.forEach((pass) => {
        row = tbody.insertRow();
        cellPassId = row.insertCell();
        cellTicketId = row.insertCell();
        cellSeatId = row.insertCell();
        cellName = row.insertCell();
        cellDate = row.insertCell();
        cellSeatNum = row.insertCell();
        cellVagonNum = row.insertCell();
        cellTrainId = row.insertCell();
        cellRouteId = row.insertCell();
        cellDepSt = row.insertCell();
        cellDepDate = row.insertCell();
        cellDepTime = row.insertCell();
        cellArrSt = row.insertCell();
        cellArrDate = row.insertCell();
        cellArrTime = row.insertCell();

        cellPassId.innerText = pass[0];
        cellTicketId.innerText = pass[1];
        cellSeatId.innerText = pass[2];
        cellName.innerText = pass[3];
        cellDate.innerText = pass[4];
        cellSeatNum.innerText = pass[5];
        cellVagonNum.innerText = pass[6];
        cellTrainId.innerText = pass[7];
        cellRouteId.innerText = pass[8];
        cellDepSt.innerText = pass[9];
        cellDepDate.innerText = pass[10];
        cellDepTime.innerText = pass[11];
        cellArrSt.innerText = pass[12];
        cellArrDate.innerText = pass[13];
        cellArrTime.innerText = pass[14];

        cellPassId.classList.add('hidden');
        cellTicketId.classList.add('hidden');
        cellSeatId.classList.add('hidden');
    });
}

function refreshRows(rows) {
    rows.forEach((row) => {
        row.classList.remove('selected');
    });
}

passTab.addEventListener('click', (e) => {
    let row = e.target.parentElement;
    let tbody = row.parentElement;
    if(e.target.nodeName == 'TD' && tbody.nodeName == 'TBODY') {
        refreshRows(Array.from(tbody.children));
        row.classList.add('selected');
        retBtn.disabled = false;
        delBtn.disabled = false;
        cols = row.querySelectorAll('td');
        pId = cols[0].innerText;
        tId = cols[1].innerText;
        sId = cols[2].innerText;
    }
});

retBtn.addEventListener('click', async () => {
    await deleteTicket();
    await updateSeat();

    retBtn.disabled = true;
    delBtn.disabled = true;
    passTab.innerHTML = '';
});

delBtn.addEventListener('click', async () => {
    await deletePass();
    updateSeats();


    retBtn.disabled = true;
    delBtn.disabled = true;
    passTab.innerHTML = '';
})

function updateSeats() {
    passList.forEach(async (pass) => {
        if(pass[0] == pId) {
            sId = pass[2];

            console.log(sId);
        }
    });
}

async function deletePass() {
    let response = await fetch("http://localhost:8082/passenger/deletePassenger/" + pId, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);
}

async function deleteTicket() {
    let response = await fetch("http://localhost:8082/ticket/deleteTicket/" + tId, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    alert(response.status);
}

async function updateSeat() {
    let response = await fetch("http://localhost:8082/seatCard/getSeatCard/" + sId, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    let seat = await response.json();

    seat.isBusy = false;

    response = await fetch("http://localhost:8082/seatCard/updateSeatCard/" + sId, {
        method: 'PATCH',
        body: JSON.stringify(seat),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
}