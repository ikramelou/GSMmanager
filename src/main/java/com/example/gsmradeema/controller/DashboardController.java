package com.example.gsmradeema.controller;

import com.example.gsmradeema.model.StatistiqueDAO;
import com.example.gsmradeema.model.Statistique;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StatistiqueDAO statistiqueDAO = new StatistiqueDAO();
        List<Statistique> statistiques = null;
        try {
            statistiques = statistiqueDAO.getNombrePuceParEntite();
            System.out.println("Statistiques size: " + (statistiques != null ? statistiques.size() : "null"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("statistiques", statistiques);
        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }
}

