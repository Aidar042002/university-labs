package com.example.web_lab2.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Проверка наличия параметров x, y и r в запросе
        if (request.getParameter("x") != null && request.getParameter("y") != null && request.getParameter("r") != null) {
            // Делегирование запроса AreaCheckServlet
            request.getRequestDispatcher("/AreaCheckServlet").forward(request, response);
        }
        else {
            // Если параметры отсутствуют, перенаправление на форму
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}