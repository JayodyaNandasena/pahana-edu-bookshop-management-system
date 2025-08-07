package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.pahanaedu.model.Customer;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.CustomerService;
import com.pahanaedu.util.Validator;

@WebServlet("/customer")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerService customerService;

	public CustomerController() {
		super();
	}

	public void init() throws ServletException {
		customerService = CustomerService.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/customers");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("searchByMobile".equals(action)) {
			String mobile = request.getParameter("mobile");
			request.setAttribute("mobile", mobile);
			handleSearchByMobile(mobile, request, response);
			return;
		}

		if ("update".equals(action)) {
			handleUpdate(request, response);
			return;
		}

		if ("deactivate".equals(action)) {
			handleDeactivate(request, response);
			return;
		}

		if ("activate".equals(action)) {
			handleActivate(request, response);
			return;
		}

		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			handleCustomerCreate(firstName, lastName, phone, email, address, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Return JSON error response for unexpected exception
			writeJsonError(response, "An unexpected error occurred.");
		}
	}

	private void handleDeactivate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");

		customerService.deactivate(id);

		response.sendRedirect(request.getContextPath() + "/customers?q=" + id);

		return;
	}

	private void handleActivate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");

		customerService.activate(id);

		response.sendRedirect(request.getContextPath() + "/customers?q=" + id);

		return;
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");

		HashMap<String, String> errors = validateInputs(firstName, lastName, phone, email, address);

		// Preserve customer data in a map
		Map<String, String> customerFormData = new HashMap<>();
		customerFormData.put("first_name", firstName);
		customerFormData.put("last_name", lastName);
		customerFormData.put("phone", phone);
		customerFormData.put("email", email);
		customerFormData.put("address", address);

		HttpSession session = request.getSession();

		// If validation errors exist, redirect back
		if (!errors.isEmpty()) {
			session.setAttribute("errors", errors);
			session.setAttribute("formData", customerFormData);
			response.sendRedirect(request.getContextPath() + "/customers?q=" + id);
			return;
		}

		// Attempt update
		PersistResult result = customerService.update(id, firstName, lastName, phone, email, address);

		switch (result) {
		case SUCCESS:
			response.sendRedirect(request.getContextPath() + "/customers?q=" + id);
			return;
		case EMAIL_EXISTS:
			errors.put("emailError", "Email already exists.");
			break;
		case PHONE_EXISTS:
			errors.put("phoneError", "Mobile number already exists.");
			break;
		case OTHER_ERROR:
			errors.put("generalError", "Failed to update customer. Please try again.");
			break;
		default:
			throw new IllegalArgumentException("Unexpected result: " + result);
		}

		// On update error, set errors and form data in session and redirect
		session.setAttribute("errors", errors);
		session.setAttribute("customer", customerFormData);
		response.sendRedirect(request.getContextPath() + "/customers?q=" + id);
	}

	private void handleSearchByMobile(String mobile, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Customer customer = customerService.getByMobile(mobile);

			if (customer == null) {
				request.setAttribute("errorMessage", "Customer not found with the provided mobile number.");
			} else {
				request.setAttribute("customer", customer);
			}
		} catch (SQLException e) {
			request.setAttribute("errorMessage", "An error occurred while searching: " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/views/bill.jsp").include(request, response);
	}

	private void handleCustomerCreate(String firstName, String lastName, String phone, String email, String address,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Validate input fields and collect errors
		HashMap<String, String> errors = validateInputs(firstName, lastName, phone, email, address);

		// If validation errors exist, return them as JSON response
		if (!errors.isEmpty()) {
		    writeJsonErrors(response, errors);
		    return;
		}

		try {
			// Attempt to persist customer data and handle result accordingly
			PersistResult result = customerService.persist(firstName, lastName, phone, email, address);

			switch (result) {
			case SUCCESS:
				// Success - send JSON success message
				writeJsonSuccess(response, "Customer created successfully.");
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
				// Handle unexpected result
				writeJsonError(response, "Unexpected error occurred.");
				return;
			}

			// Return errors if persistence fails due to known constraints
			writeJsonErrors(response, errors);	
			
		} catch (Exception e) {
			e.printStackTrace();
			// Return a generic error message on unexpected exceptions
			writeJsonError(response, "An error occurred while creating the customer. Please try again.");
		}
	}

	private HashMap<String, String> validateInputs(String firstName, String lastName, String phone, String email,
			String address) {
		HashMap<String, String> errors = new HashMap<>();

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

		return errors;
	}

	// helper methods for success and error JSON responses using JSONObject
	private void writeJsonSuccess(HttpServletResponse response, String message) throws IOException {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", message);

		response.getWriter().write(jsonResponse.toString());
	}

	private void writeJsonErrors(HttpServletResponse response, Map<String, String> errors) throws IOException {
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("success", false);
	    jsonResponse.put("errors", new JSONObject(errors)); // converts Map to JSON object automatically

	    response.getWriter().write(jsonResponse.toString());
	}
	
	private void writeJsonError(HttpServletResponse response, String message) throws IOException {
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("success", false);
	    jsonResponse.put("message", message);

	    response.getWriter().write(jsonResponse.toString());
	}

}
