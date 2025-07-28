package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahanaedu.service.LoginService;
import com.pahanaedu.util.Validator;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService;
       
    public LoginController() {
        super();
    }
    
    public void init() throws ServletException {
        loginService = LoginService.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			handleLogin(username, password, request, response);
		} catch (ServletException | IOException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
	        return;
		}
		
		return;
	}

	private void handleLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		HashMap<String, String> errors = new HashMap<>();

	    // Validate username
	    if (!Validator.isValidString(username, 1, 100)) {
	        errors.put("usernameError", "Username must be between 1 and 100 characters.");
	    }

	    // Validate password
	    if (!Validator.isValidString(password, 1, 100)) {
	        errors.put("passwordError", "Password must be between 1 and 100 characters.");
	    }

	    // If there are errors, send them back to the form
	    if (!errors.isEmpty()) {
	        request.setAttribute("errors", errors);
	        request.getRequestDispatcher("/index.jsp").forward(request, response);
	        return;
	    }
	    
	    if(loginService.login(username, password) != null) {
	    	response.sendRedirect(request.getContextPath() + "/dashboard");
	    	return;
	    }
	    
	    errors.put("loginError", "Invalid username or password");
	    request.setAttribute("errors", errors);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        return;
	}
}
