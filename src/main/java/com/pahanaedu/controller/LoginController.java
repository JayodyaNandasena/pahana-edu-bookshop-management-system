package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.model.User;
import com.pahanaedu.service.LoginService;
import com.pahanaedu.util.Toast;
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
			
			Toast.setToastCookie(response, "error", "An unexpected error occurred");		    
		    response.sendRedirect(request.getContextPath() + "/");
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
	    
	    User user = loginService.login(username, password);
	    
	    if(user != null) {
	    	// Authentication success
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            Toast.setToastCookie(response, "success", "Login successful!");
            
	    	response.sendRedirect(request.getContextPath() + "/dashboard");
	    	return;
	    }
	    
	    errors.put("loginError", "Invalid username or password");
	    request.setAttribute("errors", errors);
	    
	    Toast.setToastCookie(response, "error", "Invalid username or password");
	    
	    response.sendRedirect(request.getContextPath() + "/");
        return;
	}
}
