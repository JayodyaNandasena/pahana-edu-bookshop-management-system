package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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

import com.pahanaedu.model.Category;
import com.pahanaedu.model.Item;
import com.pahanaedu.service.CategoryService;
import com.pahanaedu.service.ItemService;

@WebServlet(urlPatterns = { "/", "/bill", "/customers", "/dashboard", "/inventory", "/user-guide" })
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
			handleInventoryPage(request);
			request.setAttribute("activePage", "inventory");
			targetPage = "/WEB-INF/views/inventory.jsp";
			break;
		case "/user-guide":
			targetPage = "/WEB-INF/views/user-guide.jsp";
			request.setAttribute("activePage", "user-guide");
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

	private void handleInventoryPage(HttpServletRequest request) {
		try {
			List<Category> categories = CategoryService.getInstance().all();
			List<Item> items;

			String categoryIdParam = request.getParameter("category");
			String searchParam = request.getParameter("q");

			Integer selectedCategoryId = null;

			if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
				try {
					int categoryId = Integer.parseInt(categoryIdParam);

					// Check if this ID exists in the category list
					boolean categoryExists = categories.stream().anyMatch(c -> c.getId() == categoryId);

					if (categoryExists) {
						selectedCategoryId = categoryId;
						items = ItemService.getInstance().byCategory(categoryId);
					} else {
						// Invalid category
						items = ItemService.getInstance().all();
					}

				} catch (NumberFormatException e) {
					// Invalid input
					items = ItemService.getInstance().all();
				}
			} else if (searchParam != null && !searchParam.isEmpty()) {
				if ("deleted".equals(searchParam)) {
					items = ItemService.getInstance().getDeleted();
				} else {
					items = ItemService.getInstance().byIdOrName(searchParam);
				}

				selectedCategoryId = -1;
			} else {
				// No category selected
				items = ItemService.getInstance().all();
			}

			request.setAttribute("items", items);
			request.setAttribute("categories", categories);
			request.setAttribute("selectedCategoryId", selectedCategoryId);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Unable to load items.");
		}
	}
}