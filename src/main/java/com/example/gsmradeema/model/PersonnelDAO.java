package com.example.gsmradeema.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDAO {
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

    public List<Personnel> listAllPersonnel() throws SQLException {
        List<Personnel> listPersonnel = new ArrayList<>();
        String sql = "SELECT p.personnelid, p.nom, p.prenom, p.matricule, e.nom AS entite_nom " +
                "FROM personnel p JOIN entites e ON p.entiteID = e.id";

        connect();

        try (Statement statement = jdbcConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("personnelid");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String matricule = resultSet.getString("matricule");
                String entiteNom = resultSet.getString("entite_nom");

                Personnel personnel = new Personnel();
                personnel.setPersonnelid(id);
                personnel.setNom(nom);
                personnel.setPrenom(prenom);
                personnel.setMatricule(matricule);
                personnel.setEntiteNom(entiteNom); // Adjusted to set entite name
                listPersonnel.add(personnel);
            }
        } finally {
            disconnect();
        }

        return listPersonnel;
    }

    public void insertPersonnel(Personnel personnel) throws SQLException {
        String sql = "INSERT INTO personnel (nom, prenom, matricule, entiteID) VALUES (?, ?, ?, ?)";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, personnel.getNom());
            statement.setString(2, personnel.getPrenom());
            statement.setString(3, personnel.getMatricule());
            statement.setInt(4, personnel.getEntiteID());

            statement.executeUpdate();
        } finally {
            disconnect();
        }
    }

    public boolean updatePersonnel(Personnel personnel) throws SQLException {
        String sql = "UPDATE personnel SET nom = ?, prenom = ?, matricule = ?, entiteID = ? WHERE personnelid = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, personnel.getNom());
            statement.setString(2, personnel.getPrenom());
            statement.setString(3, personnel.getMatricule());
            statement.setInt(4, personnel.getEntiteID());
            statement.setInt(5, personnel.getPersonnelid());

            return statement.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public boolean deletePersonnel(int id) throws SQLException {
        String sql = "DELETE FROM personnel WHERE personnelid = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public List<Personnel> searchPersonnel(String searchTerm) throws SQLException {
        List<Personnel> personnelList = new ArrayList<>();
        String sql = "SELECT p.personnelid, p.nom, p.prenom, p.matricule, e.nom AS entite_nom " +
                "FROM personnel p JOIN entites e ON p.entiteID = e.id " +
                "WHERE p.nom LIKE ? OR p.prenom LIKE ? OR p.matricule LIKE ? OR e.nom LIKE ?";
        connect();

        try (PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql)) {
            String query = "%" + searchTerm + "%";
            preparedStatement.setString(1, query);
            preparedStatement.setString(2, query);
            preparedStatement.setString(3, query);
            preparedStatement.setString(4, query); // Adding the search term for Entite name
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("personnelid");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String matricule = resultSet.getString("matricule");
                String entiteNom = resultSet.getString("entite_nom");

                Personnel personnel = new Personnel();
                personnel.setPersonnelid(id);
                personnel.setNom(nom);
                personnel.setPrenom(prenom);
                personnel.setMatricule(matricule);
                personnel.setEntiteNom(entiteNom); // Adjusted to set entite name
                personnelList.add(personnel);
            }
        } finally {
            disconnect();
        }
        return personnelList;
    }


    public Personnel getPersonnel(int id) throws SQLException {
        Personnel personnel = null;
        String sql = "SELECT p.personnelid, p.nom, p.prenom, p.matricule, e.nom AS entite_nom " +
                "FROM personnel p JOIN entites e ON p.entiteID = e.id " +
                "WHERE p.personnelid = ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String matricule = resultSet.getString("matricule");
                    String entiteNom = resultSet.getString("entite_nom");

                    personnel = new Personnel();
                    personnel.setPersonnelid(id);
                    personnel.setNom(nom);
                    personnel.setPrenom(prenom);
                    personnel.setMatricule(matricule);
                    personnel.setEntiteNom(entiteNom); // Adjusted to set entite name
                }
            }
        } finally {
            disconnect();
        }

        return personnel;
    }
}
