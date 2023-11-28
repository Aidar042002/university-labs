<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Лабораторная №2</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header>
    <span>Фархутдинов А.А. P3220 вариант №223300</span>
</header>
<main>
    <div>
        <svg class="area">
            <polygon points="260,260 260,312 286,260" fill="blue" />
            <path d="M260,260 L260,208 A52,52 0 0,0 208,260 Z" fill="blue" />
            <rect class="rectangle" x="260" y="208"/>
            <line class="x-line" x1="0" y1="260" x2="520" y2="260"/>
            <line class="y-line" x1="260" y1="0" x2="260" y2="520"/>

            <!-- ox -->
            <line x1="208" y1="255" x2="208" y2="265" stroke="grey"/>
            <text x="208" y="250" text-anchor="middle" id="rxTextNeg">-r</text>

            <line x1="234" y1="255" x2="234" y2="265" stroke="grey"/>
            <text x="234" y="250" text-anchor="middle" id="rxTextNegHalf">-r/2</text>

            <line x1="286" y1="255" x2="286" y2="265" stroke="grey"/>
            <text x="286" y="250" text-anchor="middle" id="rxTextHalf">r/2</text>

            <line x1="312" y1="255" x2="312" y2="265" stroke="grey"/>
            <text x="312" y="250" text-anchor="middle" id="rxText">r</text>
            <!-- oy -->
            <line x1="255" y1="208" x2="265" y2="208" stroke="grey" />
            <text x="265" y="208" text-anchor="middle" id="ryText">r</text>

            <line x1="255" y1="234" x2="265" y2="234" stroke="grey" />
            <text x="270" y="234" text-anchor="middle" id="ryTextHalf">r/2</text>

            <line x1="255" y1="286" x2="265" y2="286" stroke="grey" />
            <text x="275" y="286" text-anchor="middle" id="ryTextNegHalf">-r/2</text>

            <line x1="255" y1="312" x2="265" y2="312" stroke="grey" />
            <text x="270" y="312" text-anchor="middle" id="ryTextNeg">-r</text>

        </svg>
        <div class="sendData">
            <form id="sendForm">
                <span class="text">X:</span><input type="text" name="x" id="x" placeholder="-5...5"><br>
                <span class="text">Y:</span><input type="text" name="y" id="y" placeholder="-5...3"><br>
                <span class="text">R:</span><input type="radio" name="r" value="1" class="btn-r"><span class="text">1</span>
                <input type="radio" name="r" value="1.5" class="btn-r"><span class="text">1.5</span>
                <input type="radio" name="r" value="2" class="btn-r"><span class="text">2</span>
                <input type="radio" name="r" value="2.5" class="btn-r"><span class="text">2.5</span>
                <input type="radio" name="r" value="3" class="btn-r"><span class="text">3</span>
                <br>
                <input type="submit" id="submitButton" class="buttons" value="Отправить"><br>
                <p id="error"></p>
                <p id="notification"></p>
            </form>
            <button id="clearButton" class="buttons">Очистить таблицу</button><br>
        </div>
        <div class="tableResults">
            <table id="results">
                <thead>
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Hit</th>
                    <th>Execute</th>
                    <th>Time</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</main>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="js/main.js"></script>
</body>
</html>