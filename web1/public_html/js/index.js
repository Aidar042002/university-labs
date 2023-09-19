$(function(){

    function checkX(x) {
        x = x.replace(',', '.');
        x = parseFloat(x);
        if (isNaN(x) || x < -5 || x > 5) {
            return true;
        }
        return false;
    }

    function checkY(y){
        y = parseFloat(y);
        if (isNaN(y) || y < -3 || y > 5) {
            return true;
        }
        return false;
    }

    function checkR(r){
        if (r !== undefined && !isNaN(parseFloat(r))) {
            r = parseFloat(r);
            if (r>=1 && r <=3) {
                return false;
            }
        }

        return true;
    }

    $('#sendButton').click(function(event){
        event.preventDefault();

        var x=$('#x').val();
        var y=$('#y').val();
        var r=$('input[type="radio"]:checked').val();

        if (checkX(x)) {
            $('#notification').text('Введите x от -5 до 5');
            return;
        }

        if (checkY(y)){
            $('#notification').text('Выберите y');
            return;
        }

        if(checkR(r)){
            $('#notification').text('Выберите r');
            return;
        }

        $.ajax({
            url:'php/index.php',
            method: 'GET',
            data: {x:x, y:y, r:r},
            success: function(response){
                if (response.startsWith('Ошибка')) {
                    $('#notification').text(response);
                } else {
                    $('#notification').text('');

                    var savedData = localStorage.getItem('table');

                    // Если в localStorage уже есть данные, добавить новый результат к ним
                    if (savedData) {
                        savedData += response;
                    } else {
                        savedData = response;
                    }

                    // Сохранение данных в localStorage
                    localStorage.setItem('table', savedData);

                    window.location.href = 'results.html';

                }
            }
        });

    });

});
