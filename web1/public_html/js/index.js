$(function(){

    function checkX(x){
        return x >= -5 && x <= 5;
    }

    $('#clearButton').click(function() {
        localStorage.removeItem('table');
        $('#results tbody').empty();
        alert('Данные удалены из local storage и из таблицы.');
    });

    var savedData = localStorage.getItem('table');
    if (savedData) {
        $('#results').html(savedData);
    }

    $('#sendButton').click(function(e){
        e.preventDefault();
        $('#notification').text('');
        $('#error-br').remove();

        var x=$('#x').val();
        var y=$('#y').val();
        var r=$('input[type="radio"]:checked').val();

        if(!checkX(x) || x.trim()==''){
            $('#notification').text('Введите x от -5 до 5').append('<br id="error-br">');
            return;
        }

        if(y === null){
            $('#notification').text('Выберите Y').append('<br id="error-br">');
            return;
        }

        if(r == undefined){
            $('#notification').text('Выберите R').append('<br id="error-br">');
            return;
        }

        $.ajax({
            url:'php/index.php', 
            type: 'GET',
            data: { x: x, y: y, r: r },
            success: function (response) {
                $('#results').append(response);
                localStorage.setItem('table', $('#results').html());
            }
        });
    })
});
