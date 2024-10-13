<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Puce List</title>
    <link rel="stylesheet" href="homestyle.css">
    <style>
        /* Puce Table Styles */
        .puce-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .puce-table th, .puce-table td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        .puce-table th {
            background-color: #3498db; /* Blue color */
            color: white;
            text-align: left;
        }

        .puce-table tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .puce-table tr:hover {
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

        /* New Puce Button Styles */
        .new-puce-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .new-puce-button:hover {
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
            <h2>Puce List</h2>
            <form action="puce" method="get" class="search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" name="search" placeholder="Search">
                <input type="submit" value="Search">
            </form>
            <a href="puce?action=new" class="new-puce-button">New Puce</a>
            <table class="puce-table">
                <tr>
                    <th>Numero</th>
                    <th>Code</th>
                    <th>Etat</th>
                    <th>Type</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="puce" items="${listPuces}">
                    <tr>
                        <td>${puce.numero}</td>
                        <td>${puce.code}</td>
                        <td>${puce.etat}</td>
                        <td>${puce.type}</td>
                        <td>
                            <a href="puce?action=edit&id=${puce.puceId}" class="action-link">Edit</a>
                            <form action="puce" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${puce.puceId}">
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
