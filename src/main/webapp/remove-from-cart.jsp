<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.Cart" %>
<%
    // Récupérer l'index de l'élément à supprimer
    int index = Integer.parseInt(request.getParameter("index"));

    // Récupérer le panier depuis la session
    Cart cart = (Cart) session.getAttribute("cart");

    // Supprimer l'élément du panier
    if (cart != null) {
        cart.removeItem(index);
    }

    // Rediriger vers la page du panier
    response.sendRedirect("cart.jsp");
%>
