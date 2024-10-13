package com.example.gsmradeema.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
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

    public boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        connect();
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } finally {
            disconnect();
        }
    }

    public List<User> listAllUsers() throws SQLException {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM users";

        connect();

        try (Statement statement = jdbcConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Profil profil = Profil.valueOf(resultSet.getString("profil").toUpperCase());

                User user = new User(username, password, profil);
                listUser.add(user);
            }
        } finally {
            disconnect();
        }

        return listUser;
    }

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, profil) VALUES (?, ?, ?)";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getProfil().name());

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected (insert): " + rowsAffected);
        } finally {
            disconnect();
        }
    }


    public boolean updateUser(User user, String originalUsername) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, profil = ? WHERE username = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getProfil().name());
            statement.setString(4, originalUsername);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } finally {
            disconnect();
        }
    }


    public boolean deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, username);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected (delete): " + rowsAffected);
            return rowsAffected > 0;
        } finally {
            disconnect();
        }
    }

    public User getUser(String username) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString("password");
                    Profil profil = Profil.valueOf(resultSet.getString("profil").toUpperCase());
                    user = new User(username, password, profil);
                }
            }
        } finally {
            disconnect();
        }

        return user;
    }
    public List<User> searchUsers(String searchTerm) throws SQLException {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE username LIKE ? OR password LIKE ? OR profil LIKE ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Profil profil = Profil.valueOf(resultSet.getString("profil").toUpperCase());

                    User user = new User(username, password, profil);
                    listUser.add(user);
                }
            }
        } finally {
            disconnect();
        }

        return listUser;
    }
}
