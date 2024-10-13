package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.Entite;
import com.example.gsmradeema.model.EntiteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class EntiteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EntiteDAO entiteDAO;

    public void init() {
        entiteDAO = new EntiteDAO();
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
                    deleteEntite(request, response);
                    break;
                case "search":
                    searchEntites(request, response);
                    break;
                default:
                    listEntites(request, response);
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
                    insertEntite(request, response);
                    break;
                case "update":
                    updateEntite(request, response);
                    break;
                case "delete":
                    deleteEntite(request, response);
                    break;
                default:
                    listEntites(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listEntites(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Entite> listEntite = entiteDAO.listAllEntites();
        request.setAttribute("listEntite", listEntite);
        RequestDispatcher dispatcher = request.getRequestDispatcher("EntiteList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("entite", new Entite());
        request.setAttribute("action", "entite?action=insert");
        RequestDispatcher dispatcher = request.getRequestDispatcher("EntiteForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Entite existingEntite = entiteDAO.getEntite(id);
            request.setAttribute("entite", existingEntite);
            request.setAttribute("action", "entite?action=update");
        } catch (NumberFormatException e) {
            // Handle invalid ID format
            request.setAttribute("error", "Invalid Entite ID.");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("EntiteForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertEntite(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nom = request.getParameter("nom");
        String type = request.getParameter("type");
        String entiteMere = request.getParameter("entiteMere");

        Entite newEntite = new Entite(nom, type, entiteMere);
        entiteDAO.insertEntite(newEntite);
        response.sendRedirect("entite?action=list");
    }

    private void searchEntites(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String searchTerm = request.getParameter("searchTerm");
        List<Entite> searchResults = entiteDAO.searchEntites(searchTerm);
        request.setAttribute("listEntite", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("EntiteList.jsp");
        dispatcher.forward(request, response);
    }

    private void updateEntite(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nom = request.getParameter("nom");
            String type = request.getParameter("type");
            String entiteMere = request.getParameter("entiteMere");

            Entite entite = new Entite(id, nom, type, entiteMere);
            entiteDAO.updateEntite(entite);
        } catch (NumberFormatException e) {
            // Handle invalid ID format
            request.setAttribute("error", "Invalid Entite ID.");
        }
        response.sendRedirect("entite?action=list");
    }

    private void deleteEntite(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            entiteDAO.deleteEntite(id);
        } catch (NumberFormatException e) {
            // Handle invalid ID format
            request.setAttribute("error", "Invalid Entite ID.");
        }
        response.sendRedirect("entite?action=list");
    }
}
