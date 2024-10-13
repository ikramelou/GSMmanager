<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entité List</title>
    <link rel="stylesheet" href="homestyle.css">
    <style>
        /* Entité Table Styles */
        .entite-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .entite-table th, .entite-table td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        .entite-table th {
            background-color: #3498db; /* Blue color */
            color: white;
            text-align: left;
        }

        .entite-table tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .entite-table tr:hover {
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

        /* New Entité Button Styles */
        .new-entite-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .new-entite-button:hover {
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
                    <a href="personnel">Personnel</a>
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
            <h2>Entité List</h2>
            <form action="entite" method="get" class="search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" name="searchTerm" placeholder="Search">
                <input type="submit" value="Search">
            </form>
            <a href="entite?action=new" class="new-entite-button">New Entité</a>
            <table class="entite-table">
                <tr>
                    <th>Nom</th>
                    <th>Type</th>
                    <th>Entité Mère</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="entite" items="${listEntite}">
                    <tr>
                        <td>${entite.nom}</td>
                        <td>${entite.type}</td>
                        <td>${entite.entiteMere}</td>
                        <td>
                            <a href="entite?action=edit&id=${entite.id}" class="action-link">Edit</a>
                            <form action="entite" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${entite.id}">
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
