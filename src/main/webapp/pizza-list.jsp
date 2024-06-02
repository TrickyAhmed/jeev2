<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.Pizza" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pizza List</title>
    <%@ include file="/include/css.jsp"%>   
    <style>
        /* Ajouter un style pour limiter la taille des images */
        .pizza-image {
            max-width: 100px; /* Définir la largeur maximale */
            max-height: 100px; /* Définir la hauteur maximale */
        }
        /* Ajouter une séparation entre les boutons Edit et Delete */
        .action-buttons a {
            margin-right: 10px; /* Ajouter une marge à droite */
        }
    </style>
</head>
<body>
    <%@ include file="/include/header.jsp"%>
    <div class="container">
        <h2>Pizza List</h2>
        <a href="AdminPizzaController?action=new">Add New Pizza</a>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Pizza> pizzas = (List<Pizza>) request.getAttribute("pizzas");
                    if (pizzas != null) {
                        for (Pizza pizza : pizzas) {
                %>
                <tr>
                    <td><%= pizza.getId() %></td>
                    <td><%= pizza.getName() %></td>
                    <td><%= pizza.getDescription() %></td>
                    <td><%= pizza.getPrice() %></td>
                    <td><img src="<%= pizza.getImage() %>" alt="<%= pizza.getName() %>" class="pizza-image"></td>
                    <td class="action-buttons">
                        <a href="AdminPizzaController?action=edit&id=<%= pizza.getId() %>"><i class="fas fa-edit"></i></a>
                        <!-- Ajouter une classe CSS pour une séparation -->
                        <a href="AdminPizzaController?action=delete&id=<%= pizza.getId() %>" onclick="return confirm('Are you sure you want to delete this pizza?')"><i class="fas fa-trash"></i></a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6"></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
