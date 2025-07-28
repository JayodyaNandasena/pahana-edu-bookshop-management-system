package com.pahanaedu.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahanaedu.util.Validator;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		handleLogin(username, password, request, response);
		
//		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
		return;
	}

	private void handleLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
        
        
		
	}

}
