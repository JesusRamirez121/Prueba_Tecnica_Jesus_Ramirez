/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.7391821207798384, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.6131855309218203, 500, 1500, "Demo.qa3"], "isController": false}, {"data": [0.9974141984015045, 500, 1500, "Demo.qa2-1"], "isController": false}, {"data": [0.8413068844807468, 500, 1500, "Demo.qa3-0"], "isController": false}, {"data": [0.8958627174424072, 500, 1500, "Demo.qa2-0"], "isController": false}, {"data": [0.9945546479968884, 500, 1500, "Demo.qa3-1"], "isController": false}, {"data": [0.8033521407235313, 500, 1500, "Demo.qa-0"], "isController": false}, {"data": [0.6440424825755061, 500, 1500, "Demo.qa-1"], "isController": false}, {"data": [0.36425489545303685, 500, 1500, "Demo.qa"], "isController": false}, {"data": [0.6194170192759756, 500, 1500, "Demo.qa2"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 23133, 0, 0.0, 648.9152293260729, 162, 6845, 438.0, 1295.0, 1856.0, 4499.960000000006, 28.44586211263986, 70.87390254236506, 4.296510423263312], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Demo.qa3", 2571, 0, 0.0, 730.6907817969674, 326, 6660, 625.0, 1148.8000000000002, 1344.0, 1961.000000000055, 3.1670557221958746, 11.836252196136341, 0.7175360620600029], "isController": false}, {"data": ["Demo.qa2-1", 2127, 0, 0.0, 199.22284908321564, 162, 1484, 181.0, 232.0, 260.0, 308.0, 2.62072653390126, 8.793765986801494, 0.29687917766850214], "isController": false}, {"data": ["Demo.qa3-0", 2571, 0, 0.0, 529.0898483080506, 163, 5565, 430.0, 927.6000000000004, 1140.4, 1558.8800000000042, 3.167754208886961, 1.2095623981199237, 0.3588471564754761], "isController": false}, {"data": ["Demo.qa2-0", 2127, 0, 0.0, 420.3117066290553, 162, 1524, 418.0, 642.2, 716.7999999999997, 1344.0, 2.6202809995749896, 1.0005174519861533, 0.2968287069831043], "isController": false}, {"data": ["Demo.qa3-1", 2571, 0, 0.0, 201.53442240373374, 162, 1499, 182.0, 232.0, 264.4000000000001, 759.9600000000014, 3.1684764323768753, 10.631723653952092, 0.3589289708551928], "isController": false}, {"data": ["Demo.qa-0", 3013, 0, 0.0, 526.0886159973451, 163, 4617, 464.0, 855.0, 929.0, 1224.1600000000008, 3.7090106924881763, 1.416233574963747, 0.4201613675084262], "isController": false}, {"data": ["Demo.qa-1", 3013, 0, 0.0, 904.0716893461646, 163, 5942, 291.0, 1935.0, 2052.2999999999997, 5411.4400000000005, 3.709796176681351, 12.448105139723754, 0.42025034813968437], "isController": false}, {"data": ["Demo.qa", 3013, 0, 0.0, 1430.3481579820827, 327, 6845, 926.0, 2834.3999999999996, 2981.5999999999995, 6301.72, 3.70819359404326, 13.85864930117535, 0.8401376111504262], "isController": false}, {"data": ["Demo.qa2", 2127, 0, 0.0, 619.5829807240233, 327, 2350, 617.0, 828.2, 907.0, 1622.7199999999998, 2.6197033219858015, 9.790629505116858, 0.5935265338874082], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 23133, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
