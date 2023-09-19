<?php
$start_time = microtime(true);
$x = isset($_GET['x']) ? str_replace(',', '.', $_GET['x']) : null;
$y = isset($_GET['y']) ? str_replace(',', '.', $_GET['y']) : null;
$r = isset($_GET['r']) ? str_replace(',', '.', $_GET['r']) : null;


if(!($x !== null && is_numeric($x)) || ($x < -5 || $x > 5)){
    echo 'Ошибка: неверное значение для параметра x';
    exit();
}

if(!($y!=null && is_numeric($y)) || ($y < -3 || $y > 5)){
    echo 'Ошибка: неверное значение для параметра y';
    exit();
}

if(!($r!=null && is_numeric($r)) || ($r < 1 || $r > 3)){
    echo 'Ошибка: неверное значение для параметра r';
    exit();
}

$hit = '-';

if ($x >= 0 && $y >= 0) { // 1-я четверть
    if ($x <= $r / 2 && $y <= $r) { // попадание в круг
        $hit = '+';
    }
} elseif ($x <= 0 && $y >= 0) { // 2-я четверть
    if ($x >= -$r / 2 && $y <= $r / 2) { // попадание в треугольник
        $hit = '+';
    }
} elseif ($x <= 0 && $y <= 0) { // 3-я четверть
    if ($x >= -$r / 2 && $y >= -$r) { //  попадание в прямоугольник
        $hit = '+';
    }
}

$rowHTML = '<tr>';
$rowHTML .= '<td>' . $x . '</td>';
$rowHTML .= '<td>' . $y . '</td>';
$rowHTML .= '<td>' . $r . '</td>';
$rowHTML .= '<td>'.$hit.'</td>';
$rowHTML .= '<td>' . date('Y-m-d H:i:s') . '</td>';
$execution_time=(number_format(microtime(true) - $start_time, 6));
$rowHTML .= '<td>'.$execution_time.' мкc'.'</td>';
$rowHTML .= '</tr>';

echo $rowHTML;

?>
