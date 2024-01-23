import "./index.scoped.css";
import AppContainer from "../../AppContainer";
import {useEffect, useState} from "react";
import toast from "react-hot-toast";
import Graph from "../Graph";
import store from "../../../store";
import {useNavigate} from "react-router-dom";
import Belle, {Button} from "belle";
import { Select,Option } from 'belle';


function Input() {

    useEffect(() => {
        if (store.getState().login.value == null || store.getState().password.value == null){
            navigate("/");
        }
        else{
            sendShowRequest();
        }
    }, []);

    function popupMessage(message){
        toast(message, {
            style: {
                borderRadius: '10px',
                color: 'rgb(4, 30, 55)',
                background: 'rgb(255, 255, 255, 50%)'
            }
        })
    }

    let rstate=1;
    if(localStorage.getItem("rad")){
        rstate=localStorage.getItem("rad");
    }
    const [xValue, setX] = useState("-5");
    const [yValue, setY] = useState("0");
    const [rValue, setR] = useState(rstate);
    const updateR = (newR) => {
        setR(newR);
        localStorage.setItem("rad", newR);

    };
    const handleStorage2 = (e) => {
        if (e.key === 'change') {
            // Принудительная перезагрузка страницы
            window.location.reload(true);
        }
    };

    useEffect(() => {
        // Подписка на события изменения localStorage
        window.addEventListener('storage', handleStorage2);

        return () => {
            // Отписка от событий при размонтировании компонента
            window.removeEventListener('storage', handleStorage2);
        };
    }, []);

    const [dotData, setDotData] = useState([]);

    const navigate = useNavigate();

    const INVALID_Y_ERROR = "Неверный Y!";

    const selectX = (e) => {
        setX(parseInt(e.target.value));
    }
    const selectY = (value) => {
        if (validateY(value.value)) {
            setY(value.value);
        } else {
            popupMessage(INVALID_Y_ERROR);
        }
    }

    const selectR = (value) => {
        setR(value);
        localStorage.setItem("change", Date.now().toString());
    }

    function validateY(y) {
        return y >= -4 && y <= 4;
    }

    function sendShowRequest(){
        (async () => {
            let response = await fetch("/api/dots", {
                method: "GET",
                headers: {"Authorization": "Basic " + btoa(store.getState().login.value + ":" + store.getState().password.value).replaceAll("=", "")}
            })
            let data = await response.json();
            if (response.ok) setDotData(data);
        })()
    }

    function sendCheckRequest(x,y,r){
        let dotFormData = new FormData();
        dotFormData.append('x', parseFloat(x));
        dotFormData.append('y', parseFloat(y));
        dotFormData.append('r', parseFloat(r));
        (async () => {
            let response = await fetch("/api/dots", {
                method: "POST",
                headers: {"Authorization": "Basic " + btoa(store.getState().login.value + ":" + store.getState().password.value).replaceAll("=", "")},
                body: dotFormData
            })
            let data = await response.json();
            if (response.ok) setDotData([...dotData, data]);
        })()
    }

    function sendDeleteRequest(){
        (async () => {
            let response = await fetch("/api/dots", {
                method: "DELETE",
                headers: {"Authorization": "Basic " + btoa(store.getState().login.value + ":" + store.getState().password.value).replaceAll("=", "")}
            })
            if (response.ok) setDotData([]);
            localStorage.setItem("delete", Date.now().toString());
        })()
    }

    //
    const handleStorage = (e) => {
        if (e.key === 'delete') {
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
    //

    function parseNumber(number){
        if (number < 10) return ("0" + number);
        return number;
    }

    function parseCurrentTime(timeStamp){
        let dateFormat = new Date(timeStamp*1000);
        return (parseNumber(dateFormat.getDate()) + "/" + parseNumber((dateFormat.getMonth()+1)) + "/" + dateFormat.getFullYear() + " "
            + parseNumber(dateFormat.getHours()) + ":" + parseNumber(dateFormat.getMinutes()) + ":" + parseNumber(dateFormat.getSeconds()));
    }

    function parseScriptTime(scriptTime){
        return scriptTime/1000;
    }

    function sendCoordsFromClick(x,y){
        setX(x);
        setY(y);
        sendCheckRequest(x,y,rValue);
    }


    return (
        <AppContainer>
            <div id="main-container">
                <div id="data-container">
                    <div id="X" >
                        Choose X:
                        <div id="X-first-row">
                            {[...Array(9)].map((_, index) => {
                                const xValueOption = index - 4;
                                return (
                                    <div className="one-radio-container" key={xValueOption}>
                                        <Belle.Button
                                            className={`pointer radio-button ${xValue === xValueOption ? 'active' : ''}`}
                                            onClick={() => selectX({ target: { value: xValueOption } })}
                                        >
                                            {xValueOption}
                                        </Belle.Button>
                                    </div>
                                );
                            })}
                        </div>
                    </div>

                    <div id="Y" style={{marginTop: '-5px', marginRight: '20px'}}>
                        Enter Y (-4; 4):
                        <br />
                        <Select id="Y-value" onUpdate={(value) => selectY(value)}>
                            <Option value={-4}>-4</Option>
                            <Option value={-3}>-3</Option>
                            <Option value={-2}>-2</Option>
                            <Option value={-1}>-1</Option>
                            <Option value={0}>0</Option>
                            <Option value={1}>1</Option>
                            <Option value={2}>2</Option>
                            <Option value={3}>3</Option>
                            <Option value={4}>4</Option>
                        </Select>
                    </div>

                    <div id="R" style={{marginBottom:'600px'}}>
                        Choose R:
                        <div id="R-first-row">
                            {[...Array(7)].map((_, index) => {
                                const rValueOption = index - 3;
                                return (
                                    <div className="one-radio-container" key={rValueOption}>
                                        <Belle.Button
                                            className={`pointer radio-button `}
                                            onClick={() => selectR(rValueOption.toString())}
                                        >
                                            {rValueOption}
                                        </Belle.Button>
                                    </div>
                                );
                            })}

                        </div>
                        <div style={{padding:'5px', marginLeft:'30px'}}>
                            <Button className="pointer button" id="check-button" onClick={() => sendCheckRequest(xValue, yValue, rValue)}>Проверить</Button>
                            <Button className="pointer button" id="clear-button" onClick={sendDeleteRequest}>Очистить</Button>
                        </div>
                    </div>


                </div>
                <div id="graph-container">
                    <Graph radius={rValue} dots={dotData} setAndSendCoords={sendCoordsFromClick} updateR={updateR}/>
                </div>
                <div id="button-container">

                </div>
                <div id="table-container">
                    <table id="results">
                        <thead><tr>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Current time</th>
                            <th>Script time</th>
                            <th>Result</th>
                        </tr></thead>
                        <tbody>
                        {dotData && dotData.map(
                            (dot, i) => (
                                <tr key={i}>
                                    <td>{dot.x}</td>
                                    <td>{dot.y}</td>
                                    <td>{dot.r}</td>
                                    <td>{parseCurrentTime(dot.timestamp)}</td>
                                    <td>{parseScriptTime(dot.scriptTime)}</td>
                                    <td>{dot.status ? "+" : "-"}</td>
                                </tr>
                            )
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
        </AppContainer>
    );
}
export default Input;