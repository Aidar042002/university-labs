import Author from "../../components/Sections/Author";
import AppBody from "../../components/AppBody";
import Header from "../../components/Sections/Header";
import Input from "../../components/Sections/Input";
import { Toaster } from "react-hot-toast";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from 'belle';
import { useDispatch, useSelector } from 'react-redux';

function MainPage() {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Отправка сообщения об выходе на все открытые вкладки
        localStorage.setItem('logout', Date.now().toString());
        localStorage.removeItem("rad");
        localStorage.removeItem("change");
        // Очистка логина и пароля в localStorage
        localStorage.removeItem("login");
        localStorage.removeItem("password");

        // Переход на другую страницу
        navigate("/");
    };

    // Обработчик события хранения в localStorage
    const handleStorage = (e) => {
        if (e.key === 'logout') {
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
        <AppBody>
            <Author />
            <Input />
            <br />
            {/*<Header navigation={headerNavigation} />*/}
            <Button onClick={handleLogout}>Выход</Button>
            <Toaster
                position="bottom-center"
                reverseOrder={false}
            />
        </AppBody>
    );
}

export default MainPage;
