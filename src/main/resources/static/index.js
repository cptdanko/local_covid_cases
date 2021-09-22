async function getData() {
    const postcode = document.getElementById("inPostcode").value;
    const url = `/data/byPostcode/${postcode}`;
    let cases = await fetch(url)
    .then(response => response.json())
    .then(data => data.covidCases);
    renderData(cases);
}
async function getAggregate() {
    const postcode = document.getElementById("inPostcode").value;
    const url = `/data/byPostcode/${postcode}/aggregate`;
    let cases = await fetch(url)
    .then(response => response.json())
    .then(data => data);
    renderAggregate(cases);
}
function renderAggregate(data) {
    const reducer = (a, b) => a + b.count;
    let casesCount = data.reduce(reducer, 0);
    let header = document.createElement("h3");
    header.innerHTML = `Total cases: ${casesCount}`;
    const tableHeader = document.getElementById("tableHeading");
    tableHeader.innerHTML = "";
    tableHeader.appendChild(header);

    const dateMap = new Map();
    data.forEach(dt => {
        let casesObj = dateMap.get(dt.date);
        if(casesObj === null || casesObj === undefined) {
            casesObj = {};
            casesObj.dataPoints = [];
            casesObj.date = dt.date;
            casesObj.total = 0;
        }
        casesObj.total += dt.count;
        casesObj.dataPoints.push(dt);
        dateMap.set(dt.date, casesObj);
    });
    const parent = document.getElementById("summaryCards");
    parent.innerHTML = "";
    //we should have a map of case numbers by date
    for (const [key, value] of dateMap) {
      const card = document.createElement("div");
      card.className = "resultCard";

      const summaryDiv = document.createElement("div");
      summaryDiv.style.textAlign = "center";
      summaryDiv.innerHTML = `<p> <b>${value.total}</b> cases on
                              <b>${formatDate(key)}</b></p>
                               <hr />`;
      card.appendChild(summaryDiv);
      const dataPointsDiv = document.createElement("div");
      value.dataPoints.forEach(dp => {
        const detailDiv = document.createElement("div");
        detailDiv.innerHTML = `<p> <b>${dp.count}</b> cases are
                                <b><i>${dp.source}</i> </b> </p>
                                <hr />`;
        card.appendChild(detailDiv);
      });
      parent.appendChild(card);

    }
}
function renderData(cases) {
    const parentToHeader = document.getElementById("tableHeading");
    let header = document.createElement("h3");
    header.innerHTML = `Total cases: ${cases.length}`;
    parentToHeader.appendChild(header);
    const parent = document.getElementById("caseNumbers");
    parent.innerHTML = "";

    const headerRow = createTableHeader(["Date", "Likely Source"]);
    parent.appendChild(headerRow);
    cases.forEach(c => {
        const row = document.createElement("div");
        row.className = "row";
        let caseDate = formatDate(c.notification_date)
        const v1 = document.createElement("div");
        v1.className = "col";
        v1.innerHTML = `<span> ${caseDate} </span>`;
        row.appendChild(v1);

        const v2 = document.createElement("div");
        v2.innerHTML = `<span> ${c.likely_source_of_infection} </span>`;
        v2.className = "col";
        row.appendChild(v2);
        parent.appendChild(row);
    });
}
function createTableHeader(headers) {
    const headerRow = document.createElement("div");
    headerRow.className = "row";
    headers.forEach(header => {
        const col = document.createElement("div");
        col.className = "col";
        col.innerHTML = `<h3> ${header} </h3>`;
        headerRow.appendChild(col);
    });
    return headerRow;
}
function toggleDetails() {
    let display = document.getElementById("summaryCards").style.display;
    if(display === 'none') {
        document.getElementById("summaryCards").style.display = "flex";
    } else {
        document.getElementById("summaryCards").style.display = "none";
    }
}
function formatDate(date) {
    let d = new Date(date);
    let ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d);
    let mo = new Intl.DateTimeFormat('en', { month: 'short' }).format(d);
    let da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d);
    let caseDate = `${da}-${mo}-${ye}`;
    return caseDate
}