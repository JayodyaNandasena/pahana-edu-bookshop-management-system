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

import org.json.JSONObject;

import com.pahanaedu.model.Item;
import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.ItemService;
import com.pahanaedu.service.exception.InsufficientStockException;
import com.pahanaedu.service.exception.ItemNotFoundException;
import com.pahanaedu.util.Toast;
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
		if (request.getParameter("id") != null && request.getParameter("quantity") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));

			handleSearchById(id, quantity, request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/inventory");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("delete".equals(action)) {
			try {
				int itemId = Integer.parseInt(request.getParameter("itemId"));

				if (itemService.delete(itemId)) {
					Toast.setToastCookie(response, "success", "Item deleted successfully!");
				} else {
					Toast.setToastCookie(response, "error", "Error deleting item");
				}
				response.sendRedirect("inventory");
			} catch (NumberFormatException e) {
				e.printStackTrace();
				Toast.setToastCookie(response, "error", "Invalid item ID");
				response.sendRedirect("inventory");
			}
			return;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if ("update".equals(action)) {
			try {
				int itemId = Integer.parseInt(request.getParameter("u_id"));
				String name = request.getParameter("u_name");
				int category = Integer.parseInt(request.getParameter("u_category"));
				double price = Double.parseDouble(request.getParameter("u_price"));
				int quantity = Integer.parseInt(request.getParameter("u_quantity"));

				handleItemUpdate(itemId, name, category, price, quantity, request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				// Return JSON error response for unexpected exception
				writeJsonError(response, "An unexpected error occurred.");
			}
			return;
		}

		String name = request.getParameter("name");
		int category = Integer.parseInt(request.getParameter("category"));
		double price = Double.parseDouble(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		try {
			handleItemCreate(name, category, price, quantity, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Return JSON error response for unexpected exception
			writeJsonError(response, "An unexpected error occurred.");
		}

	}

	private void handleItemUpdate(int id, String name, int category, double price, int quantity,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Validate input fields and collect errors
		HashMap<String, String> errors = validateInputs(name, category, price, quantity);

		// If validation errors exist, return them as JSON response
		if (!errors.isEmpty()) {
			writeJsonErrors(response, errors);
			return;
		}

		try {
			// Attempt to persist customer data and handle result accordingly
			PersistResult result = itemService.update(id, name, category, price, quantity);

			switch (result) {
			case SUCCESS:
				// Success - send JSON success message
				writeJsonSuccess(response, "Item updated successfully.");
				return;

			case ITEM_NAME_CATEGORY_EXISTS:
				errors.put("categoryuNameError",
						"An item with the same name already exists in the selected category. Please use a different name or choose another category.");
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
			writeJsonError(response, "An error occurred while updating the item. Please try again.");
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
				errors.put("categoryNameError",
						"An item with the same name already exists in the selected category. Please use a different name or choose another category.");
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

	private void handleSearchById(int id, int quantity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			// Validate input
			HashMap<String, String> errors = new HashMap<>();

			if (!Validator.isValidDigit(id, 1, Integer.MAX_VALUE)) {
				errors.put("idError", "Item ID must be a positive integer.");
			}

			if (!Validator.isValidDigit(quantity, 1, 10000)) {
				errors.put("quantityError", "Quantity must be between 1 and 10000.");
			}

			if (!errors.isEmpty()) {
				writeJsonErrors(response, errors);
				return;
			}

			// Item search with quantity check
			Item item = itemService.byId(id, quantity);

			JSONObject jsonResponse = new JSONObject();
			JSONObject itemJson = new JSONObject();

			itemJson.put("id", item.getId());
			itemJson.put("name", item.getName());
			itemJson.put("quantity", quantity);
			itemJson.put("unitPrice", item.getUnitPrice());
			itemJson.put("total", quantity * item.getUnitPrice());

			jsonResponse.put("success", true);
			jsonResponse.put("item", itemJson);

			response.getWriter().write(jsonResponse.toString());

		} catch (ItemNotFoundException e) {
			writeJsonError(response, e.getMessage());
		} catch (InsufficientStockException e) {
			writeJsonError(response, e.getMessage());
		} catch (SQLException e) {
			writeJsonError(response, "Database error occurred while fetching item.");
		} catch (Exception e) {
			writeJsonError(response, "Unexpected error: " + e.getMessage());
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
			errors.put("priceError",
					"Unit price must be greater than 0 and up to 100,000 with at most two decimal places.");
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
