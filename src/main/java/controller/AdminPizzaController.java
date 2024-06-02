package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Pizza;
import test.DBUtil;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import DAO.PizzaDAO;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AdminPizzaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "uploads";
    private PizzaDAO pizzaDAO;

    public void init() throws ServletException {
        try {
            Connection connection = DBUtil.getConnection();
            pizzaDAO = new PizzaDAO(connection);
            // Create upload directory if not exists
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize database connection", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "insert":
                        insertPizza(request, response);
                        break;
                    case "delete":
                        deletePizza(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "update":
                        updatePizza(request, response);
                        break;
                    default:
                        listPizzas(request, response);
                        break;
                }
            } else {
                response.sendRedirect("AdminPizzaController?action=list");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listPizzas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Pizza> pizzas = pizzaDAO.getAllPizzas();
        request.setAttribute("pizzas", pizzas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pizza-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pizza-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Pizza existingPizza = pizzaDAO.getPizzaById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pizza-form.jsp");
        request.setAttribute("pizza", existingPizza);
        dispatcher.forward(request, response);
    }

    private void insertPizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String image = saveUploadedFile(request); // Save the uploaded file

        if (name != null && description != null && priceStr != null && image != null) {
            name = name.trim();
            description = description.trim();
            double price = Double.parseDouble(priceStr.trim());
            Pizza newPizza = new Pizza(name, description, price, image);

            try {
                pizzaDAO.insertPizza(newPizza);
                response.sendRedirect("AdminPizzaController?action=list");
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
        }
    }

    private void updatePizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String image = saveUploadedFile(request); // Save the uploaded file

        if (image == null || image.isEmpty()) {
            image = request.getParameter("existingImage"); // Use existing image if no new file is uploaded
        }

        Pizza pizza = new Pizza(id, name, description, price, image);
        pizzaDAO.updatePizza(pizza);
        response.sendRedirect("AdminPizzaController?action=list");
    }

    private void deletePizza(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        pizzaDAO.deletePizza(id);
        response.sendRedirect("AdminPizzaController?action=list");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    insertPizza(request, response);
                    break;
                case "update":
                    updatePizza(request, response);
                    break;
                case "delete":
                    deletePizza(request, response);
                    break;
                default:
                    response.sendRedirect("AdminPizzaController?action=list");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private String saveUploadedFile(HttpServletRequest request) throws IOException, ServletException {
        for (Part part : request.getParts()) {
            String fileName = getFileName(part);
            if (fileName != null && !fileName.isEmpty()) {
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File file = new File(uploadPath + File.separator + fileName);
                part.write(file.getAbsolutePath());
                // Return relative path
                return UPLOAD_DIRECTORY + "/" + fileName;
            }
        }
        return null;
    }


    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
}
