$(document).ready(function () {
    const tableResults = $(".tableResults");
    const tableBody = $('#results tbody');
    var oldR=0;

    // Загрузка данных из localStorage
    function loadTableData() {
        const tableData = localStorage.getItem('tableData');
        if (tableData) {
            tableBody.html(tableData);
        }
    }

    // Сохранение данных таблицы в localStorage
    function saveTableData() {
        const tableData = tableBody.html();
        localStorage.setItem('tableData', tableData);
    }

    function checkX(x) {
        x = x.replace(',', '.');
        if (isNaN(x) || x < -5 || x > 5 || x === "") {
            return true;
        }
        return false;
    }

    function checkY(y) {
        y = y.replace(',', '.');
        if (isNaN(y) || y < -5 || y > 3 || y === "") {
            return true;
        }
        return false;
    }

    function checkR(r) {
        var validValuesR = [1, 1.5, 2, 2.5, 3];
        r = parseFloat(r);
        if (r !== undefined && !isNaN(parseFloat(r)) && validValuesR.includes(r)) {
            if (r >= 1 && r <= 3) {
                return false;
            }
        }
        return true;
    }

    function removeRText() {
        $("#rxText").text('r');
        $("#rxTextNeg").text('-r');
        $("#rxTextNegHalf").text('-r/2');
        $("#rxTextHalf").text('r/2');
        $("#ryText").text('r');
        $("#ryTextNeg").text('-r');
        $("#ryTextNegHalf").text('-r/2');
        $("#ryTextHalf").text('r/2');
    }

    function addCurrentRText(r) {
        $("#rxText").text(r);
        $("#rxTextNeg").text(-r);
        $("#rxTextNegHalf").text(-r / 2);
        $("#rxTextHalf").text(r / 2);
        $("#ryText").text(r);
        $("#ryTextNeg").text(-r);
        $("#ryTextNegHalf").text(-r / 2);
        $("#ryTextHalf").text(r / 2);
    }

    // Загрузка данных из localStorage при загрузке страницы
    loadTableData();

    $('#clearButton').click(function () {
        localStorage.removeItem('points');
        tableBody.empty();
        $(".area circle").remove();
        removeRText();
        // Очистка данных таблицы и сохранение в localStorage
        tableBody.html('');
        saveTableData();
    });

    $('#showButton').click(function () {
        tableResults.toggle();
    });

    $(".area").click(function (event) {
        $('#error').text('');
        if ($('#x').val() && $('#y').val()) {
            $('#error').text('Ваши поля не пустые');
            return;
        }
        var r = $("input[name='r']:checked").val();
        if (r === undefined) {
            $('#error').text('Выберите r');
            return;
        }

        var x = (event.offsetX - 260) / (52);
        var y = (260 - event.offsetY) / (52);
        var newPoint = {x: x, y: y, r: r};

        var xr=x*r;
        var yr=y*r;
        if (checkX(xr.toString())) {
            $('#error').text('Введите x от -5 до 5');
            console.log(xr);
            return;
        }

        if (checkY(yr.toString())) {
            $('#error').text('Введите y от -5 до 3');
            return;
        }

        if (checkR(r)) {
            $('#error').text('Выберите r');
            return;
        }

        var points = JSON.parse(localStorage.getItem("points")) || [];
        points.push(newPoint);
        localStorage.setItem("points", JSON.stringify(points));

        var svg = $(".area");
        var circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        circle.setAttribute("cx", x * 52.0 + 260.0);
        circle.setAttribute("cy", 260.0 - y * 52.0);
        circle.setAttribute("r", 3);
        circle.setAttribute("fill", "red");
        svg.append(circle);

        x *= r;
        y *= r;

        updateTable(x, y, r);
    });

    $("input[name='r']").change(function () {
        var r = $(this).val();
        $(".area circle").remove();

        addCurrentRText(r);

        var points = JSON.parse(localStorage.getItem("points")) || [];

        points.forEach(function (point,i) {
            var svg = $(".area");
            var circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");

            var scaledY,scaledX;

            if(oldR<r){
                scaledX = point.x / r;
                scaledY = point.y / r;
            } else{
                scaledX = point.x *oldR;
                scaledY = point.y *oldR;
            }

            points[i].x = scaledX;
            points[i].y = scaledY;

            circle.setAttribute("cx", scaledX * 52.0 + 260.0);
            circle.setAttribute("cy", 260.0 - scaledY * 52.0);
            circle.setAttribute("r", 3);
            circle.setAttribute("fill", "red");
            svg.append(circle);
        });

        localStorage.setItem("points", JSON.stringify(points));
        oldR=r;
    });

    function updateTable(x, y, r) {
        $.ajax({
            method: 'post',
            url: 'controller',
            data: {x: x, y: y, r: r},
            success: function (response) {
                if (response.startsWith('Error')) {
                    $('#error').text(response);
                } else {
                    tableBody.append(response);
                    // Сохранение данных таблицы в localStorage после обновления
                    saveTableData();
                }
            }
        });
    }

    $("#submitButton").click(function (event) {
        event.preventDefault();
        $('#error').text('');
        var x = $("#x").val();
        var y = $("#y").val();
        var r = $("input[name='r']:checked").val();

        x = x.replace(',', '.');
        y = y.replace(',', '.');

        if (checkX(x)) {
            $('#error').text('Введите x от -5 до 5');
            return;
        }

        if (checkY(y)) {
            $('#error').text('Введите y от -5 до 3');
            return;
        }

        if (checkR(r)) {
            $('#error').text('Выберите r');
            return;
        }

        var newPoint = {x: x, y: y, r: r};

        var points = JSON.parse(localStorage.getItem("points")) || [];
        points.push(newPoint);
        localStorage.setItem("points", JSON.stringify(points));

        points.forEach(function (point) {
            var svg = $(".area");
            var circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
            var scaledX = point.x / r;
            var scaledY = point.y / r;

            circle.setAttribute("cx", scaledX * 52.0 + 260.0);
            circle.setAttribute("cy", 260.0 - scaledY * 52.0);
            circle.setAttribute("r", 3);
            circle.setAttribute("fill", "red");
            svg.append(circle);
        });

        $("#x").val("");
        $("#y").val("");


        updateTable(x, y, r);
    });
});
