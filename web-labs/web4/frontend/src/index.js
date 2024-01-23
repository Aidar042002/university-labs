import React, {useEffect} from 'react';
import ReactDOM from 'react-dom';
import {HashRouter as Router, Routes, Route, useNavigate} from 'react-router-dom';

import './index.css';

import LoginPage from './routes/LoginPage';
import MainPage from './routes/MainPage';
import NotFoundPage from './routes/NotFoundPage';
import store from './store';
import { Provider } from 'react-redux';


const App = () => {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/mainpage" element={<MainPage />} />
                <Route path="/*" element={<NotFoundPage />} />
            </Routes>
        </Router>
    );
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <App />
        </Provider>
    </React.StrictMode>
);
