package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.User;
import com.example.gsmradeema.model.UserDAO;
import com.example.gsmradeema.model.Profil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "search":
                    searchUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "insert":
                    insertUser(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "search":
                    searchUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    private void searchUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String searchTerm = request.getParameter("searchTerm");
        List<User> listUser = userDAO.searchUsers(searchTerm);
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
        dispatcher.forward(request, response);
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDAO.listAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User newUser = new User();
        request.setAttribute("user", newUser);
        request.setAttribute("action", "user?action=insert");
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserForm.jsp");
        dispatcher.forward(request, response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String username = request.getParameter("username");
        User existingUser = userDAO.getUser(username);
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserForm.jsp");
        request.setAttribute("user", existingUser);
        request.setAttribute("action", "user?action=update");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Profil profil = Profil.valueOf(request.getParameter("profil").toUpperCase());

        User newUser = new User(username, password, profil);
        userDAO.insertUser(newUser);
        response.sendRedirect("user?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String originalUsername = request.getParameter("originalUsername");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Profil profil = Profil.valueOf(request.getParameter("profil").toUpperCase());

        User user = new User(username, password, profil);
        userDAO.updateUser(user, originalUsername);
        response.sendRedirect("user?action=list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String username = request.getParameter("username");
        // Null check
        if (username == null) {
            System.out.println("Username parameter is null. Please check the request.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing username parameter.");
            return;
        }
        userDAO.deleteUser(username);
        response.sendRedirect("user?action=list");
    }
}