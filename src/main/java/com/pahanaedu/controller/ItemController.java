package com.pahanaedu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pahanaedu.service.ItemService;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

	    if ("delete".equals(action)) {
	        try {
	            int itemId = Integer.parseInt(request.getParameter("itemId"));
	            boolean success = ItemService.getInstance().delete(itemId);

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

	}

}
