const BUTTON = document.getElementById("but");
let table = document.getElementById("allStations"), tbody, rows, info, id;
let token = sessionStorage.getItem('token');

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

function buildTable(data) {
    table.classList.remove("hidden");
    if(table.hasChildNodes()) {
        table.innerHTML = '';
    }
    if(info != undefined && document.body.contains(info)) {
        document.body.removeChild(info);
    }
    thead = table.createTHead();
    let row = thead.insertRow();
    let cell1 = row.insertCell();
    let cell2 = row.insertCell();
    cell1.innerHTML = "id станции"
    cell2.innerHTML = "Название станции"

    tbody = table.createTBody();

    Array.from(data).forEach((element) => {
        const {id, nameSt} = element;
        row = tbody.insertRow();
        cell1 = row.insertCell();
        cell1.innerHTML = id;
        cell2 = row.insertCell();
        cell2.innerHTML = nameSt;
    });
}

function deleteOrUpdateElements(rows) {
    rows.forEach((row) => {
        row.addEventListener('click', () => {
            document.getElementById('buttons').classList.remove("hidden");
            id = row.cells[0].innerHTML;
        });
    });
}

async function sendData(data) {
    return await fetch("http://localhost:8082/createStation", {
        method: 'POST',
        body: data,
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });
}

document.getElementById("stChName").addEventListener('input', () => document.getElementById('update').disabled = false);

document.getElementById('delete').addEventListener('click', async () => {                
    let response = await fetch("http://localhost:8082/deleteStation/" + id, {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    })
    document.getElementById('buttons').classList.add("hidden");
    if(response.ok) {
        info = document.createElement('p');
        info.innerHTML = "Станция удалена";
        document.body.appendChild(info);
        table.classList.add('hidden');
    }
});

document.getElementById('update').addEventListener('click', async () => {
    let obj = {
        nameSt: document.getElementById("stChName").value
    };
    let response = await fetch("http://localhost:8082/updateStation/" + id, {
        method: 'PATCH',
        body: JSON.stringify(obj),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    })
    document.getElementById('buttons').classList.add("hidden");
    document.getElementById('update').disabled = true;
    document.getElementById('stChName').value = '';
    if(response.ok) {
        info = document.createElement('p');
        info.innerHTML = "Станция обновлена";
        document.body.appendChild(info);
        table.classList.add('hidden');
    }
});

BUTTON.addEventListener("click", async () => {
    let response = await fetch("http://localhost:8082/getAllStations", {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': "http://localhost:8082",
            Authorization: 'Bearer ' + token
        }
    });

    if (response.ok) { 
        buildTable(await response.json());
        deleteOrUpdateElements(document.querySelectorAll('tbody tr'));

    } else {
        alert("Ошибка HTTP: " + response.status);
    }
});

const input = document.forms.station;
input.addEventListener("submit", async (e) => {
    e.preventDefault();
    let data = serializeData(e.target);
    await sendData(data);
    e.target.reset();
});
