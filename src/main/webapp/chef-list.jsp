<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.Chef" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pizza List</title>
    <%@ include file="/include/css.jsp"%>   
    <style>
        /* Ajouter un style pour limiter la taille des images */
        .chef-image {
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
        <h2>Chef List</h2>
        <a href="AdminChefController?action=new">Add New Chef</a>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>	
            <tbody>
                <%
                    List<Chef> chefs = (List<Chef>) request.getAttribute("chefs");
                    if (chefs != null) {
                        for (Chef chef : chefs) {
                %>
                <tr>
                    <td><%= chef.getId() %></td>
                    <td><%= chef.getName() %></td>
                    <td><%= chef.getDescription() %></td>
                
                    <td><img src="<%= chef.getImage() %>" alt="<%= chef.getName() %>" class="chef-image"></td>
                    <td class="action-buttons">
                        <a href="AdminChefController?action=edit&id=<%= chef.getId() %>"><i class="fas fa-edit"></i></a>
                        <!-- Ajouter une classe CSS pour une séparation -->
                        <a href="AdminChefController?action=delete&id=<%= chef.getId() %>" onclick="return confirm('Are you sure you want to delete this chef?')"><i class="fas fa-trash"></i></a>
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
