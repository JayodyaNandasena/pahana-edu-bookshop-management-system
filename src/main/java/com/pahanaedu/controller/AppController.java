package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.log.SystemLogHandler;

import com.pahanaedu.model.Item;
import com.pahanaedu.service.ItemService;

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
			targetPage = "/WEB-INF/views/customers.jsp";
			request.setAttribute("activePage", "customers");
			break;
		case "/dashboard":
			targetPage = "/WEB-INF/views/dashboard.jsp";
			request.setAttribute("activePage", "dashboard");
			break;
		case "/inventory":
			try {
				List<Item> items = ItemService.getInstance().all();
				request.setAttribute("items", items);
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("error", "Unable to load inventory data.");
			}

			request.setAttribute("activePage", "inventory");
			targetPage = "/WEB-INF/views/inventory.jsp";
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
}