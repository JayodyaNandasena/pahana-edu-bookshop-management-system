<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/bill.css">

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

<div class="ml-64 p-8">
	<div class="max-w-7xl mx-auto">
		<!-- Header -->
		<div class="flex items-center justify-between mb-6">
			<h1 class="text-3xl font-bold mb-2">Create New Bill</h1>
		</div>

		<div class="grid grid-cols-1 md:grid-cols-7 gap-4">
			<!-- Left Column - Form -->
			<div class="md:col-span-4 space-y-6">
				<!-- Customer Details Card -->
				<div
					class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden">
					<div class="mx-6 py-4 flex border-b-2 border-gray-200">
						<h2 class="flex-1 text-lg font-semibold flex items-center">
							<i class="fa-solid fa-user mr-3"></i> Customer Details
						</h2>

						<button id="new-customer-btn"
							class="btn-primary px-6 py-2 rounded-xl font-medium transition-all flex items-center justify-center"
							data-dialog-open="new-customer-modal">
							<i class="fa-solid fa-user-plus mr-1"></i> <span>New</span>
						</button>
					</div>
					<div class="p-6 pt-3 pb-6">
						<form method="get" action="customer" id="search-customer-form">
							<div class="mb-6">
								<label for="customerSearch"
									class="block text-sm font-medium text-gray-700 mb-2">Search
									Customer</label>
								<div class="relative">
									<input type="text" name="mobile"
										placeholder="Enter mobile number (e.g., 0700000000)"
										class="px-4 py-3" id="customerSearch">
									<button type="submit"
										class="absolute right-3 top-1/2 transform -translate-y-1/2 text-blue-500 hover:text-blue-700 p-2">
										<i class="fas fa-search"></i>
									</button>
								</div>
								<p class="text-red-500 text-sm mt-1 ml-1" id="mobileError"></p>
							</div>

							<div class="grid grid-cols-2 gap-4 mb-6">
								<div>
									<label class="block text-sm font-medium text-gray-700 mb-2">Customer
										ID</label> <input type="text" id="customerId" class="px-4 py-3"
										readonly disabled />
								</div>
								<div>
									<label class="block text-sm font-medium text-gray-700 mb-2">Customer
										Name</label> <input type="text" id="customerName" class="px-4 py-3"
										readonly disabled>
								</div>
							</div>
						</form>

						<div class="flex">
							<button id="btn-confirm-customer"
								class="btn-confirm flex-1 py-3 rounded-xl font-medium transition-all flex items-center justify-center space-x-2"
								disabled>
								<i class="fas fa-check"></i> <span>Confirm Customer</span>
							</button>
						</div>
					</div>
				</div>

				<!-- Items Management Card -->
				<div
					class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden fade-in">
					<div class="px-6 py-4">
						<h2
							class="text-lg font-semibold flex items-center py-3 border-b-2 border-gray-200">
							<i class="fas fa-shopping-cart mr-3"></i> Bill Items
						</h2>
					</div>
					<div class="px-6 pt-3 pb-6">
						<!-- Add Item Form -->
						<div
							class="grid grid-cols-1 md:grid-cols-5 gap-4 mb-6 p-6 bg-blue-50 rounded-xl border border-blue-200">
							<div class="md:col-span-2">
								<label class="block text-sm font-semibold text-gray-700 mb-3">Item
									ID</label> <input id="item-id" class="px-4 py-3 border-gray-400"
									placeholder="eg: ITM00001" disabled>
							</div>
							<div class="md:col-span-2">
								<label class="block text-sm font-semibold text-gray-700 mb-3">Quantity</label>
								<input type="number" id="item-quantity" placeholder="0" min="1"
									class="px-4 py-3 border-gray-400" disabled>
							</div>
							<div class="flex items-end">
								<button id="btn-add-bill-item"
									class="btn-primary w-full py-3 rounded-xl font-semibold transition-all duration-300 pulse-ring"
									disabled>
									<!-- <i class="fas fa-plus mr-2"></i> -->
									Add
								</button>
							</div>
						</div>

						<!-- Items List -->
						<div
							class="overflow-hidden rounded-xl border-2 border-gray-200 shadow-sm">
							<div
								class="bg-gradient-to-r from-gray-50 to-gray-100 px-6 py-4 border-b-2 border-gray-200">
								<div class="grid grid-cols-6 gap-4 text-gray-700">
									<div class="text-center col-span-2">Item</div>
									<div class="text-center">Quantity</div>
									<div class="text-center">Unit Price</div>
									<div class="text-center">Total</div>
									<div class="text-center"></div>
								</div>
							</div>
							<div id="itemsList" class="divide-y divide-gray-200">
								<!-- Items will be added here dynamically -->
							</div>
							<div id="emptyState" class="p-8 text-center text-gray-500">
								<i class="fas fa-shopping-cart text-4xl mb-4 text-gray-300"></i>
								<p class="text-lg">No items added yet</p>
								<p class="text-sm">Add items using the form above</p>
							</div>
						</div>

						<!-- Action Buttons -->
						<div
							class="flex justify-between mt-6 pt-6 border-t-2 border-gray-200">
							<button id="clearAllBtn"
								class="px-6 py-3 bg-red-100 text-red-700 rounded-xl font-semibold hover:bg-red-200 transition-all duration-300 flex items-center space-x-2"
								disabled>
								<i class="fas fa-trash"></i> <span>Clear All Items</span>
							</button>
							<button id="generateBillBtn"
								class="btn-confirm px-8 py-3 rounded-xl font-semibold transition-all duration-300 flex items-center space-x-2"
								disabled>
								<i class="fas fa-file-invoice-dollar"></i> <span>Generate
									Bill</span>
							</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Right Column - Preview -->
			<div class="md:col-span-3 min-h-screen">
				<div
					class="rounded-2xl shadow-xl overflow-hidden sticky top-8 bg-white ">
					<div
						class="justify-between items-center mx-6 py-4 flex border-b-2 border-gray-200">
						<h2 class="text-lg font-semibold flex items-center">
							<i class="fas fa-file-invoice mr-3"></i> Bill Preview
						</h2>
						<button
							class="btn-primary px-4 py-2 rounded-lg text-sm flex items-center space-x-2"
							disabled>
							<i class="fas fa-download"></i> <span>PDF</span>
						</button>
					</div>
					<div class="p-6">
						<!-- Invoice Header -->
						<div class="text-center mb-6 pb-4 border-b-2 border-gray-200">
							<div class="flex items-center justify-center space-x-3 mb-3">
								<img src="/bookshopManagement/assets/images/logo.png"
									alt="Pahana Edu" class="h-10 w-auto">
							</div>
							<p class="text-lg font-semibold text-blue-600" id="invoiceNumber">Invoice
								#003</p>
						</div>

						<!-- Customer & Date Info -->
						<div class="grid grid-cols-2 gap-4 mb-6 text-sm">
							<div>
								<p class="text-gray-500 font-semibold">Bill To:</p>
								<p class="font-bold text-gray-800" id="previewCustomerName"></p>
								<p class="text-gray-600" id="previewCustomerEmail"></p>
							</div>
							<div>
								<p class="text-gray-500 font-semibold">Date & Time:</p>
								<p class="font-bold text-gray-800" id="previewDateTime"></p>
							</div>
						</div>

						<!-- Items Summary -->
						<div class="space-y-3 mb-6">
							<div
								class="flex justify-between text-sm font-semibold text-gray-700 border-b border-gray-200 pb-2">
								<span>Item</span> <span>Amount (Rs.)</span>
							</div>
							<div id="previewItems" class="space-y-2">
								<!-- Items will be populated here -->
							</div>
						</div>

						<!-- Total -->
						<div class="pt-4 border-t-2 border-gray-200">
							<div class="flex justify-between items-center">
								<span class="text-xl font-bold text-gray-800">Total
									Amount (Rs.)</span> <span class="text-2xl font-bold text-green-600"
									id="previewTotal">Rs. 0.00</span>
							</div>
						</div>

						<!-- Footer -->
						<div class="mt-8 pt-4 border-t border-gray-200 text-center">
							<p class="text-sm text-gray-600 font-medium">Thank you for
								your business!</p>
							<p class="text-xs text-gray-500 mt-1">Powered by Pahana Edu
								Billing System</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Add new customer dialog -->
	<%@ include file="/WEB-INF/views/common/new-customer-dialog.jsp"%>

</div>

<script type="text/javascript"
	src="/bookshopManagement/assets/js/bill.js"></script>
<script type="text/javascript"
	src="/bookshopManagement/assets/js/dialog-controller.js"></script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>