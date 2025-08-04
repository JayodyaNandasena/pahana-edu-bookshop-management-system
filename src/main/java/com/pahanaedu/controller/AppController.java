package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.model.Customer;
import com.pahanaedu.service.CustomerService;
import com.pahanaedu.util.Validator;

@WebServlet(urlPatterns = { "/", "/bill", "/customers", "/dashboard", "/inventory" })
public class AppController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getServletPath();

		// Bypass static resources
		if (path.startsWith("/assets/")) {
			getServletContext().getNamedDispatcher("default").forward(request, response);
			return;
		}

		String targetPage;

		switch (path) {
		case "/":
			targetPage = "/index.jsp";
			break;
		case "/bill":
			targetPage = "/WEB-INF/views/bill.jsp";
			request.setAttribute("activePage", "bill");
			break;
		case "/customers":
			handleCustomersPage(request);
			targetPage = "/WEB-INF/views/customers.jsp";
			request.setAttribute("activePage", "customers");
			break;
		case "/dashboard":
			targetPage = "/WEB-INF/views/dashboard.jsp";
			request.setAttribute("activePage", "dashboard");
			break;
		case "/inventory":
			targetPage = "/WEB-INF/views/inventory.jsp";
			request.setAttribute("activePage", "inventory");
			break;
		default:
			targetPage = "/WEB-INF/views/404.jsp";
			request.setAttribute("pageTitle", "Page Not Found");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
		dispatcher.forward(request, response);
	}

	private void handleCustomersPage(HttpServletRequest request) {
		HttpSession session = request.getSession();

		// Move errors from session to request
		Object errorsObj = session.getAttribute("errors");
		if (errorsObj != null) {
			request.setAttribute("errors", errorsObj);
			session.removeAttribute("errors");
		}

		// Move preserved form data to request
		Object formData = session.getAttribute("formData");
		if (formData != null) {
			request.setAttribute("formData", formData);
			session.removeAttribute("formData");
		}

		// Always try to fetch customer from DB if `q` exists
		HashMap<String, String> errors = new HashMap<>();
		try {
			String searchParam = request.getParameter("q");
			if (searchParam != null && !searchParam.isEmpty()) {
				if (!Validator.isValidCustomerIdOrPhone(searchParam)) {
					errors.put("qError", "Input must be a valid customer ID or a phone number starting with 070–078.");
					request.setAttribute("errors", errors);
					return;
				}

				Customer customer = CustomerService.getInstance().byIdOrPhone(searchParam);
				request.setAttribute("customer", customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Unable to search customer.");
		}
	}
}