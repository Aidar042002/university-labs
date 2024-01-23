import "./index.scoped.css";
import AppContainer from "../../AppContainer";
import {useNavigate} from "react-router-dom";
import {useState, useEffect} from "react";
import toast from "react-hot-toast";
import {useDispatch, useSelector} from "react-redux";
import {selectLogin, setLogin} from "../../../redux/login";
import {setPassword} from "../../../redux/password";
// import {Button} from "belle";

function LogIn() {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [newLogin, setNewLogin] = useState("");
    const [newPassword, setNewPassword] = useState("");


    const EMPTY_LOGIN_ERROR = "Логин не может быть пустым";
    const EMPTY_PASSWORD_ERROR = "пароль не может быть пустым";
    const CONFLICT_ERROR = "Логин уже занят";
    const UNAUTHORIZED_ERROR = "Неверный логин или пароль";
    const BAD_REQUEST_ERROR = "Неверный запрос";
    const UNKNOWN_ERROR = "Неизвестная ошибка";

    const handleStorage = (e) => {
        if (e.key === 'log') {
            // Принудительная перезагрузка страницы
            window.location.reload(true);
        }
    };

    useEffect(() => {
        // Проверяем наличие сохраненного логина в localStorage
        const storedLogin = localStorage.getItem("login");
        const storedPassword = localStorage.getItem("password");

        if (storedLogin && storedPassword) {
            // Восстанавливаем логин и пароль в Redux
            dispatch(setLogin(storedLogin));
            dispatch(setPassword(storedPassword));

            // Попытка повторной аутентификации на сервере
            fetch("/api/login", {
                method: "POST",
                headers: {
                    "Authorization": "Basic " + btoa(storedLogin + ":" + storedPassword)
                }
            }).then(response => {
                // Обработка ответа
                if (response.ok) {
                    // Аутентификация прошла успешно
                    navigate("/mainpage");
                } else {
                    // Обработка ошибки аутентификации
                    console.error("Authentication error:", response.statusText);
                }
            });
        }

        window.addEventListener('storage', handleStorage);

        return () => {
            // Отписка от событий при размонтировании компонента
            window.removeEventListener('storage', handleStorage);
        };
    }, [dispatch, navigate]);

    function popupMessage(message){
        toast(message, {
            style: {
                borderRadius: '10px',
                color: 'rgb(4, 30, 55)',
                background: 'rgb(255, 255, 255, 80%)'
            }
        })
    }

    function validateData(){
        if (newLogin === ""){
            popupMessage(EMPTY_LOGIN_ERROR);
            return false;
        }
        else if (newPassword === ""){
            popupMessage(EMPTY_PASSWORD_ERROR);
            return false;
        }
        else{
            return true;
        }
    }

    function checkResponse(response){
        if (response.ok){
            return true;
        }
        else{
            if (response.statusText === 'Conflict'){
                popupMessage(CONFLICT_ERROR);
            }
            else if(response.statusText === 'Unauthorized'){
                popupMessage(UNAUTHORIZED_ERROR);
            }
            else if(response.statusText === 'Bad Request'){
                popupMessage(BAD_REQUEST_ERROR);
            }
            else{
                popupMessage(UNKNOWN_ERROR);
            }
            return false;
        }
    }

    function logInRequest(){
        if (validateData()){
            fetch("/api/login", {
                method: 'POST',
                headers: {"Authorization": "Basic " + btoa(newLogin + ":" + newPassword).replaceAll("=", "")}}).then(response => {
                if (checkResponse(response)){
                    dispatch(setLogin(newLogin));
                    dispatch(setPassword(newPassword));

                    localStorage.setItem("login", newLogin);
                    localStorage.setItem("password", newPassword);

                    localStorage.setItem("log", Date.now().toString());

                    navigate("/mainpage");
                }
            })
        }
     }

    function signUpRequest(){
        if (validateData()){
            let formData = new FormData();
            formData.append('login', newLogin);
            formData.append('password', newPassword);
            fetch("/api/register",{
                method: 'POST',
                body: formData
            }).then(response => {
                if (checkResponse(response)){
                    dispatch(setLogin(newLogin));
                    dispatch(setPassword(newPassword));

                    localStorage.setItem("login", newLogin);
                    localStorage.setItem("password", newPassword);

                    localStorage.setItem("log", Date.now().toString());

                    navigate("/mainpage");
                }
            })

        }
    }
    return (
        <AppContainer>
            <div id="login-container">
                <div id="login-small-text" className="small-text">
                    Введите логин и пароль
                </div>
                <form id="login-password-form">
                    <span className="popup" id="error-popup"></span>
                    <div id="input-container">
                        <input className="input-text" type="text" placeholder="Login" id="login" value={newLogin} onChange={e => setNewLogin(e.target.value)}/>
                        <input className="input-text" type="password" placeholder="Password" id="password" value={newPassword} onChange={e => setNewPassword(e.target.value)}/>
                    </div>
                </form>
                <div id="choice-container">
                    <button className="pointer button" id="login-button" onClick={logInRequest}>Вход</button>
                    <button className="pointer button" id="signup-button" onClick={signUpRequest}>Регистрация</button>
                </div>
            </div>
        </AppContainer>
    );
}
export default LogIn;