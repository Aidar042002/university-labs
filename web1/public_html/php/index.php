<?php
$start_time = microtime(true);

if(isset($_GET['x'])&&isset($_GET['y'])&&isset($_GET['r'])){
    $x=floatval($_GET['x']);
    $y=floatval($_GET['y']);
    $r=floatval($_GET['r']);
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


