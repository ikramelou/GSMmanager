<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="homestyle.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="container">
    <header class="navbar">
        <div class="logo">
            <img src="WhatsApp%20Image%202024-08-15%20at%2012.11.29.jpeg" alt="RADEEMA Logo">
        </div>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/dashboard">Home</a>
            <div class="dropdown">
                <button class="dropbtn">Gestion Organigramme</button>
                <div class="dropdown-content">
                    <a href="entite">Entite</a>
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
            <a href="user">Gestion Utilisateurs</a>
        </nav>
        <a href="logout" class="logout">Logout</a>
    </header>

    <main class="content">
        <h1>Nombre de puce par division</h1>
        <div class="statistics">
            <canvas id="puceChart"></canvas> <!-- Canvas for Chart -->
        </div>
    </main>
</div>

<script>
    // Prepare data for the chart
    const labels = [];
    const data = [];

    <c:forEach var="statistique" items="${statistiques}">
    labels.push('${statistique.entiteNom}');
    data.push(${statistique.nombrePuce});
    </c:forEach>

    // Create the bar chart
    const ctx = document.getElementById('puceChart').getContext('2d');
    const puceChart = new Chart(ctx, {
        type: 'bar', // Bar chart type
        data: {
            labels: labels,
            datasets: [{
                label: 'Number of Puces',
                data: data,
                backgroundColor: 'rgba(52, 152, 219, 0.2)', // Light blue background
                borderColor: 'rgba(41, 128, 185, 1)', // Dark blue border
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true // Start y-axis at 0
                }
            }
        }
    });
</script>

</body>
</html>
