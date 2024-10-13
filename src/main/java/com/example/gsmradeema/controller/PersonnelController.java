package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.Entite;
import com.example.gsmradeema.model.EntiteDAO;
import com.example.gsmradeema.model.Personnel;
import com.example.gsmradeema.model.PersonnelDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PersonnelController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PersonnelDAO personnelDAO;
    private EntiteDAO entiteDAO;

    @Override
    public void init() throws ServletException {
        personnelDAO = new PersonnelDAO();
        entiteDAO = new EntiteDAO();
    }

    @Override
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
                    deletePersonnel(request, response);
                    break;
                case "search":
                    searchPersonnel(request, response);
                    break;
                default:
                    listPersonnel(request, response);
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
            switch (action) {
                case "insert":
                    insertPersonnel(request, response);
                    break;
                case "update":
                    updatePersonnel(request, response);
                    break;
                case "search":
                    searchPersonnel(request, response);
                    break;
                case "delete":
                    deletePersonnel(request, response);
                    break;
                default:
                    listPersonnel(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listPersonnel(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Personnel> listPersonnel = personnelDAO.listAllPersonnel();
        request.setAttribute("listPersonnel", listPersonnel);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonnelList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Entite> listEntites = null;
        try {
            listEntites = entiteDAO.listAllEntites();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("listEntites", listEntites);
        Personnel newPersonnel = new Personnel();
        request.setAttribute("personnel", newPersonnel);
        request.setAttribute("formAction", "insert"); // Use 'formAction' instead of 'action' to differentiate form actions
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonnelForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("personnelid"));
        Personnel existingPersonnel = personnelDAO.getPersonnel(id);
        List<Entite> listEntites = entiteDAO.listAllEntites();
        request.setAttribute("listEntites", listEntites);
        request.setAttribute("personnel", existingPersonnel);
        request.setAttribute("formAction", "update"); // Use 'formAction' instead of 'action' to differentiate form actions
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonnelForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertPersonnel(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String matricule = request.getParameter("matricule");
        int entiteID = Integer.parseInt(request.getParameter("entiteID"));

        System.out.println("Inserting Personnel: " + nom + " " + prenom + " " + matricule + " " + entiteID);

        Personnel newPersonnel = new Personnel();
        newPersonnel.setNom(nom);
        newPersonnel.setPrenom(prenom);
        newPersonnel.setMatricule(matricule);
        newPersonnel.setEntiteID(entiteID);
        personnelDAO.insertPersonnel(newPersonnel);
        response.sendRedirect("personnel?action=list");
    }


    private void updatePersonnel(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("personnelid"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String matricule = request.getParameter("matricule");
        int entiteID = Integer.parseInt(request.getParameter("entiteID"));

        Personnel personnel = new Personnel();
        personnel.setPersonnelid(id);
        personnel.setNom(nom);
        personnel.setPrenom(prenom);
        personnel.setMatricule(matricule);
        personnel.setEntiteID(entiteID);
        personnelDAO.updatePersonnel(personnel);
        response.sendRedirect("personnel?action=list");
    }

    private void deletePersonnel(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("personnelid"));
        personnelDAO.deletePersonnel(id);
        response.sendRedirect("personnel?action=list");
    }

    private void searchPersonnel(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String searchTerm = request.getParameter("searchTerm");
        List<Personnel> searchResults = personnelDAO.searchPersonnel(searchTerm);
        request.setAttribute("listPersonnel", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonnelList.jsp");
        dispatcher.forward(request, response);
    }
}
