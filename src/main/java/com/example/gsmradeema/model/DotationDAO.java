package com.example.gsmradeema.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DotationDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3305/gsm_manager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "admin";
    private Connection jdbcConnection;

    protected Connection getConnection() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                throw new SQLException(e);
            }
        }
        return jdbcConnection;
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public List<Dotation> readAll() throws SQLException {
        List<Dotation> dotations = new ArrayList<>();
        String sql = "SELECT d.dotationId, d.solde, d.dateAffectation, d.puceId, p.numero " +
                "FROM Dotation d " +
                "JOIN Puce p ON d.puceId = p.puceId";

        connect();

        try (Statement stmt = jdbcConnection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Dotation dotation = new Dotation(
                        rs.getInt("dotationId"),
                        rs.getString("solde"),
                        rs.getDate("dateAffectation"),
                        rs.getInt("puceId"),
                        rs.getString("numero")
                );
                dotations.add(dotation);
            }
        } finally {
            disconnect();
        }
        return dotations;
    }

    public void create(Dotation dotation) throws SQLException {
        String sql = "INSERT INTO Dotation (solde, dateAffectation, puceId) VALUES (?, ?, ?)";
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setString(1, dotation.getSolde());
            stmt.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Set current date
            stmt.setInt(3, dotation.getPuceId());
            stmt.executeUpdate();
        } finally {
            disconnect();
        }
    }

    public Dotation read(int dotationId) throws SQLException {
        Dotation dotation = null;
        String sql = "SELECT d.dotationId, d.solde, d.dateAffectation, d.puceId, p.numero " +
                "FROM Dotation d " +
                "JOIN Puce p ON d.puceId = p.puceId " +
                "WHERE d.dotationId = ?";

        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, dotationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dotation = new Dotation(
                            rs.getInt("dotationId"),
                            rs.getString("solde"),
                            rs.getDate("dateAffectation"),
                            rs.getInt("puceId"),
                            rs.getString("numero")
                    );
                }
            }
        } finally {
            disconnect();
        }
        return dotation;
    }

    public boolean update(Dotation dotation) throws SQLException {
        String sql = "UPDATE Dotation SET solde = ?, dateAffectation = ?, puceId = ? WHERE dotationId = ?";
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setString(1, dotation.getSolde());
            stmt.setDate(2, dotation.getDateAffectation() != null ? new java.sql.Date(dotation.getDateAffectation().getTime()) : new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(3, dotation.getPuceId());
            stmt.setInt(4, dotation.getDotationId());

            return stmt.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public boolean delete(int dotationId) throws SQLException {
        String sql = "DELETE FROM Dotation WHERE dotationId = ?";
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, dotationId);

            return stmt.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public List<Dotation> search(String soldeOrPuceNumber) throws SQLException {
        List<Dotation> dotations = new ArrayList<>();
        String sql = "SELECT d.dotationId, d.solde, d.dateAffectation, d.puceId, p.numero " +
                "FROM Dotation d " +
                "JOIN Puce p ON d.puceId = p.puceId " +
                "WHERE d.solde LIKE ? OR p.numero LIKE ?";
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setString(1, "%" + soldeOrPuceNumber + "%");
            stmt.setString(2, "%" + soldeOrPuceNumber + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Dotation dotation = new Dotation(
                            rs.getInt("dotationId"),
                            rs.getString("solde"),
                            rs.getDate("dateAffectation"),
                            rs.getInt("puceId"),
                            rs.getString("numero")
                    );
                    dotations.add(dotation);
                }
            }
        } finally {
            disconnect();
        }
        return dotations;
    }

    private void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (ClassNotFoundException e) {
                throw new SQLException("JDBC Driver not found", e);
            }
        }
    }
}
