$(function() {
    var savedData = localStorage.getItem('table');
    if (savedData) {
        // Вставляем данные из localStorage в tbody таблицы
        $('#results tbody').html(savedData);
    }

    $('#clearButton').click(function() {
        localStorage.removeItem('table');
        $('#results tbody').empty();
        $('#clearTable').text('Данные удалены из local storage и из таблицы.');
    });

});
