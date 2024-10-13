package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.Dotation;
import com.example.gsmradeema.model.DotationDAO;
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
import java.sql.Date;
import java.util.List;

@WebServlet("/dotation")
public class DotationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DotationDAO dotationDAO;
    private PuceDAO puceDAO; // Add PuceDAO to fetch Puces

    @Override
    public void init() throws ServletException {
        dotationDAO = new DotationDAO();
        puceDAO = new PuceDAO(); // Initialize PuceDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                action = "list";
            }

            switch (action) {
                case "list":
                    listDotation(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteDotation(request, response);
                    break;
                case "search":
                    searchDotation(request, response);
                    break;
                default:
                    listDotation(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "insert":
                    insertDotation(request, response);
                    break;
                case "update":
                    updateDotation(request, response);
                    break;
                case "delete":
                    deleteDotation(request, response);
                    break;
                default:
                    listDotation(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listDotation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Dotation> listDotation = dotationDAO.readAll();
        request.setAttribute("listDotations", listDotation);
        RequestDispatcher dispatcher = request.getRequestDispatcher("dotationList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Dotation newDotation = new Dotation();
        List<Puce> puces;
        try {
            puces = puceDAO.listAllPuces(); // Fetch all Puces
        } catch (SQLException e) {
            throw new ServletException("Error fetching Puce data", e);
        }
        request.setAttribute("dotation", newDotation);
        request.setAttribute("puces", puces); // Pass Puce list to JSP
        request.setAttribute("formAction", "insert");
        RequestDispatcher dispatcher = request.getRequestDispatcher("dotationForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int dotationId = Integer.parseInt(request.getParameter("dotationId"));
        Dotation existingDotation = dotationDAO.read(dotationId);
        List<Puce> puces = puceDAO.listAllPuces(); // Fetch all Puces
        request.setAttribute("dotation", existingDotation);
        request.setAttribute("puces", puces); // Pass Puce list to JSP
        request.setAttribute("formAction", "update");
        RequestDispatcher dispatcher = request.getRequestDispatcher("dotationForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertDotation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String solde = request.getParameter("solde");
        int puceId = Integer.parseInt(request.getParameter("puceId")); // Get puceId from form

        Dotation newDotation = new Dotation();
        newDotation.setSolde(solde);
        newDotation.setPuceId(puceId); // Set puceId
        newDotation.setDateAffectation(new Date(System.currentTimeMillis())); // Set current date
        dotationDAO.create(newDotation);
        response.sendRedirect("dotation?action=list");
    }

    private void updateDotation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int dotationId = Integer.parseInt(request.getParameter("dotationId"));
        String solde = request.getParameter("solde");
        int puceId = Integer.parseInt(request.getParameter("puceId")); // Get puceId from form

        Dotation dotation = new Dotation();
        dotation.setDotationId(dotationId);
        dotation.setSolde(solde);
        dotation.setPuceId(puceId); // Set puceId
        dotation.setDateAffectation(new Date(System.currentTimeMillis())); // Set current date
        dotationDAO.update(dotation);
        response.sendRedirect("dotation?action=list");
    }

    private void deleteDotation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int dotationId = Integer.parseInt(request.getParameter("dotationId"));
        dotationDAO.delete(dotationId);
        response.sendRedirect("dotation?action=list");
    }

    private void searchDotation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String soldeOrPuceNumber = request.getParameter("search"); // Allow searching by solde or Puce number
        List<Dotation> searchResults = dotationDAO.search(soldeOrPuceNumber);
        request.setAttribute("listDotations", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("dotationList.jsp");
        dispatcher.forward(request, response);
    }
}
