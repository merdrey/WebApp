const list = document.getElementById('routesList');
const updateBtn = document.getElementById('updateRtBtn');
const createBtn = document.getElementById('createRtBtn');
const deleteBtn = document.getElementById('deleteRtBtn');
const addStToRtBtn = document.getElementById('addStToRtBtn');
const upStToRtBtn = document.getElementById('upStToRtBtn');
const delStToRtBtn = document.getElementById('delStToRtBtn');
const form = document.getElementById('routeSt');
const typeSelector = document.getElementById('sttype');
const stationSelector = document.getElementById('station');
const timetable = document.getElementById('timetable');

let curRoute, stations, routeStations, stationRtId, stationObj, token = sessionStorage.getItem('token');

function buildRouteList(entries) {
    if(list.hasChildNodes()) {
        list.innerHTML = '';
    }
    entries.forEach(entry => {
        let li = document.createElement('li');
        li.innerHTML = "Маршрут " + entry.id;
        list.appendChild(li);
    });
}

function buildStationSelect(entries) {
    entries.forEach(entry => {
        let option = document.createElement('option');
        option.innerHTML = entry.nameSt;
        stationSelector.appendChild(option);
    })
}

function buildTimetable(entries) {
    if(timetable.hasChildNodes()) {
        timetable.innerHTML = '';
    }

    let thead = timetable.createTHead();
    let row = thead.insertRow();
    let cellStationName = row.insertCell();
    let cellArritveDate = row.insertCell();
    let cellArriveTime = row.insertCell();
    let cellDepartDate = row.insertCell();
    let cellDepartTime = row.insertCell();

    cellStationName.innerHTML = 'Станция';
    cellArritveDate.innerHTML = 'Дата прибытия';
    cellArriveTime.innerHTML = 'Время прибытия';
    cellDepartDate.innerHTML = 'Дата отправления';
    cellDepartTime.innerHTML = 'Время отправления';

    let tbody = timetable.createTBody();

    Array.from(entries).forEach((entry) => {
        let stationName;
        const {stationId, arriveDate, arriveTime, departDate, departTime} = entry;
        stations.forEach((station) => {
            if(station.id == stationId) {
                stationName = station.nameSt;
            }
        });

        row = tbody.insertRow();
        cellStationName = row.insertCell();
        cellArritveDate = row.insertCell();
        cellArriveTime = row.insertCell();
        cellDepartDate = row.insertCell();
        cellDepartTime = row.insertCell();

        cellStationName.innerHTML = stationName;
        cellArritveDate.innerHTML = arriveDate == null ? '-' : arriveDate;
        cellArriveTime.innerHTML = arriveTime == null ? '-' : arriveTime;
        cellDepartDate.innerHTML = departDate == null ? '-' : departDate;
        cellDepartTime.innerHTML = departTime == null ? '-' : departTime;
    })
}

function serializeStationData(data) {
    const { elements } = data;
    
    const formattedData = new FormData();

    Array.from(elements).forEach(element => {
        const {name, value} = element;

        if (name != "" && name != "type") {
            if (name == "station") {
                let stationId;
                stations.forEach((station) => {
                    if(station.nameSt == value) {
                        stationId = station.id;
                    }
                });
                formattedData.append('stationId', stationId);
            }
            else {
                formattedData.append(name, value);
            }
        }
    });

    formattedData.append('routeId', curRoute);

    return JSON.stringify(Object.fromEntries(formattedData));
    
}

function parseStationIntoForm(data) {
    let stationId;
    stations.forEach((station) => {
        if(station.nameSt == data[0].innerText) {
            stationId = station.id;
        }
    });
    
    routeStations.forEach((rtStation) => {
        if(rtStation.stationId == stationId) {
            stationObj = rtStation;
        }
    });
    
    stationRtId = stationObj.id;

    Array.from(stationSelector.options).forEach((option) => {
        if(option.innerText == data[0].innerText) {
            option.selected = true;
        }
    });

    if(stationObj.arriveDate == null && stationObj.arriveTime == null) {
        typeSelector.selectedIndex = 0;
    }
    else if(stationObj.departDate == null && stationObj.departTime == null) {
        typeSelector.selectedIndex = 2;
    }
    else {
        typeSelector.selectedIndex = 1;
    }
    typeSelector.dispatchEvent(new Event('change'));

    document.getElementById('arrdateInp').value = stationObj.arriveDate;
    document.getElementById('arrtimeInp').value = stationObj.arriveTime;
    document.getElementById('depdateInp').value = stationObj.departDate;
    document.getElementById('deptimeInp').value = stationObj.departTime;
}

