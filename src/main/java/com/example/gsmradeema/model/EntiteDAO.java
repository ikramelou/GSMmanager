package com.example.gsmradeema.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntiteDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3305/gsm_manager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "admin";
    private Connection jdbcConnection;

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

    public List<Entite> listAllEntites() throws SQLException {
        List<Entite> listEntite = new ArrayList<>();
        String sql = "SELECT * FROM entites";

        connect();

        try (Statement statement = jdbcConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String type = resultSet.getString("type");
                String entiteMere = resultSet.getString("entite_mere");

                Entite entite = new Entite(nom, type, entiteMere);
                entite.setId(id);
                listEntite.add(entite);
            }
        } finally {
            disconnect();
        }

        return listEntite;
    }

    public void insertEntite(Entite entite) throws SQLException {
        String sql = "INSERT INTO entites (nom, type, entite_mere) VALUES (?, ?, ?)";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, entite.getNom());
            statement.setString(2, entite.getType());
            statement.setString(3, entite.getEntiteMere());

            statement.executeUpdate();
        } finally {
            disconnect();
        }
    }

    public boolean updateEntite(Entite entite) throws SQLException {
        String sql = "UPDATE entites SET nom = ?, type = ?, entite_mere = ? WHERE id = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, entite.getNom());
            statement.setString(2, entite.getType());
            statement.setString(3, entite.getEntiteMere());
            statement.setInt(4, entite.getId());

            return statement.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public boolean deleteEntite(int id) throws SQLException {
        String sql = "DELETE FROM entites WHERE id = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public Entite getEntite(int id) throws SQLException {
        Entite entite = null;
        String sql = "SELECT * FROM entites WHERE id = ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String type = resultSet.getString("type");
                    String entiteMere = resultSet.getString("entite_mere");

                    entite = new Entite(nom, type, entiteMere);
                    entite.setId(id);
                }
            }
        } finally {
            disconnect();
        }

        return entite;
    }

    // New method for searching entities based on a search term
    public List<Entite> searchEntites(String searchTerm) throws SQLException {
        List<Entite> listEntite = new ArrayList<>();
        String sql = "SELECT * FROM entites WHERE nom LIKE ? OR type LIKE ? OR entite_mere LIKE ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String type = resultSet.getString("type");
                    String entiteMere = resultSet.getString("entite_mere");

                    Entite entite = new Entite(nom, type, entiteMere);
                    entite.setId(id);
                    listEntite.add(entite);
                }
            }
        } finally {
            disconnect();
        }

        return listEntite;
    }
}
