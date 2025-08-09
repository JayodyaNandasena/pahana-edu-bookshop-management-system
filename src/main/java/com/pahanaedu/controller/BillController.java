package com.pahanaedu.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pahanaedu.model.enums.PersistResult;
import com.pahanaedu.service.BillService;

@WebServlet("/bills")
public class BillController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BillService billService;

	public BillController() {
		super();
	}

	public void init() throws ServletException {
		billService = BillService.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject json = new JSONObject(sb.toString());

			int customerId = json.getInt("customerId");
			JSONArray items = json.getJSONArray("items");
			String date = json.getString("date");
			String time = json.getString("time");
			double total = json.getDouble("total");
			int cashierId = json.getInt("cashierId");

			handleBillCreate(customerId, items, date, time, total, cashierId, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Return JSON error response for unexpected exception
			writeJsonError(response, "An unexpected error occurred.");
		}
	}

	private void handleBillCreate(int customerId, JSONArray items, String date, String time, double total,
			int cashierId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, String> errors = new HashMap<String, String>();
		
		try {
			// Attempt to persist customer data and handle result accordingly
			PersistResult result = billService.persist(customerId, items, date, time, total, cashierId);

			switch (result) {
			case SUCCESS:
				// Success - send JSON success message
				writeJsonSuccess(response, "Bill created successfully.");
				return;

			case OTHER_ERROR:
				errors.put("generalError", "Failed to create bill. Please try again.");
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
			writeJsonError(response, "An error occurred while creating the bill. Please try again.");
		}
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
