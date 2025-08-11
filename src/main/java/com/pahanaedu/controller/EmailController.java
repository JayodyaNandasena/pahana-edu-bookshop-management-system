package com.pahanaedu.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.pahanaedu.service.EmailService;

@WebServlet("/send-email")
public class EmailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmailService emailService;

	@Override
	public void init() throws ServletException {
		super.init();
		emailService = new EmailService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));

			String to = json.getString("to");
			String subject = json.getString("subject");
			String date = json.getString("date");
			String time = json.getString("time");
			String imageBase64 = json.optString("imageBase64", null);

			// Validation
			if (to == null || to.trim().isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"status\":\"error\",\"message\":\"Recipient email is required\"}");
				return;
			}

			if (!emailService.isValidEmail(to)) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid email format\"}");
				return;
			}

			if (subject == null || subject.trim().isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"status\":\"error\",\"message\":\"Subject is required\"}");
				return;
			}

			if (imageBase64 == null && imageBase64.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"status\":\"error\",\"message\":\"Bill preview is required\"}");
				return;
			}

			// Send email
			boolean emailSent;
			emailSent = emailService.sendEmailWithInlineImage(to, subject, date, time, imageBase64);

			if (emailSent) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"status\":\"success\",\"message\":\"Email sent successfully\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to send email\"}");
			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter()
					.write("{\"status\":\"error\",\"message\":\"Internal server error: " + e.getMessage() + "\"}");
		} finally {
			response.getWriter().close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().write("<h1>Email Service</h1><p>Use POST method to send emails</p>");
	}
}
