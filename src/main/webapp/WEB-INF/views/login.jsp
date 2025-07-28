<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>


<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/login.css">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Root wrapper -->
<div class="min-h-screen flex items-center justify-center">
	<div class="bg-white p-8 rounded-lg shadow-lg max-w-md w-full mx-4">
		<!-- Logo/Icon -->
		<div class="text-center mb-8">
			<div class="inline-flex items-center justify-center mb-4">
				<img src="assets/images/logo1.png" alt="logo" class="h-20" />
			</div>
			<h1 class="text-4xl font-semibold text-blue-600 mb-8">Login</h1>
		</div>

		<!-- Login Form -->
		<%
		Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
		%>

		<form action="Login" method="POST" class="space-y-6">

			<!-- Username Field -->
			<div>
			  <div class="relative">
			    <span
			      class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
			      <i class="fa-solid fa-user"></i>
			    </span>
			    <input
			      type="text"
			      placeholder="Username"
			      name="username"
			      class="w-full pl-12 pr-4 py-3 rounded-lg transition-colors placeholder-gray-400
			             border
			             <%= (errors != null && errors.get("usernameError") != null) ? "border-red-500" : "border-gray-300 focus:ring-blue-500 focus:border-blue-500" %>"
			    />
			  </div>
			
			  <% if (errors != null && errors.get("usernameError") != null) { %>
			    <p class="text-red-500 text-sm mt-1 ml-1"><%= errors.get("usernameError") %></p>
			  <% } %>
			</div>


			<!-- Password Field -->
			<div>
			  <div class="relative">
			    <span
			      class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none text-gray-400">
			      <i class="fa-solid fa-lock"></i>
			    </span>
			    <input
			      type="text"
			      placeholder="Password"
			      name="password"
			      class="w-full pl-12 pr-4 py-3 rounded-lg transition-colors placeholder-gray-400
			             border
			             <%= (errors != null && errors.get("passwordError") != null) ? "border-red-500" : "border-gray-300 focus:ring-blue-500 focus:border-blue-500" %>"
			    />
			  </div>
			
			  <% if (errors != null && errors.get("passwordError") != null) { %>
			    <p class="text-red-500 text-sm mt-1 ml-1"><%= errors.get("passwordError") %></p>
			  <% } %>
			</div>

			<!-- Submit Button -->
			<button type="submit"
				class="w-full bg-blue-600 text-white font-medium py-4 px-4 rounded-lg transition-colors hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
				Get Started</button>
		</form>

	</div>
</div>
