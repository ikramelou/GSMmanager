package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        try {
            if (userDAO.validateUser(username, password)) {
                if ("on".equals(remember)) {
                    Cookie usernameCookie = new Cookie("username", username);
                    Cookie passwordCookie = new Cookie("password", password);
                    usernameCookie.setMaxAge(7 * 24 * 60 * 60);
                    passwordCookie.setMaxAge(7 * 24 * 60 * 60);
                    response.addCookie(usernameCookie);
                    response.addCookie(passwordCookie);
                }
                request.getSession().setAttribute("user", username);
                response.sendRedirect("welcome.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }

        try {
            if (username != null && password != null && userDAO.validateUser(username, password)) {
                request.getSession().setAttribute("user", username);
                response.sendRedirect("welcome.jsp");
            } else {
                request.getRequestDispatcher("/login.html").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