async function sendStation(data) {
    let response = await fetch("http://localhost:8082/routeSt/createRouteStation", {
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

async function getRouteStations() {
    let response = await fetch("http://localhost:8082/routeSt/getStationsForRoute/" + curRoute, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
    return await response.json();
}

function checkLines(lines) {
    lines.forEach((line) => {
        if(line.classList.contains('active')) {
            line.classList.remove('active');
        }
    });
}

function checkRows(rows) {
    rows.forEach((row) => {
        if(row.classList.contains('selected')) {
            row.classList.remove('selected');
        }
    });
}

async function loadRoutes() {
    let response = await fetch("http://localhost:8082/route/getAllRoutes", {
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
    buildRouteList(await response.json());
}

async function refreshTable() {
    routeStations = await getRouteStations();
    buildTimetable(routeStations);
    document.body.insertBefore(timetable, list.nextSibling);
}

function refreshForm() {
    upStToRtBtn.disabled = true;
    delStToRtBtn.disabled = true;
    let index = typeSelector.selectedIndex;
    form.reset();
    typeSelector.selectedIndex = index;
}

document.addEventListener('DOMContentLoaded', async () => {
    await loadRoutes();

    let response = await fetch("http://localhost:8082/getAllStations", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    stations = await response.json();

    buildStationSelect(stations);
});

list.addEventListener('click', async (e) => {
    if(e.target.nodeName == 'LI') {
        checkLines(list.querySelectorAll('li'));
        e.target.classList.add('active');
        curRoute = e.target.innerHTML.split(" ")[1];
        addStToRtBtn.disabled = false;
        deleteBtn.disabled = false;
        await refreshTable();
    }
});

createBtn.addEventListener('click', async () => {
    let obj = {
    };
    await fetch("http://localhost:8082/route/createRoute", {
        method: 'POST',
        body: JSON.stringify(obj),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
    await loadRoutes();
});

deleteBtn.addEventListener('click', async () => {
    deleteBtn.disabled = true;
    await fetch("http://localhost:8082/route/deleteRoute/" + curRoute, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
    await loadRoutes();
});

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    let data = serializeStationData(e.target);
    await sendStation(data);
    await refreshTable();
    refreshForm();
});

timetable.addEventListener('click', (e) => {
    let rows = Array.from(timetable.getElementsByTagName('tbody')[0].getElementsByTagName('tr'));
    rows.forEach((row) => {
        let cells = Array.from(row.getElementsByTagName('td'));
        cells.forEach((cell) => {
            if(e.target == cell) {
                checkRows(rows);
                row.classList.add('selected');
                upStToRtBtn.disabled = false;
                delStToRtBtn.disabled = false;
                parseStationIntoForm(cells);
            }
        });
    });
});

delStToRtBtn.addEventListener('click', async () => {
    await fetch("http://localhost:8082/routeSt/deleteRouteStation/" + stationRtId, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    await refreshTable();
    refreshForm();
});

upStToRtBtn.addEventListener('click', async () => {
    let data = serializeStationData(form);

    await fetch("http://localhost:8082/routeSt/updateRouteStation/" + stationRtId, {
        method: 'PATCH',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
    
    await refreshTable();
    refreshForm();
});

typeSelector.addEventListener('change', () => {
    switch (typeSelector.selectedIndex) {
        case 0: //start
            document.getElementById('arrdateInp').required = false;
            document.getElementById('arrdate').classList.add('hidden');
            document.getElementById('arrtimeInp').required = false;
            document.getElementById('arrtime').classList.add('hidden');
            document.getElementById('depdateInp').required = true;
            document.getElementById('depdate').classList.remove('hidden');
            document.getElementById('deptimeInp').required = true;
            document.getElementById('deptime').classList.remove('hidden');
            break;
        case 1: //middle
            document.getElementById('arrdateInp').required = true;
            document.getElementById('arrdate').classList.remove('hidden');
            document.getElementById('arrtimeInp').required = true;
            document.getElementById('arrtime').classList.remove('hidden');
            document.getElementById('depdateInp').required = true;
            document.getElementById('depdate').classList.remove('hidden');
            document.getElementById('deptimeInp').required = true;
            document.getElementById('deptime').classList.remove('hidden');
            break;

        case 2: //end
            document.getElementById('arrdateInp').required = true;
            document.getElementById('arrdate').classList.remove('hidden');
            document.getElementById('arrtimeInp').required = true;
            document.getElementById('arrtime').classList.remove('hidden');
            document.getElementById('depdateInp').required = false;
            document.getElementById('depdate').classList.add('hidden');
            document.getElementById('deptimeInp').required = false;
            document.getElementById('deptime').classList.add('hidden');
            break;
    }
});