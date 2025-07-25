<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/dashboard.css">

<%@ include file="/pages/common/header.jsp"%>
<%@ include file="/pages/common/sidebar.jsp"%>

<div class="ml-64 flex-1 p-8">
	<div class="grid grid-cols-1 md:grid-cols-2 gap-6">

		<!-- Welcome, Card -->
		<div class="col-span-1 md:col-span-2">
			<h1 class="text-3xl font-bold mb-2 pb-3">Welcome Back to Pahana
				Edu</h1>
			<div
				class="card main-card mt-4 p-5 rounded-xl bg-white shadow-sm border border-gray-200">
				<h2 class="text-2xl font-semibold text-gray-800 mb-2">Hello
					John!</h2>
				<p class="text-gray-600 mb-6">Start with the User Guide or
					create a bill below.</p>
				<a href="#"
					class="btn-primary px-8 py-3 rounded-lg font-medium transition-colors bg-blue-600 text-white hover:bg-blue-700">
					Create Bill </a>
			</div>
		</div>

		<!-- Manage Customers Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow cursor-pointer">
			<div class="flex items-start space-x-4">
				<div class="w-12 h-12 rounded-lg flex items-center justify-center">
					<i class="fa-solid fa-users text-blue-600 text-xl"></i>
				</div>
				<div>
					<h3 class="text-lg font-semibold text-gray-800 mb-2">Manage
						Customers</h3>
					<p class="text-gray-600">Add new customer and search for a
						customer</p>
				</div>
			</div>
		</div>

		<!-- Search Inventory Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow cursor-pointer">
			<div class="flex items-start space-x-4">
				<div class="w-12 h-12 rounded-lg flex items-center justify-center">
					<i class="fa-solid fa-magnifying-glass text-blue-600 text-xl"></i>
				</div>
				<div>
					<h3 class="text-lg font-semibold text-gray-800 mb-2">Search
						Inventory</h3>
					<p class="text-gray-400">Search inventory</p>
				</div>
			</div>
		</div>

		<!-- Create New Bill Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow cursor-pointer">
			<div class="flex items-start space-x-4">
				<div class="w-12 h-12 rounded-lg flex items-center justify-center">
					<i class="fa-solid fa-file-invoice-dollar text-blue-600 text-xl"></i>
				</div>
				<div>
					<h3 class="text-lg font-semibold text-gray-800 mb-2">Create
						New Bill</h3>
					<p class="text-gray-600">Create a new bill</p>
				</div>
			</div>
		</div>

		<!-- User Guide Card -->
		<div
			class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-shadow cursor-pointer">
			<div class="flex items-start space-x-4">
				<div class="w-12 h-12 rounded-lg flex items-center justify-center">
					<i class="fa-solid fa-circle-question text-blue-600 text-xl"></i>
				</div>
				<div>
					<h3 class="text-lg font-semibold text-gray-800 mb-2">User
						Guide</h3>
					<p class="text-gray-400">Refer the user guide on any problem</p>
				</div>
			</div>
		</div>

	</div>
</div>

<%@ include file="/pages/common/footer.jsp"%>