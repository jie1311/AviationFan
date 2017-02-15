

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawSeriesChart);

function drawSeriesChart() {

    var jsonData = $.ajax({
        url: "getAircraft",
        dataType: "json",
        async: false
    }).responseText;

    var arr = JSON.parse(jsonData);

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Type');
    data.addColumn('number', 'Range');
    data.addColumn('number', 'Capacity');
    data.addColumn('string', 'Manufacturer');
    data.addColumn('number', 'Capacity');

    for (var i = 0; i < arr.length; i++) {
        data.addRow([arr[i].model + '-' + arr[i].submodel, arr[i].range, arr[i].capacity, arr[i].manufacturer, arr[i].capacity]);
    }

    var options = {
        hAxis: {
            minValue: 8000,
            maxValue: 20000,
            title: 'Range'},
        vAxis: {
            minValue: 200,
            maxValue: 450,
            title: 'Capacity'},
        sizeAxis: {
            minSize: 10,
            maxSize: 12,
            sortBubblesBySize: true},
        bubble :{
            textStyle: {
                fontSize: 8
            }
        }
    };

    var chart = new google.visualization.BubbleChart(document.getElementById('series_chart_div'));
    chart.draw(data, options);
}

//<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
//<div id="series_chart_div" style="width: 100%; height: 400px;"></div>