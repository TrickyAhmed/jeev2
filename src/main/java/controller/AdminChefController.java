package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.ChefDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Chef;
import test.DBUtil;

/**
 * Servlet implementation class AdminChefController
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AdminChefController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "uploads";
	private ChefDAO ChefDAO;

	public void init() throws ServletException {
		try {
			Connection connection = DBUtil.getConnection();
			ChefDAO = new ChefDAO(connection);
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			if (action != null) {
				switch (action) {
				case "new":
					showNewForm(request, response);
					break;
				case "insert":
					insertChef(request, response);
					break;
				case "delete":
					deleteChef(request, response);
					break;
				case "edit":
					showEditForm(request, response);
					break;
				case "update":
					updateChef(request, response);
					break;
				default:
					listChefs(request, response);
					break;
				}
			} else {
				response.sendRedirect("AdminChefController?action=list");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void listChefs(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, ServletException, IOException {
	    List<Chef> chefs = ChefDAO.getAllChefs();
	    request.setAttribute("chefs", chefs); // Assurez-vous d'utiliser "chefs" au lieu de "Chefs"
	    RequestDispatcher dispatcher = request.getRequestDispatcher("chef-list.jsp");
	    dispatcher.forward(request, response);
	}


	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("chef-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Chef existingChef = ChefDAO.getChefById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("chef-form.jsp");
		request.setAttribute("Chef", existingChef);
		dispatcher.forward(request, response);
	}

	private void insertChef(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String image = saveUploadedFile(request); // Save the uploaded file

		if (name != null && description != null && image != null) {
			name = name.trim();
			description = description.trim();
			Chef newChef = new Chef(name, description, image);
			try {
				ChefDAO.insertChef(newChef);
				response.sendRedirect("AdminChefController?action=list");
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
		}
	}

	private void updateChef(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String image = saveUploadedFile(request); // Save the uploaded file

		if (image == null || image.isEmpty()) {
			image = request.getParameter("existingImage"); // Use existing image if no new file is uploaded
		}

		Chef Chef = new Chef(id, name, description, image);
		ChefDAO.updateChef(Chef);
		response.sendRedirect("AdminChefController?action=list");
	}

	private void deleteChef(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ChefDAO.deleteChef(id);
		response.sendRedirect("AdminChefController?action=list");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "insert":
				insertChef(request, response);
				break;
			case "update":
				updateChef(request, response);
				break;
			case "delete":
				deleteChef(request, response);
				break;
			default:
				response.sendRedirect("AdminChefController?action=list");
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
