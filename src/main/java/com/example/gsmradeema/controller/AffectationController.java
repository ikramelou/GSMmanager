package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.Affectation;
import com.example.gsmradeema.model.AffectationDAO;
import com.example.gsmradeema.model.Personnel;
import com.example.gsmradeema.model.PersonnelDAO;
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

@WebServlet("/affectation")
public class AffectationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AffectationDAO affectationDAO;
    private PersonnelDAO personnelDAO;
    private PuceDAO puceDAO;

    @Override
    public void init() throws ServletException {
        affectationDAO = new AffectationDAO();
        personnelDAO = new PersonnelDAO();
        puceDAO = new PuceDAO();
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
                    listAffectation(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteAffectation(request, response);
                    break;
                case "search":
                    searchAffectation(request, response);
                    break;
                default:
                    listAffectation(request, response);
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
                    insertAffectation(request, response);
                    break;
                case "update":
                    updateAffectation(request, response);
                    break;
                case "delete":
                    deleteAffectation(request, response);
                    break;
                case "search":
                    searchAffectation(request, response);
                    break;
                default:
                    listAffectation(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listAffectation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Affectation> listAffectation = affectationDAO.readAll();
        request.setAttribute("listAffectations", listAffectation);
        RequestDispatcher dispatcher = request.getRequestDispatcher("affectationList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Personnel> personnels;
        List<Puce> puces;
        try {
            personnels = personnelDAO.listAllPersonnel();
            puces = puceDAO.listAllPuces();
        } catch (SQLException e) {
            throw new ServletException("Error fetching personnel or puce data", e);
        }
        request.setAttribute("personnels", personnels);
        request.setAttribute("puces", puces);
        request.setAttribute("formAction", "insert");
        RequestDispatcher dispatcher = request.getRequestDispatcher("affectationForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int affectationId = Integer.parseInt(request.getParameter("affectationId"));
        Affectation existingAffectation = affectationDAO.read(affectationId);
        List<Personnel> personnels = personnelDAO.listAllPersonnel();
        List<Puce> puces = puceDAO.listAllPuces();
        request.setAttribute("affectation", existingAffectation);
        request.setAttribute("personnels", personnels);
        request.setAttribute("puces", puces);
        request.setAttribute("formAction", "update");
        RequestDispatcher dispatcher = request.getRequestDispatcher("affectationForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertAffectation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String personnelIdParam = request.getParameter("personnelid");
        String puceIdParam = request.getParameter("puceId");

        if (personnelIdParam == null || personnelIdParam.isEmpty() ||
                puceIdParam == null || puceIdParam.isEmpty()) {
            request.setAttribute("errorMessage", "Personnel and Puce must be selected.");
            try {
                showNewForm(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        int personnelid = Integer.parseInt(personnelIdParam);
        int puceId = Integer.parseInt(puceIdParam);

        String dateDeDesaffectationParam = request.getParameter("dateDeDesaffectation");
        Date dateDeDesaffectation = null;

        if (dateDeDesaffectationParam != null && !dateDeDesaffectationParam.trim().isEmpty()) {
            dateDeDesaffectation = Date.valueOf(dateDeDesaffectationParam);
        }

        Affectation newAffectation = new Affectation();
        newAffectation.setPersonnelid(personnelid);
        newAffectation.setPuceId(puceId);
        newAffectation.setDateDeDesaffectation(dateDeDesaffectation);

        affectationDAO.create(newAffectation);
        response.sendRedirect("affectation?action=list");
    }

    private void updateAffectation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int affectationId = Integer.parseInt(request.getParameter("affectationId"));
        int personnelid = Integer.parseInt(request.getParameter("personnelid"));
        int puceId = Integer.parseInt(request.getParameter("puceId"));

        String dateDeDesaffectationParam = request.getParameter("dateDeDesaffectation");
        Date dateDeDesaffectation = null;

        if (dateDeDesaffectationParam != null && !dateDeDesaffectationParam.trim().isEmpty()) {
            dateDeDesaffectation = Date.valueOf(dateDeDesaffectationParam);
        }

        Affectation affectation = new Affectation();
        affectation.setAffectationId(affectationId);
        affectation.setPersonnelid(personnelid);
        affectation.setPuceId(puceId);
        affectation.setDateDeDesaffectation(dateDeDesaffectation);

        affectationDAO.update(affectation);
        response.sendRedirect("affectation?action=list");
    }

    private void deleteAffectation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int affectationId = Integer.parseInt(request.getParameter("affectationId"));
        affectationDAO.delete(affectationId);
        response.sendRedirect("affectation?action=list");
    }

    private void searchAffectation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String searchQuery = request.getParameter("search");
        System.out.println("Search Query: " + searchQuery); // Debugging line
        List<Affectation> searchResults = affectationDAO.search(searchQuery);
        System.out.println("Search Results: " + searchResults.size()); // Debugging line
        request.setAttribute("listAffectations", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("affectationList.jsp");
        dispatcher.forward(request, response);
    }

}
