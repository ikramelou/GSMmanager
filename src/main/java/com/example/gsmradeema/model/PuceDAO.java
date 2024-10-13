package com.example.gsmradeema.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuceDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3305/gsm_manager?useSSL=false&useUnicode=true&characterEncoding=utf8";
    private String jdbcUsername = "root";
    private String jdbcPassword = "admin";

    // Ensure connection is managed properly using try-with-resources
    protected Connection getConnection() throws SQLException {
        Connection jdbcConnection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        return jdbcConnection;
    }

    public List<Puce> listAllPuces() throws SQLException {
        List<Puce> listPuces = new ArrayList<>();
        String sql = "SELECT * FROM puce";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int puceId = resultSet.getInt("PuceId");
                String numero = resultSet.getString("numero");
                String code = resultSet.getString("code");
                String etat = resultSet.getString("etat");
                String type = resultSet.getString("type");  // Fetch the type column

                Puce puce = new Puce(puceId, numero, code, etat, type);  // Include type in the constructor
                listPuces.add(puce);
            }
        }

        return listPuces;
    }

    public void insertPuce(Puce puce) throws SQLException {
        if (puce == null || puce.getNumero() == null || puce.getCode() == null || puce.getEtat() == null || puce.getType() == null) {
            throw new SQLException("Puce or required fields are null");
        }
        String sql = "INSERT INTO puce (numero, code, etat, type) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, puce.getNumero());
            statement.setString(2, puce.getCode());
            statement.setString(3, puce.getEtat());
            statement.setString(4, puce.getType());  // Set the type value
            statement.executeUpdate();
        }
    }

    public boolean updatePuce(Puce puce) throws SQLException {
        if (puce == null || puce.getNumero() == null || puce.getCode() == null || puce.getEtat() == null || puce.getType() == null) {
            throw new SQLException("Puce or required fields are null");
        }
        String sql = "UPDATE puce SET numero = ?, code = ?, etat = ?, type = ? WHERE PuceId = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, puce.getNumero());
            statement.setString(2, puce.getCode());
            statement.setString(3, puce.getEtat());
            statement.setString(4, puce.getType());  // Set the type value
            statement.setInt(5, puce.getPuceId());

            return statement.executeUpdate() > 0;  // Returns true if at least one row is updated
        }
    }

    public boolean deletePuce(int puceId) throws SQLException {
        String sql = "DELETE FROM puce WHERE PuceId = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, puceId);
            return statement.executeUpdate() > 0;  // Returns true if at least one row is deleted
        }
    }

    public Puce getPuce(int puceId) throws SQLException {
        Puce puce = null;
        String sql = "SELECT * FROM puce WHERE PuceId = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, puceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String numero = resultSet.getString("numero");
                    String code = resultSet.getString("code");
                    String etat = resultSet.getString("etat");
                    String type = resultSet.getString("type");  // Fetch the type column

                    puce = new Puce(puceId, numero, code, etat, type);  // Include type in the constructor
                }
            }
        }

        return puce;
    }

    public List<Puce> searchPuces(String searchTerm) throws SQLException {
        List<Puce> puces = new ArrayList<>();
        String sql = "SELECT * FROM puce WHERE numero LIKE ? OR code LIKE ? OR etat LIKE ? OR type LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String query = "%" + searchTerm + "%";
            statement.setString(1, query);
            statement.setString(2, query);
            statement.setString(3, query);
            statement.setString(4, query);  // Include search for type

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int puceId = resultSet.getInt("PuceId");
                    String numero = resultSet.getString("numero");
                    String code = resultSet.getString("code");
                    String etat = resultSet.getString("etat");
                    String type = resultSet.getString("type");  // Fetch the type column

                    Puce puce = new Puce(puceId, numero, code, etat, type);  // Include type in the constructor
                    puces.add(puce);
                }
            }
        }

        return puces;
    }
}
