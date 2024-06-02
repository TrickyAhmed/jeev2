<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.User"%>
<%
    String currentURI = request.getRequestURI();
    String userRole = null;
    if (session.getAttribute("user") != null) {
        userRole = ((User) session.getAttribute("user")).getRole();
    }
%>

<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
    <div class="container">
        <a class="navbar-brand" href="index.html"><span class="flaticon-pizza-1 mr-1"></span>Pizza<br><small>Delicious</small></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="oi oi-menu"></span> Menu
        </button>
        <div class="collapse navbar-collapse" id="ftco-nav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item <%= currentURI.contains("home.jsp") ? "active" : "" %>">
                    <a href="home.jsp" class="nav-link">Home</a>
                </li>
                <li class="nav-item <%= currentURI.contains("ChefController") ? "active" : "" %>">
                    <a href="ChefController" class="nav-link">About</a>
                </li>
                <li class="nav-item <%= currentURI.contains("contact.jsp") ? "active" : "" %>">
                    <a href="contact.jsp" class="nav-link">Contact</a>
                </li>
                <% if (userRole != null) { %>
                    <% if (userRole.equals("Administrateur")) { %>
                        <li class="nav-item <%= currentURI.contains("AdminPizzaController") ? "active" : "" %>">
                            <a href="AdminPizzaController" class="nav-link">Liste des Pizzas</a>
                        </li>
                        <li class="nav-item <%= currentURI.contains("AdminChefController") ? "active" : "" %>">
                            <a href="AdminChefController" class="nav-link">Liste des Chefs</a>
                        </li>
                    <% } %>
                    <li class="nav-item">
                        <a href="LogoutServlet" class="nav-link">Logout</a>
                    </li>
                <% } else { %>
                    <li class="nav-item <%= currentURI.contains("user/authentification.jsp") ? "active" : "" %>">
                        <a href="user/authentification.jsp" class="nav-link">Login</a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
