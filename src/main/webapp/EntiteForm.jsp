<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire Entité</title>
    <link rel="stylesheet" href="homestyle.css">
    <style>
        .center-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
            margin-top: 50px;
        }

        .form-container {
            background-color: #f9f9f9;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
        }

        .form-container h2 {
            margin-bottom: 20px;
            text-align: center;
            color: #3498db;
        }

        .form-container table {
            width: 100%;
        }

        .form-container table td {
            padding: 10px;
        }

        .form-container input[type="text"], .form-container select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .form-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-container input[type="submit"]:hover {
            background-color: #2980b9;
        }

        .back-link {
            margin-top: 20px;
            display: block;
            text-align: center;
            color: #3498db;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="center-content">
    <div class="form-container">
        <h2>Entité Form</h2>
        <form action="${action}" method="post">
            <input type="hidden" name="id" value="${entite.id}">
            <table>
                <tr>
                    <td>Nom</td>
                    <td><input type="text" name="nom" value="${entite.nom}" required></td>
                </tr>
                <tr>
                    <td>Type</td>
                    <td><input type="text" name="type" value="${entite.type}" required></td>
                </tr>
                <tr>
                    <td>Entité Mère</td>
                    <td><input type="text" name="entiteMere" value="${entite.entiteMere}"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Submit"></td>
                </tr>
            </table>
        </form>
        <a href="entite?action=list" class="back-link">Back</a>
    </div>
</div>
</body>
</html>
