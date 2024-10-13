<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Affectation List</title>
    <link rel="stylesheet" href="homestyle.css">
    <style>
        /* Affectation Table Styles */
        .affectation-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .affectation-table th, .affectation-table td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        .affectation-table th {
            background-color: #3498db; /* Blue color */
            color: white;
            text-align: left;
        }

        .affectation-table tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .affectation-table tr:hover {
            background-color: #ddd;
        }

        /* Center Content Styles */
        .center-content {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* Search Form Styles */
        .search-form {
            margin-bottom: 20px;
            display: flex;
            justify-content: center;
            width: 100%;
        }

        .search-form input[type="text"] {
            width: 300px;
            padding: 8px;
            margin-right: 10px;
        }

        .search-form input[type="submit"] {
            padding: 8px 15px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .search-form input[type="submit"]:hover {
            background-color: #2980b9;
        }

        /* Action Links Styles */
        .action-link {
            color: #3498db;
            text-decoration: none;
            margin-right: 10px;
        }

        .action-link:hover {
            text-decoration: underline;
        }

        .action-button {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .action-button:hover {
            background-color: #c0392b;
        }

        /* New Affectation Button Styles */
        .new-affectation-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .new-affectation-button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<div class="container">
    <header class="navbar">
        <div class="logo">
            <img src="WhatsApp%20Image%202024-08-15%20at%2012.11.29.jpeg" alt="RADEEMA Logo">
        </div>
        <nav class="nav-links">
            <a href="welcome.jsp">Home</a>
            <div class="dropdown">
                <button class="dropbtn">Gestion Organigramme</button>
                <div class="dropdown-content">
                    <a href="entite?action=list">Entité</a>
                    <a href="personnel?action=list">Personnel</a>
                </div>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Puce</button>
                <div class="dropdown-content">
                    <a href="puce">Puce</a>
                    <a href="dotation">Dotation</a>
                    <a href="affectation">Affectation de puce</a>
                </div>
            </div>
            <a href="user?action=list">Gestion Utilisateurs</a>
        </nav>
        <a href="logout" class="logout">Logout</a>
    </header>
    <main class="content">
        <div class="center-content">
            <h2>Affectation List</h2>
            <form action="affectation" method="get" class="search-form">
                <input type="text" name="search" placeholder="Search" value="${param.search}">
                <input type="hidden" name="action" value="search">
                <button type="submit">Search</button>
            </form>
            <a href="affectation?action=new" class="new-affectation-button">New Affectation</a>
            <table class="affectation-table">
                <tr>
                    <th>Personnel</th>
                    <th>Puce Number</th>
                    <th>Date d'Affectation</th>
                    <th>Date de Désaffectation</th> <!-- New column -->
                    <th>Actions</th>
                </tr>
                <c:forEach var="affectation" items="${listAffectations}">
                    <tr>
                        <td>${affectation.matricule}</td>
                        <td>${affectation.numero}</td>
                        <td>${affectation.datteDaffectation}</td>
                        <td>${affectation.dateDeDesaffectation}</td> <!-- Display the desaffectation date -->
                        <td>
                            <a href="affectation?action=edit&affectationId=${affectation.affectationId}" class="action-link">Edit</a>
                            <form action="affectation" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="affectationId" value="${affectation.affectationId}">
                                <button type="submit" class="action-button" onclick="return confirm('Are you sure?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </main>
</div>
</body>
</html>
