package com.example.web_lab2.servlets;

import com.example.web_lab2.beans.Results;
import com.example.web_lab2.beans.ResultsBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@WebServlet("/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        BigDecimal x = new BigDecimal(request.getParameter("x"));
        BigDecimal y = new BigDecimal(request.getParameter("y"));
        BigDecimal r = new BigDecimal(request.getParameter("r"));


        double doubleX = x.doubleValue();
        double doubleY = y.doubleValue();
        double doubleR = r.doubleValue();


        if (checkX(doubleX) || checkY(doubleY) || checkR(doubleR)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Error: incorrect value");
            out.close();
        } else {
            String hit = "-";
            if (checkCircle(doubleX, doubleY, doubleR) || checkRectangle(doubleX, doubleY, doubleR) || checkTriangle(doubleX, doubleY, doubleR)) {
                hit = "+";
            }

            ResultsBean results = (ResultsBean) request.getSession().getAttribute("results");
            if (results == null) results = new ResultsBean();

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = currentTime.format(formatter);


            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            Results result = new Results(x, y, r, hit, executionTime, formattedTime);

            results.getResults().add(result);
            request.getSession().setAttribute("results", results);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<tr>");
            out.println("<td>" + result.getX() + "</td>");
            out.println("<td>" + result.getY() + "</td>");
            out.println("<td>" + result.getR() + "</td>");
            out.println("<td>" + result.getHit() + "</td>");
            out.println("<td>" + executionTime + "</td>");
            out.println("<td>" + result.getFormattedTime() + "</td>");
            out.println("</tr>");


            out.close();
        }
    }

    private boolean checkCircle(double x, double y, double r) {
        if (x < 0 && y > 0) {
            double distanceToOrigin = Math.sqrt(x * x + y * y);
            return distanceToOrigin <= r;
        }
        return false;
    }

    private boolean checkRectangle(double x, double y, double r) {
        return x >= 0 && x <= r && y >= 0 && y <= r && x <= r / 2 && y <= r;
    }


    public static boolean checkTriangle(double x, double y, double r) {
        return x>=0 && y<=0 && y>=2*x-r;
    }


    private boolean checkX(double x) {
        return Double.isNaN(x) || x < -5 || x > 5;
    }

    private boolean checkY(double y) {
        return Double.isNaN(y) || y < -5 || y > 3;
    }

    private boolean checkR(double r) {
        if (Double.isNaN(r)) {
            return true;
        }
        double[] validValuesR = {1, 1.5, 2, 2.5, 3};
        return Arrays.stream(validValuesR).noneMatch(value -> value == r) || r < 1 || r > 3;
    }
}
