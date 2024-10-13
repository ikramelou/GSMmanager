package com.example.gsmradeema.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatistiqueDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3305/gsm_manager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "admin";
    private Connection jdbcConnection;

    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        return connection;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("JDBC Driver not found", e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public List<Statistique> getNombrePuceParEntite() throws SQLException {
        List<Statistique> statistiques = new ArrayList<>();
        String query = "SELECT e.nom, COUNT(pu.puceId) AS nombrePuce " +
                "FROM Entites e " +
                "JOIN Personnel p ON e.id = p.entiteId " +
                "JOIN Affectation a ON p.personnelid = a.personnelid " +
                "JOIN Puce pu ON a.puceId = pu.puceId " +
                "GROUP BY e.nom";

        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Statistique stat = new Statistique();
                stat.setEntiteNom(rs.getString("nom")); // Match column alias 'nom' from your query
                stat.setNombrePuce(rs.getInt("nombrePuce"));
                statistiques.add(stat);
            }

        } finally {
            disconnect();
        }

        return statistiques;
    }
}
