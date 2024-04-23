jQuery.noConflict();
    function selectR(event, element) {
       event.preventDefault();
       $('.r-btn').removeClass('selected');
       $(element).addClass('selected');
       var selectedR = $(element).text();
       $('#myForm\\:selectedR').val(selectedR);
       return false;
    }
$(function(){
        function checkY(y) {
            y = y.replace(',', '.');
            if (isNaN(y) || y < -3 || y > 3 || y === "") {
                return true;
            }
            return false;
        }

        function checkX(x) {
            var validValuesX = [-4, -3.5, -3, -2.5, -2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4];
            x = parseFloat(x);
            if (isNaN(x) || x < -4 || x > 4 || x === "" || !validValuesX.includes(x)) {
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
    var oldR=1;
    const tableBody= $('#table');
    var canvas =document.getElementById("area");
    var ctx = canvas.getContext("2d");

    function loadTableData() {
        const tableData = localStorage.getItem('tableData');
        if (tableData) {tableBody.html(tableData);}  }

    function saveTableData() {
        const tableData = tableBody.html();
        localStorage.setItem('tableData', tableData); }

    function updateTable(x, y, r) {
        sendDataToServer([{name: 'x', value: x}, {name: 'y', value: y}, {name: 'r', value: r}]);
        }

function drawAll(){
    // polygon
    ctx.fillStyle = "blue";
    ctx.beginPath();
    ctx.moveTo(260, 260);
    ctx.lineTo(260, 325);
    ctx.lineTo(195, 260);
    ctx.fill();

    // circle
    ctx.beginPath();
    ctx.moveTo(260, 260);
    ctx.arc(260, 260, 65, Math.PI, 3 * Math.PI / 2, false);
    ctx.lineTo(260, 260);
    ctx.closePath();
    ctx.fill();


    // rectangle
    ctx.fillStyle = "blue";
    ctx.fillRect(260, 195, 32.5, 65);

    // x-line and y-line
    ctx.beginPath();
    ctx.moveTo(0, 260);
    ctx.lineTo(520, 260);
    ctx.moveTo(260, 0);
    ctx.lineTo(260, 520);
    ctx.stroke();

function drawLineY(x, y, id) {
    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.lineTo(x + 10, y);
    ctx.strokeStyle = "grey";
    ctx.stroke();
    ctx.closePath();
}

function drawTextX(x, y, text, id) {
    var textX = x;
    ctx.fillStyle = "black";
    ctx.font = "12px Arial";
    ctx.textAlign = "start";
    ctx.fillText(text, textX, y);
}

function drawLineX(x, y, id) {
    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.lineTo(x, y + 10);
    ctx.strokeStyle = "grey";
    ctx.stroke();
    ctx.closePath();
}

function drawTextY(x, y, text, id) {
    var textY = y;
    ctx.fillStyle = "black";
    ctx.font = "12px Arial";
    ctx.textAlign = "start";
    ctx.fillText(text, x , textY);
}

drawLineX(195, 255, "rxTextNeg");
drawLineX(227.5, 255, "rxTextNegHalf");
drawLineX(292.5, 255, "rxTextHalf");
drawLineX(325, 255, "rxText");

drawTextX(195, 255, "-R", "rxTextNeg");
drawTextX(227.5, 255, "-R/2", "rxTextNegHalf");
drawTextX(292.5, 255, "R/2", "rxTextHalf");
drawTextX(325, 255, "R", "rxText");

drawLineY(255, 195, "ryText");
drawLineY(255, 227.5, "ryTextHalf");
drawLineY(255, 292.5, "ryTextNegHalf");
drawLineY(255, 325, "ryTextNeg");

drawTextY(255, 195, "R", "ryText");
drawTextY(255, 227.5, "R/2", "ryTextHalf");
drawTextY(255, 292.5, "-R/2", "ryTextNegHalf");
drawTextY(255, 325, "-R", "ryTextNeg");


function drawTextAndLine(ctx, x, y, textId, labelText) {
    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.lineTo(x, y);
    ctx.stroke();

    var textElement = document.getElementById(textId);
    ctx.font = "10px Arial";
    ctx.fillText(labelText, x, textElement ? textElement.getAttribute("y") : y);
    }
}
    drawAll();

    loadTableData();

    $("#area").click(function(event){
            $("#error").text('');
            var x = (event.offsetX - 260) / (65);
            var y = (260 - event.offsetY) / (65);
            var r= $('.r-btn.selected').text();

            if(!r) {
                $("#error").text("Выберите r");
                return false;
            }

            var xr=x*r;
            var yr=y*r;

//            if (checkX(xr)) {
//                 $('#error').text('Выберите x от -4 до 4. Ваше значение:'+xr);
//                 return;
//            }

             if(xr<-4 || xr>4){
                $('#error').text('Введите x от -4 до 4');
                return;
             }

            if (checkY(yr.toString())) {
                $('#error').text('Введите y от -3 до 3');
                return;
            }

            if (checkR(r)) {
                 $('#error').text('Некорректный r');
                 return;
            }

            console.log("Сохранение в массив клик "+x);
            var newPoint = {x: x, y: y, r: r};
            var points = JSON.parse(localStorage.getItem("points")) || [];
            points.push(newPoint);
            localStorage.setItem("points", JSON.stringify(points));

            ctx.beginPath();
            ctx.arc(x * 65.0 + 260.0, 260.0 - y * 65.0, 3, 0, 2 * Math.PI);
            ctx.fillStyle = "red";
            ctx.fill();

            x *= r;
            y *= r;

        updateTable(x, y, r);
    });

    $('.r-btn').click(function(){
        var r= parseFloat($(this).text());

        $('.r-btn').removeClass('selected');
        $(this).addClass('selected');

        var canvas =document.getElementById("area");
        var context = canvas.getContext("2d");

        context.clearRect(0, 0, canvas.width, canvas.height);
        drawAll();

        var points = JSON.parse(localStorage.getItem("points")) || [];

        points.forEach(function(point, i) {
            var scaledX, scaledY;
            point.x=parseFloat(point.x);
            point.y=parseFloat(point.y);

            scaledX = point.x / (r / oldR);
            scaledY = point.y / (r / oldR);


            points[i].x = scaledX;
            points[i].y = scaledY;

            context.beginPath();
            context.arc(scaledX * 65.0 + 260.0, 260.0 - scaledY * 65.0, 3, 0, 2 * Math.PI);
            context.fillStyle = "red";
            context.fill();
            context.closePath();
        });

        localStorage.setItem("points", JSON.stringify(points));
        oldR = r;
    });

        $('#myForm').submit(function(event) {
            event.preventDefault();
            $('#error').text('');

            var xx = $('#myForm\\:X_input').val();
            var y = $('.input-y').val();
            var r = $('.r-btn.selected').text(); // Получаем выбранное значение R

            var x = parseFloat(xx.replace(',', '.'));
            if (checkX(x)) {
                $('#error').text('Введите x от -4 до 4(шаг 0.5)');
                return;
            }

            if (checkY(y)) {
                  $('#error').text('Введите y от -3 до 3');
                  return;
            }
            y = y.replace(',', '.');

            if (checkR(r)) {
                 $('#error').text('Некорректный r');
                 return;
            }

            var newPoint = {x: x/r, y: y/r, r: r};

            var points = JSON.parse(localStorage.getItem("points")) || [];
            points.push(newPoint);
            localStorage.setItem("points", JSON.stringify(points));


                    var canvas = document.getElementById("area");
                    var ctx = canvas.getContext("2d");


                    var scaledX = x / r;
                    var scaledY = y / r;


                    ctx.beginPath();
                    ctx.arc(scaledX * 65.0 + 260.0, 260.0 - scaledY * 65.0, 3, 0, 2 * Math.PI);
                    ctx.fillStyle = "red";
                    ctx.fill();
                    ctx.closePath();

                    updateTable(x, y, r);
                    $('#myForm\\:X_input').val("");
                    $('.input-y').val("");
        });

});