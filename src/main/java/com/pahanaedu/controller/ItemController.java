package com.pahanaedu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.ItemService;
import com.pahanaedu.util.Validator;

@WebServlet("/item")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemService itemService;

	public ItemController() {
		super();
	}

	public void init() throws ServletException {
		itemService = ItemService.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("delete".equals(action)) {
			try {
				int itemId = Integer.parseInt(request.getParameter("itemId"));
				boolean success = itemService.delete(itemId);

				if (success) {
					response.sendRedirect("inventory?deleted=true");
				} else {
					response.sendRedirect("inventory?deleted=false");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.sendRedirect("inventory?error=invalid_id");
			}
			return;
		}

		String name = request.getParameter("name");
		int category = Integer.parseInt(request.getParameter("category"));
		double price = Double.parseDouble(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			handleItemCreate(name, category, price, quantity, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Return JSON error response for unexpected exception
			writeJsonError(response, "An unexpected error occurred.");
		}

	}

	private void handleItemCreate(String name, int category, double price, int quantity, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// Validate input fields and collect errors
		HashMap<String, String> errors = validateInputs(name, category, price, quantity);

		// If validation errors exist, return them as JSON response
		if (!errors.isEmpty()) {
			writeJsonErrors(response, errors);
			return;
		}
		
		try {
			// Attempt to persist customer data and handle result accordingly
			PersistResult result = itemService.persist(name, category, price, quantity);

			switch (result) {
			case SUCCESS:
				// Success - send JSON success message
				writeJsonSuccess(response, "Item created successfully.");
				return;

			case ITEM_NAME_CATEGORY_EXISTS:
				errors.put("categoryNameError", "An item with the same name already exists in the selected category. Please use a different name or choose another category.");
				break;

			case OTHER_ERROR:
				errors.put("generalError", "Failed to create item. Please try again.");
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
			writeJsonError(response, "An error occurred while creating the item. Please try again.");
		}
		

	}

	private HashMap<String, String> validateInputs(String name, int category, double price, int quantity) {
		HashMap<String, String> errors = new HashMap<>();

		if (!Validator.isValidString(name, 1, 150)) {
			errors.put("nameError", "Item name must be between 1 and 150 characters.");
		}
		
		if (!Validator.isValidDigit(category, 1, 5)) {
			errors.put("categoryError", "Please select a valid category.");
		}
		
		if (!Validator.isValidPrice(price, 0.0, 100000.00)) {
			errors.put("priceError", "Unit price must be greater than 0 and up to 100,000 with at most two decimal places.");
		}
		
		if (!Validator.isValidDigit(quantity, 1, 10000)) {
			errors.put("quantityError", "Quantity must be between 1 and 10000.");
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
