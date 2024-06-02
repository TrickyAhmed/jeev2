<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Authentification</title>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .container {
        background: #fff;
        padding: 3rem;
        border-radius: 20px;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
        text-align: center;
        width: 400px;
    }
    h1 {
        color: #333;
        margin-bottom: 2rem;
        font-size: 28px;
        font-weight: bold;
    }
    form {
        margin-top: 2rem;
    }
    .input-container {
        position: relative;
        margin-bottom: 20px;
    }
    input[type='text'],
    input[type='password'] {
        width: calc(100% - 40px);
        padding: 15px;
        border: none;
        border-radius: 30px;
        background-color: #f9f9f9;
        font-size: 16px;
        transition: background-color 0.3s;
    }
    input[type='text']:focus,
    input[type='password']:focus {
        background-color: #e0e0e0;
        outline: none;
    }
    input[type='text']:not(:placeholder-shown),
    input[type='password']:not(:placeholder-shown) {
        background-color: #e0e0e0;
    }
    input[type='text']:placeholder-shown,
    input[type='password']:placeholder-shown {
        padding-left: 40px;
        transition: padding-left 0.3s;
    }
    input[type='submit'] {
        background: linear-gradient(135deg, #72edf2 0%, #5151e5 100%);
        color: white;
        padding: 15px 30px;
        border: none;
        border-radius: 30px;
        cursor: pointer;
        font-weight: bold;
        font-size: 16px;
        transition: background 0.3s ease;
    }
    input[type='submit']:hover {
        background: linear-gradient(135deg, #5151e5 0%, #72edf2 100%);
    }
    .message {
        color: red;
        margin-bottom: 20px;
        font-size: 18px;
    }
    a {
        display: inline-block;
        margin-top: 1rem;
        color: #007bff;
        text-decoration: none;
        font-weight: bold;
        transition: color 0.3s ease;
    }
    a:hover {
        color: #0056b3;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>Authentification</h1>
        <%
        String m = (String) session.getAttribute("message");
        if (m != null) {
        %>
        <div class="message"><%=m%></div>
        <%
        }
        %>
        <form action='../UserController' method="post">
            <div class="input-container">
                <input type='text' name='login' placeholder='Login' required>
            </div>
            <div class="input-container">
                <input type='password' name='pass' placeholder='Password' required>
            </div>
            <div>
                <input type='submit' name='authentification' value='Login'>
            </div>
        </form>
        <a href='InscriptionForm.html'>Inscription</a>
    </div>
</body>
</html>
