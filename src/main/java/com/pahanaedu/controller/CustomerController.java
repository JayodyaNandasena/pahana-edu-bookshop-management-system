package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.CustomersService;
import com.pahanaedu.util.Validator;

@WebServlet("/customer")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomersService customersService;

	public CustomerController() {
		super();
	}

	public void init() throws ServletException {
		customersService = CustomersService.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/customers");
	}

	// TODO: fix error redirection
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");

		// Determine the source page to redirect back to on error
		String sourcePage = getSourcePage(request);

		try {
			handleCustomerCreate(firstName, lastName, phone, email, address, request, response, sourcePage);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occurred.");
			request.getRequestDispatcher(sourcePage).forward(request, response);
		}
	}

	private void handleCustomerCreate(String firstName, String lastName, String phone, String email, String address,
			HttpServletRequest request, HttpServletResponse response, String sourcePage)
			throws IOException, ServletException {
		HashMap<String, String> errors = new HashMap<>();

		// Validate inputs
		if (!Validator.isValidString(firstName, 1, 100)) {
			errors.put("firstNameError", "First name must be between 1 and 100 characters.");
		}
		if (!Validator.isValidString(lastName, 1, 100)) {
			errors.put("lastNameError", "Last name must be between 1 and 100 characters.");
		}
		if (!Validator.isValidPhoneNumber(phone)) {
			errors.put("phoneError", "Phone number must be a valid Sri Lankan mobile number (e.g., 0771234567).");
		}
		if (!Validator.isValidEmail(email)) {
			errors.put("emailError", "Please enter a valid email address (e.g., name@example.com).");
		}
		if (!Validator.isValidString(address, 1, 250)) {
			errors.put("addressError", "Address must be between 1 and 250 characters.");
		}

		// If there are validation errors, send them back to the source form
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			// Preserve form data
			preserveFormData(request, firstName, lastName, phone, email, address);
//			request.getRequestDispatcher(sourcePage).forward(request, response);
			response.sendRedirect(request.getContextPath() + sourcePage);
			return;
		}

		try {
			PersistResult result = customersService.persist(firstName, lastName, phone, email, address);
			System.out.println("result : "+ result);
			switch (result) {
			case SUCCESS:
				response.sendRedirect(request.getContextPath() + sourcePage);
				return;
			case EMAIL_EXISTS:
				errors.put("emailError", "Email already exists. Please search for the customer or use another email.");
				break;
			case PHONE_EXISTS:
				errors.put("phoneError",
						"Mobile number already exists. Please search for the customer or use another mobile number.");
				break;
			case OTHER_ERROR:
				errors.put("generalError", "Failed to create customer. Please try again.");
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + result);
			}

			// Handling database constraint errors
			request.setAttribute("errors", errors);
			preserveFormData(request, firstName, lastName, phone, email, address);
			request.setAttribute("openCustomerDialog", true);
			request.getRequestDispatcher(sourcePage).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			errors.put("generalError", "An error occurred while creating the customer. Please try again.");
			request.setAttribute("errors", errors);
			preserveFormData(request, firstName, lastName, phone, email, address);
			request.getRequestDispatcher(sourcePage).forward(request, response);
		}
	}

	// Determines the source page to redirect back to on error
	private String getSourcePage(HttpServletRequest request) {
	    String sourcePage = request.getParameter("active_page");

	    if (sourcePage != null && !sourcePage.isBlank()) {
	        if (!sourcePage.startsWith("/")) {
	            sourcePage = "/" + sourcePage;
	        }
	        return sourcePage;
	    }

	    // Default to /customers
	    return "/customers";
	}

	// Preserves form data
	private void preserveFormData(HttpServletRequest request, String firstName, String lastName, String phone,
			String email, String address) {
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		request.setAttribute("phone", phone);
		request.setAttribute("email", email);
		request.setAttribute("address", address);
	}
}
