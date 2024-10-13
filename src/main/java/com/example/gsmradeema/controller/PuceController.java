package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.Puce;
import com.example.gsmradeema.model.PuceDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PuceController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PuceDAO puceDAO;

    @Override
    public void init() {
        puceDAO = new PuceDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            action = ""; // Default action
        }

        try {
            switch (action) {
                case "insert":
                    insertPuce(request, response);
                    break;
                case "update":
                    updatePuce(request, response);
                    break;
                case "delete":
                    deletePuce(request, response);
                    break;
                default:
                    listPuces(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException("Database error: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            action = ""; // Default action
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deletePuce(request, response);
                    break;
                case "search":
                    searchPuces(request, response);
                    break;
                default:
                    listPuces(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException("Database error: " + ex.getMessage(), ex);
        }
    }

    private void listPuces(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Puce> listPuces = puceDAO.listAllPuces();
        request.setAttribute("listPuces", listPuces);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PuceList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("PuceForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect("puce?action=list"); // Redirect on error
            return;
        }

        Puce existingPuce = puceDAO.getPuce(id);
        if (existingPuce == null) {
            response.sendRedirect("puce?action=list"); // Redirect if no Puce found
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("PuceForm.jsp");
        request.setAttribute("puce", existingPuce);
        dispatcher.forward(request, response);
    }

    private void insertPuce(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String numero = request.getParameter("numero");
        String code = request.getParameter("code");
        String etat = request.getParameter("etat");
        String type = request.getParameter("type");

        if (numero == null || code == null || etat == null || type == null ||
                numero.isEmpty() || code.isEmpty() || etat.isEmpty() || type.isEmpty()) {
            response.sendRedirect("puce?action=list"); // Redirect or handle error
            return;
        }

        Puce newPuce = new Puce(0, numero, code, etat, type);
        puceDAO.insertPuce(newPuce);
        response.sendRedirect("puce?action=list");
    }

    private void updatePuce(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        request.setCharacterEncoding("UTF-8");  // Ensure UTF-8 encoding
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect("puce?action=list"); // Handle error or redirect
            return;
        }

        String numero = request.getParameter("numero");
        String code = request.getParameter("code");
        String etat = request.getParameter("etat");
        String type = request.getParameter("type");

        if (numero == null || code == null || etat == null || type == null ||
                numero.isEmpty() || code.isEmpty() || etat.isEmpty() || type.isEmpty()) {
            response.sendRedirect("puce?action=list"); // Redirect or handle error
            return;
        }

        Puce puce = new Puce(id, numero, code, etat, type);
        puceDAO.updatePuce(puce);
        response.sendRedirect("puce?action=list");
    }

    private void deletePuce(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect("puce?action=list"); // Redirect on error
            return;
        }

        puceDAO.deletePuce(id);
        response.sendRedirect("puce?action=list");
    }

    private void searchPuces(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String searchTerm = request.getParameter("search");
        searchTerm = (searchTerm != null) ? searchTerm : "";

        List<Puce> searchResults = puceDAO.searchPuces(searchTerm);
        request.setAttribute("listPuces", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PuceList.jsp");
        dispatcher.forward(request, response);
    }
}
