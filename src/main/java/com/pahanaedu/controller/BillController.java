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

import com.pahanaedu.model.Bill;
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
		if (request.getParameter("id") != null) {
			String id = request.getParameter("id");
			handleSearchById(id, request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/bills");
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

	private void handleSearchById(String id, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// parse ID
			int billId = Integer.parseInt(id);

			// fetch bill
			Bill bill = billService.byId(billId);

			// if bill not found
			if (bill == null) {
				throw new Exception("Bill not found");
			}

			request.setAttribute("bill", bill);
			request.setAttribute("activePage", "customers");
			request.getRequestDispatcher("WEB-INF/views/view-bill.jsp").forward(request, response);
		} catch (Exception e) {
			// set 404 status
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			// forward to 404 page
			request.setAttribute("pageTitle", "Page Not Found");
			request.getRequestDispatcher("/error/404.jsp").forward(request, response);
		}
	}

	private void handleBillCreate(int customerId, JSONArray items, String date, String time, double total,
			int cashierId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, String> errors = new HashMap<String, String>();

		try {
			// Attempt to persist customer data and handle result accordingly
			Bill bill = billService.persist(customerId, items, date, time, total, cashierId);

			if (bill != null) {
				writeJsonSuccess(response, "Bill created successfully.", bill.getCode());
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
	private void writeJsonSuccess(HttpServletResponse response, String message, String code) throws IOException {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", message);
		jsonResponse.put("result", code);

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
