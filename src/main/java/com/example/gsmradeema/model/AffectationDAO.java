package com.example.gsmradeema.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AffectationDAO {
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

    public List<Affectation> readAll() throws SQLException {
        List<Affectation> affectations = new ArrayList<>();
        String sql = "SELECT a.affectationId, a.personnelid, a.puceId, a.datteDaffectation, a.dateDeDesaffectation, " +
                "p.matricule AS matricule, c.numero AS numero " +
                "FROM Affectation a " +
                "JOIN Personnel p ON a.personnelid = p.personnelid " +
                "JOIN Puce c ON a.puceId = c.PuceId";

        connect();

        try (Statement stmt = jdbcConnection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Affectation affectation = new Affectation();
                affectation.setAffectationId(rs.getInt("affectationId"));
                affectation.setPersonnelid(rs.getInt("personnelid"));
                affectation.setPuceId(rs.getInt("puceId"));
                affectation.setDatteDaffectation(rs.getDate("datteDaffectation"));
                affectation.setDateDeDesaffectation(rs.getDate("dateDeDesaffectation"));
                affectation.setMatricule(rs.getString("matricule"));
                affectation.setNumero(rs.getString("numero"));

                affectations.add(affectation);
            }
        } finally {
            disconnect();
        }
        return affectations;
    }

    public void create(Affectation affectation) throws SQLException {
        String sql = "INSERT INTO Affectation (personnelid, puceId, datteDaffectation) VALUES (?, ?, ?)";
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, affectation.getPersonnelid());
            stmt.setInt(2, affectation.getPuceId());
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Set current date
            stmt.executeUpdate();
        } finally {
            disconnect();
        }
    }

    public Affectation read(int affectationId) throws SQLException {
        Affectation affectation = null;
        String sql = "SELECT a.affectationId, a.personnelid, a.puceId, a.datteDaffectation, a.dateDeDesaffectation, " +
                "p.matricule AS matricule, c.numero AS numero " +
                "FROM Affectation a " +
                "JOIN Personnel p ON a.personnelid = p.personnelid " +
                "JOIN Puce c ON a.puceId = c.PuceId " +
                "WHERE a.affectationId = ?";

        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, affectationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    affectation = new Affectation();
                    affectation.setAffectationId(rs.getInt("affectationId"));
                    affectation.setPersonnelid(rs.getInt("personnelid"));
                    affectation.setPuceId(rs.getInt("puceId"));
                    affectation.setDatteDaffectation(rs.getDate("datteDaffectation"));
                    affectation.setDateDeDesaffectation(rs.getDate("dateDeDesaffectation"));
                    affectation.setMatricule(rs.getString("matricule"));
                    affectation.setNumero(rs.getString("numero"));
                }
            }
        } finally {
            disconnect();
        }
        return affectation;
    }

    public boolean update(Affectation affectation) throws SQLException {
        String sql = "UPDATE Affectation SET personnelid = ?, puceId = ?, dateDeDesaffectation = ? WHERE affectationId = ?";
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, affectation.getPersonnelid());
            stmt.setInt(2, affectation.getPuceId());
            stmt.setDate(3, affectation.getDateDeDesaffectation() != null ? new java.sql.Date(affectation.getDateDeDesaffectation().getTime()) : null);
            stmt.setInt(4, affectation.getAffectationId());

            return stmt.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public boolean delete(int affectationId) throws SQLException {
        String sql = "DELETE FROM Affectation WHERE affectationId = ?";
        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, affectationId);

            return stmt.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public List<Affectation> search(String personnelMatriculeOrPuceNumber) throws SQLException {
        List<Affectation> affectations = new ArrayList<>();
        String sql = "SELECT a.affectationId, a.personnelid, a.puceId, a.datteDaffectation, a.dateDeDesaffectation, " +
                "p.matricule AS matricule, c.numero AS numero " +
                "FROM Affectation a " +
                "JOIN Personnel p ON a.personnelid = p.personnelid " +
                "JOIN Puce c ON a.puceId = c.PuceId " +
                "WHERE p.matricule LIKE ? OR c.numero LIKE ?";

        connect();

        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setString(1, "%" + personnelMatriculeOrPuceNumber + "%");
            stmt.setString(2, "%" + personnelMatriculeOrPuceNumber + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Affectation affectation = new Affectation();
                    affectation.setAffectationId(rs.getInt("affectationId"));
                    affectation.setPersonnelid(rs.getInt("personnelid"));
                    affectation.setPuceId(rs.getInt("puceId"));
                    affectation.setDatteDaffectation(rs.getDate("datteDaffectation"));
                    affectation.setDateDeDesaffectation(rs.getDate("dateDeDesaffectation"));
                    affectation.setMatricule(rs.getString("matricule"));
                    affectation.setNumero(rs.getString("numero"));
                    affectations.add(affectation);
                }
            }
        } finally {
            disconnect();
        }
        return affectations;
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
