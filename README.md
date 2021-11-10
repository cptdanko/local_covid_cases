# Lcoal Covid Cases
This is a simple web app that was a weekend hack that tells you the the number of Covid cases in your suburb in Sydney, New South Wales.

## Tech Stack
- Java 11
- Spring boot
- Lombok
- Vanilla Javascript ( no framework ) 

## How to run
You can also run it from the command line (or command prompt for windows users).
```ssh
git clone https://github.com/cptdanko/localCovidCases.git
cd localCovidCases
mvn clean install
mvn spring-boot:run
open browser and enter http://localhost:5000/
```

### How to run in IDE
This project has only been tested in IntelliJ CE IDE.
1. Clone the repository
2. Launch IntelliJ
3. Import the project
4. Click on run from the IDE interface to launch the app
5. Enter http://localhost:5000/ in your browser

You are free to use whichever IDE you prefer. Should you use a different IDE then please create a document with setup instructions for that IDE and create a PR for this repo.

## Data source with example
This project uses the data source [Covid cases by likely source of infection] from the NSW health website. 
```text
2020-03-02,2077,Locally acquired - linked to known case or cluster,X760,Northern Sydney,14000,Hornsby (A)
2020-03-02,2073,Locally acquired - no links to known case or cluster,X760,Northern Sydney,14500,Ku-ring-gai (A)
2020-03-02,2217,Overseas,X720,South Eastern Sydney,10500,Bayside (A)
```
## Endpoints

The API exposes 3 endpoints
- /raw 
- /byPostcode/{postcode}
- /byPostcode/{postcode}/aggregate


## The UI
The current UI uses the last endpoint to retrieve and display the data. The UI has an index.html file and index.js file that 
has the Javascript code to generate the UI elements. Here's the code to dynamicall generate the UI

```javascript
    const parent = document.getElementById("summaryCards");
    parent.innerHTML = "";
    //we should have a map of case numbers by date
    for (const [key, value] of dateMap) {
      const card = document.createElement("div");
      card.className = "resultCard";

      const summaryDiv = document.createElement("div");
      summaryDiv.style.textAlign = "center";
      summaryDiv.innerHTML = `<p> <b>${value.total}</b> case(s) on
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
```
## Java code

```java
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CasesByDate {
    private Date date;
    private int count;
    private String lhdName;
    private String source;
}
```
See Lombok in action above
[This method of the source code] shows the logic to aggregate parsed data.

# More great tutorials and code samples
Checkout [this blog] for more "how-to" code samples, tutorials and just info on how to solve problems.

If you like what I am doing, you can [buy me a coffee]

Click on the next link for more info on the 13+ year [software engineering career journey] of the author.

[buy me a coffee]: https://www.buymeacoffee.com/bhumansoni
[software engineering career journey]: https://mydaytodo.com/the-3-stages-of-a-software-engineering-career/
[this blog]: https://mydaytodo.com/blog/
[This method of the source code]: https://github.com/cptdanko/localCovidCases/blob/1d3fc314a27c5430cad8c1c976d745e1ffa57c58/src/main/java/com/mydaytodo/covid/service/CSVParserImpl.java#L77
[Covid cases by likely source of infection]: https://data.nsw.gov.au/search/dataset/ds-nsw-ckan-97ea2424-abaf-4f3e-a9f2-b5c883f42b6a/details?q=
