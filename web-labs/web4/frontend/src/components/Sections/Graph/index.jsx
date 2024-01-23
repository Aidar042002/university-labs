import React, { useRef, useEffect, useState } from 'react';
import AppContainer from "../../AppContainer";


function getCoordsFromScreen(c) {
    return {
        x: ((c.x - 400) / 100),
        y: ((400 - c.y) / 100),
    };
}

function changeCoordsForDot(c) {
    return {
        x: Math.max(-5, Math.min(5, c.x)),
        y: Math.max(-5, Math.min(5, c.y))
    };
}

function setx(r){
    if(Math.abs(r)==1){
        return 271;
    } else if(Math.abs(r)==2){
        return 146.85;
    } else if(Math.abs(r)==3){
        return 22.5;
    }
}

function sety(r){
    if(Math.abs(r)==1){
        return 270;
    } else if(Math.abs(r)==2){
        return 140;
    } else if(Math.abs(r)==3){
        return 20;
    }
}

function Graph(props) {
    props.updateR(props.radius);

    const prevRadiusRef = useRef(props.radius);
    function parseXCoord(x) {
        return (400 + 100 * x);
    }

    function parseYCoord(y) {
        return (400 - 100 * y);
    }

    function changeColor(status) {
        return (status ? "red" : "red");
    }



    function onClickListener(event) {
        let map = document.getElementById("graph");
        let rect = map.getBoundingClientRect();

        let clickX = event.clientX - rect.left;
        let clickY = event.clientY - rect.top;

        let coords = changeCoordsForDot(getCoordsFromScreen({
            x: clickX,
            y: clickY
        }));

        props.setAndSendCoords(coords.x, coords.y);
        localStorage.setItem('add', Date.now().toString());

    }

    const canvasRef = useRef(null);

    useEffect(() => {
        class SquareCanvas {
            constructor(target) {
                this.canvas = target;
                if (this.canvas && this.canvas.getContext) {
                    this.ctx = this.canvas.getContext("2d");
                    this.ctx.scale(devicePixelRatio, devicePixelRatio);
                    this.updateArea();
                }
            }

            updateArea() {
                const rect = this.canvas.getBoundingClientRect();
                const rad=props.radius;
                let ms=0;
                if(Math.abs(rad) == 1){
                    ms=3.1;
                } else if(Math.abs(rad) == 2){
                    ms=1.58;
                } else if(Math.abs(rad) == 3){
                    ms=1.06;
                }
                rect.width=800;
                this.canvas.width = rect.width * devicePixelRatio/ms;
                this.canvas.height = rect.width * devicePixelRatio/ms;
                this.canvas.style.height = rect.width/ms + 'px';
                this.canvas.style.width = rect.width/ms + 'px';
                this.scale = this.canvas.width / 100;
            }

            drawBatman() {
                this.ctx.lineWidth = 3;

                function batman_upper(x) {
                    // Ваш код для верхней части бэтмена
                    x = Math.abs(x);
                    if (x < 0.5) {
                        return 2.25;
                    } else if (0.5 <= x && x < 0.75) {
                        return 3 * x + 0.75;
                    } else if (0.75 <= x && x < 1.0) {
                        return 9 - 8 * x;
                    } else if (1 <= x && x < 3) {
                        return (1.5 - 0.5 * x - 3 * Math.sqrt(10) / 7 * (Math.sqrt(3 - x ** 2 + 2 * x) - 2));
                    } else if (3 <= x && x <= 7) {
                        return 3 * Math.sqrt(-((x / 7) ** 2) + 1);
                    }
                }

                function batman_lower(x) {
                    // Ваш код для нижней части бэтмена
                    x = Math.abs(x);
                    if (0 <= x && x < 4) {
                        return (Math.abs(x / 2) - (3 * Math.sqrt(33) - 7) / 112 * x ** 2 +
                            Math.sqrt(1 - (Math.abs(x - 2) - 1) ** 2) - 3);
                    } else if (4 <= x && x <= 7) {
                        return -3 * Math.sqrt(-((x / 7) ** 2) + 1);
                    }
                }

                const xValues = Array.from({ length: 1400 }, (_, i) => -7 + (i / 100));
                const yUpperValues = xValues.map(batman_upper);
                const yLowerValues = xValues.map(batman_lower);
                const xyMax = 8.8;


                this.ctx.save(); // Сохраняем текущее состояние контекста

                if (props.radius < 0) {
                    // Применяем поворот на 180 градусов, если props.radius меньше 0
                    this.ctx.translate(this.canvas.width, this.canvas.height);
                    this.ctx.rotate(Math.PI);
                }


                this.ctx.beginPath();
                this.ctx.moveTo(this.canvas.width / 9.5, this.canvas.height / 2);

                for (let i = 0; i < xValues.length; i++) {
                    const x = (xValues[i] + xyMax) / (2 * xyMax) * this.canvas.width;
                    const y = (1 - (yUpperValues[i] + xyMax) / (2 * xyMax)) * this.canvas.height;
                    this.ctx.lineTo(x, y);
                }

                for (let i = xValues.length - 1; i >= 0; i--) {
                    const x = (xValues[i] + xyMax) / (2 * xyMax) * this.canvas.width;
                    const y = (1 - (yLowerValues[i] + xyMax) / (2 * xyMax)) * this.canvas.height;
                    this.ctx.lineTo(x, y);
                }

                this.ctx.fillStyle = 'green';
                this.ctx.fill();
                this.ctx.strokeStyle = '#702963';
                this.ctx.stroke();
                this.ctx.closePath();
                this.ctx.lineWidth = 1;
            }

        }


        const sc = new SquareCanvas(canvasRef.current);
        sc.drawBatman();

        if (prevRadiusRef.current !== props.radius) {
            prevRadiusRef.current = props.radius;
        }



    }, [props.radius, props.dots]);


    const handleStorage = (e) => {
        if (e.key === 'add') {
            // Принудительная перезагрузка страницы
            window.location.reload(true);
        }
    };

    useEffect(() => {
        // Подписка на события изменения localStorage
        window.addEventListener('storage', handleStorage);

        return () => {
            // Отписка от событий при размонтировании компонента
            window.removeEventListener('storage', handleStorage);
        };
    }, []);


    return (
        <AppContainer>
            <svg style={{ border: '1px solid black' }} id="graph" className="pointer" xmlns="http://www.w3.org/2000/svg" width="800px" height="800px" onClick={onClickListener}>
                <foreignObject x={setx(props.radius)} y={sety(props.radius)} width="800" height="800">
                        <canvas ref={canvasRef} id="graph"></canvas>
                </foreignObject>
                <line x1="0" x2="800" y1="400" y2="400" stroke="#041e37"></line>
                <line x1="400" x2="400" y1="0" y2="800" stroke="#041e37"></line>
                <polygon id="arrow" points="400,0 395,10 405,10" stroke="#041e37"></polygon>
                <polygon id="arrow" points="500,250 490,245 490,255" stroke="#041e37" transform="translate(300, 150)"></polygon>

                <text x="780" y="390">X</text>
                <text x="405" y="10">Y</text>

                <line x1="300" x2="300" y1="395" y2="405" stroke="#041e37"></line>
                <text x="290" y="390">-1</text>

                <line x1="350" x2="350" y1="395" y2="405" stroke="#041e37"></line>
                <text x="340" y="390">-1/2</text>

                <line x1="450" x2="450" y1="395" y2="405" stroke="#041e37"></line>
                <text x="440" y="390">1/2</text>

                <line x1="500" x2="500" y1="395" y2="405" stroke="#041e37"></line>
                <text x="490" y="390">1</text>

                <line x1="395" x2="405" y1="300" y2="300" stroke="#041e37"></line>
                <text x="382" y="290">1</text>

                <line x1="395" x2="405" y1="350" y2="350" stroke="#041e37"></line>
                <text x="362" y="340">1/2</text>

                <line x1="395" x2="405" y1="450" y2="450" stroke="#041e37"></line>
                <text x="362" y="460">-1/2</text>

                <line x1="395" x2="405" y1="500" y2="500" stroke="#041e37"></line>
                <text x="382" y="510">-1</text>

                <line x1="200" x2="200" y1="395" y2="405" stroke="#041e37"></line>
                <text x="190" y="390">-2</text>

                <line x1="100" x2="100" y1="395" y2="405" stroke="#041e37"></line>
                <text x="90" y="390">-3</text>

                <line x1="600" x2="600" y1="395" y2="405" stroke="#041e37"></line>
                <text x="590" y="390">2</text>

                <line x1="700" x2="700" y1="395" y2="405" stroke="#041e37"></line>
                <text x="690" y="390">3</text>

                <line x1="395" x2="405" y1="600" y2="600" stroke="#041e37"></line>
                <text x="382" y="610">-2</text>

                <line x1="395" x2="405" y1="700" y2="700" stroke="#041e37"></line>
                <text x="382" y="710">-3</text>

                <line x1="395" x2="405" y1="200" y2="200" stroke="#041e37"></line>
                <text x="382" y="210">2</text>

                <line x1="395" x2="405" y1="100" y2="100" stroke="#041e37"></line>
                <text x="382" y="110">3</text>

                <circle id="cursor" cx="400" cy="400" r="3" fill="#red" stroke="red"></circle>
                <circle id="dot" cx="400" cy="400" r="3" fill="#red" stroke="red"></circle>


                {props.dots && props.dots.map(
                    (dot, i) => (
                        <circle key={i} className="dot" cx={parseXCoord(dot.x)} cy={parseYCoord(dot.y)} r="3" fill={changeColor(dot.status)} stroke="#132a42"></circle>
                    )
                )}


            </svg>
        </AppContainer>
    );
}

export default Graph;