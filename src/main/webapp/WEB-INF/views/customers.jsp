<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/customers.css">

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

<div class="ml-64 p-8">
	<div class="max-w-7xl mx-auto">
		<!-- Header -->
		<div class="flex items-center justify-between">
			<h1 class="text-3xl font-bold mb-2">Manage Customers</h1>
			<button id="new-customer-btn"
				class="btn-primary px-6 py-2 rounded-xl font-medium transition-all flex items-center justify-center"
				data-dialog-open="new-customer-modal">
				<i class="fa-solid fa-user-plus mr-1"></i> <span>New</span>
			</button>
		</div>

		<!-- Search input-->
		<div
			class="sticky top-4 z-50 bg-white rounded-2xl shadow-xl border border-blue-300 overflow-hidden px-6 py-4 my-4">
			<label for="customerSearch" class="text-lg font-semibold mb-2 block">
				Enter customer ID or mobile number and click search </label>
			<div class="flex gap-3">
				<input type="text" placeholder="e.g., CUS0001 or 0700000000"
					class="px-4 py-2" id="customerSearch">
				<button
					class="bg-blue-300 text-black font-semibold px-6 rounded-lg hover:bg-blue-400">
					Search</button>
			</div>
		</div>

		<!-- Customer details -->
		<div
			class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden px-6 py-4 my-4">
			<div
				class="py-3 border-b-2 border-gray-200 flex items-center justify-between">
				<div class="flex items-center">
					<i class="fa-solid fa-user mr-3 blue-icon"></i>
					<h2 class="text-xl font-semibold">Customer Details</h2>
				</div>
				<div class="flex space-x-2">
					<span class="status-badge status-paid">Active</span>
				</div>
			</div>
			<div class="pt-4">
				<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-3">
					<!-- Customer ID -->
					<div>
						<label for="customer-id"
							class="block text-sm font-medium text-gray-700 mb-1">Customer
							ID</label> <input id="customer-id" type="text" value="CUS0002"
							class="px-4 py-2" readonly disabled />
					</div>

					<!-- Total Units Consumed -->
					<div>
						<label for="units"
							class="block text-sm font-medium text-gray-700 mb-1">Total
							Units Consumed</label> <input id="units" type="text" value="50"
							class="px-4 py-2" readonly disabled />
					</div>

					<!-- First Name -->
					<div>
						<label for="first-name"
							class="block text-sm font-medium text-gray-700 mb-1">First
							Name</label> <input id="first-name" type="text" class="px-4 py-2" />
					</div>

					<!-- Last Name -->
					<div>
						<label for="last-name"
							class="block text-sm font-medium text-gray-700 mb-1">Last
							Name</label> <input id="last-name" type="text" class="px-4 py-2" />
					</div>

					<!-- Mobile Number -->
					<div>
						<label for="mobile"
							class="block text-sm font-medium text-gray-700 mb-1">Mobile
							Number</label> <input id="mobile" type="text" class="px-4 py-2" />
					</div>

					<!-- Email -->
					<div>
						<label for="email"
							class="block text-sm font-medium text-gray-700 mb-1">Email</label>
						<input id="email" type="email" class="px-4 py-2" />
					</div>

					<!-- Home Address -->
					<div class="md:col-span-2">
						<label for="address"
							class="block text-sm font-medium text-gray-700 mb-1">Home
							Address</label>
						<textarea id="address" rows="3" class="px-4 py-2"></textarea>
					</div>
				</div>

				<!-- Action Buttons-->
				<div class="flex justify-between pt-6 border-t-2 border-gray-100">
					<div class="flex space-x-3">
						<button
							class="px-8 py-3 bg-red-50 hover:bg-red-100 text-red-600 rounded-xl font-semibold transition-all duration-300 flex items-center space-x-2">
							<i class="fas fa-undo"></i> <span>Reset</span>
						</button>
						<button
							class="px-8 py-3 bg-yellow-100 hover:bg-yellow-200 text-yellow-700 rounded-xl font-semibold transition-all duration-300 flex items-center space-x-2">
							<i class="fas fa-ban"></i> <span>Deactivate</span>
						</button>
					</div>
					<button
						class="bg-green-100 hover:bg-green-200 text-green-800 px-12 py-3 rounded-xl font-semibold transition-all duration-300 flex items-center space-x-2">
						<i class="fas fa-save"></i> <span>Save Changes</span>
					</button>
				</div>
			</div>
		</div>

		<!-- Invoice list -->
		<div
			class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden px-6 py-4 my-4 fade-in">
			<div class="py-3">
				<h2
					class="text-lg font-semibold flex items-center py-3 border-b-2 border-gray-200">
					<i class="fas fa-list-ul mr-3"></i> Invoice History
				</h2>
			</div>
			<div class="pt-3 pb-6">
				<div
					class="overflow-hidden rounded-xl border border-gray-200 shadow-md">
					<!-- Header -->
					<div
						class="bg-gradient-to-r from-gray-50 to-gray-100 px-3 py-4 border-b border-gray-200">
						<div
							class="grid grid-cols-9 text-sm font-semibold text-gray-600 uppercase tracking-wide">
							<div class="text-center col-span-2">ID</div>
							<div class="text-center col-span-2">Date</div>
							<div class="text-center col-span-2">Time</div>
							<div class="text-center col-span-2">Total (Rs.)</div>
							<div class="text-center"></div>
						</div>
					</div>

					<!-- Items List -->
					<div id="itemsList" class="divide-y divide-gray-100">
						<div
							class="grid grid-cols-9 px-3 py-4 text-gray-700 hover:bg-gray-50 transition-colors duration-150">
							<div class="text-center font-medium col-span-2">INV0001</div>
							<div class="text-center col-span-2">19/07/2025</div>
							<div class="text-center col-span-2">12:03 PM</div>
							<div class="text-center text-green-600 font-semibold col-span-2">2500.00</div>
							<div class="text-center">
								<i
									class="fa fa-angle-right text-gray-400 hover:text-gray-600 cursor-pointer"
									aria-hidden="true"></i>
							</div>
						</div>
						<div
							class="grid grid-cols-9 px-3 py-4 text-gray-700 hover:bg-gray-50 transition-colors duration-150">
							<div class="text-center font-medium col-span-2">INV0002</div>
							<div class="text-center col-span-2">20/07/2025</div>
							<div class="text-center col-span-2">03:45 PM</div>
							<div class="text-center text-green-600 font-semibold col-span-2">1800.00</div>
							<div class="text-center">
								<i
									class="fa fa-angle-right text-gray-400 hover:text-gray-600 cursor-pointer"
									aria-hidden="true"></i>
							</div>
						</div>
					</div>

					<!-- Empty State (Uncomment when needed) -->
					<!--
                    <div id="emptyState" class="p-8 text-center text-gray-500">
                      <i class="fas fa-shopping-cart text-4xl mb-4 text-gray-300"></i>
                      <p class="text-lg font-medium">No invoices added yet</p>
                      <p class="text-sm">Add invoices using Create Bill</p>
                    </div>
                    -->
				</div>
			</div>

			<div class="flex justify-between items-center mt-8 px-2">
				<div>
					<span class="text-sm text-gray-600">Showing 1-2 of 2
						invoices</span>
					<p class="text-xs text-gray-400 mt-1">Last updated: Today</p>
				</div>
				<div class="flex items-center space-x-3">
					<button
						class="px-4 py-2 bg-gray-100 rounded-xl text-gray-400 cursor-not-allowed">
						<i class="fas fa-chevron-left mr-1"></i> Previous
					</button>
					<div class="flex space-x-2">
						<button class="px-4 py-2 btn-primary rounded-xl font-semibold">1</button>
					</div>
					<button
						class="px-4 py-2 bg-gray-100 rounded-xl text-gray-400 cursor-not-allowed">
						Next <i class="fas fa-chevron-right ml-1"></i>
					</button>
				</div>
			</div>
		</div>

		<!--        <div class="grid grid-cols-7 md:grid-cols-7 gap-4">-->
		<!--            <div class="md:col-span-4 space-y-4">-->
		<!--                &lt;!&ndash; Items Management Card &ndash;&gt;-->
		<!--                <div class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden fade-in">-->
		<!--                    <div class="px-6 py-4">-->
		<!--                        <h2 class="text-lg font-semibold flex items-center py-3 border-b-2 border-gray-200">-->
		<!--                            <i class="fas fa-list-ul mr-3"></i>-->
		<!--                            Invoice List-->
		<!--                        </h2>-->
		<!--                    </div>-->
		<!--                    <div class="px-6 pt-3 pb-6">-->
		<!--                        <div class="overflow-hidden rounded-xl border border-gray-200 shadow-md">-->
		<!--                            &lt;!&ndash; Header &ndash;&gt;-->
		<!--                            <div class="bg-gradient-to-r from-gray-50 to-gray-100 px-3 py-4 border-b border-gray-200">-->
		<!--                                <div class="grid grid-cols-9 text-sm font-semibold text-gray-600 uppercase tracking-wide">-->
		<!--                                    <div class="text-center col-span-2">ID</div>-->
		<!--                                    <div class="text-center col-span-2">Date</div>-->
		<!--                                    <div class="text-center col-span-2">Time</div>-->
		<!--                                    <div class="text-center col-span-2">Total (Rs.)</div>-->
		<!--                                    <div class="text-center"></div>-->
		<!--                                </div>-->
		<!--                            </div>-->

		<!--                            &lt;!&ndash; Items List &ndash;&gt;-->
		<!--                            <div id="itemsList" class="divide-y divide-gray-100">-->
		<!--                                <div class="grid grid-cols-9 px-3 py-4 text-gray-700 hover:bg-gray-50 transition-colors duration-150">-->
		<!--                                    <div class="text-center font-medium col-span-2">INV0001</div>-->
		<!--                                    <div class="text-center col-span-2">19/07/2025</div>-->
		<!--                                    <div class="text-center col-span-2">12:03 PM</div>-->
		<!--                                    <div class="text-center text-green-600 font-semibold col-span-2">2500.00</div>-->
		<!--                                    <div class="text-center">-->
		<!--                                        <i class="fa fa-angle-right text-gray-400 hover:text-gray-600 cursor-pointer"-->
		<!--                                           aria-hidden="true"></i>-->
		<!--                                    </div>-->
		<!--                                </div>-->
		<!--                                <div class="grid grid-cols-9 px-3 py-4 text-gray-700 hover:bg-gray-50 transition-colors duration-150">-->
		<!--                                    <div class="text-center font-medium col-span-2">INV0002</div>-->
		<!--                                    <div class="text-center col-span-2">20/07/2025</div>-->
		<!--                                    <div class="text-center col-span-2">03:45 PM</div>-->
		<!--                                    <div class="text-center text-green-600 font-semibold col-span-2">1800.00</div>-->
		<!--                                    <div class="text-center">-->
		<!--                                        <i class="fa fa-angle-right text-gray-400 hover:text-gray-600 cursor-pointer"-->
		<!--                                           aria-hidden="true"></i>-->
		<!--                                    </div>-->
		<!--                                </div>-->
		<!--                            </div>-->

		<!--                            &lt;!&ndash; Empty State (Uncomment when needed) &ndash;&gt;-->
		<!--                            &lt;!&ndash;-->
		<!--                            <div id="emptyState" class="p-8 text-center text-gray-500">-->
		<!--                              <i class="fas fa-shopping-cart text-4xl mb-4 text-gray-300"></i>-->
		<!--                              <p class="text-lg font-medium">No invoices added yet</p>-->
		<!--                              <p class="text-sm">Add invoices using Create Bill</p>-->
		<!--                            </div>-->
		<!--                            &ndash;&gt;-->
		<!--                        </div>-->
		<!--                    </div>-->
		<!--                </div>-->
		<!--            </div>-->

		<!--            &lt;!&ndash; Right Column - Preview &ndash;&gt;-->
		<!--            <div class="md:col-span-3 min-h-screen">-->
		<!--                <div class="rounded-2xl shadow-xl overflow-hidden sticky top-8 bg-white ">-->
		<!--                    <div class="justify-between items-center mx-6 py-4 flex border-b-2 border-gray-200">-->
		<!--                        <h2 class="text-lg font-semibold flex items-center">-->
		<!--                            <i class="far fa-file-alt mr-3"></i>-->
		<!--                            Detailed Invoice-->
		<!--                        </h2>-->
		<!--                        <button class="btn-primary px-4 py-2 rounded-lg text-sm flex items-center space-x-2">-->
		<!--                            <i class="fas fa-download"></i>-->
		<!--                            <span>PDF</span>-->
		<!--                        </button>-->
		<!--                    </div>-->
		<!--                    <div class="p-6">-->
		<!--                        &lt;!&ndash; Invoice Header &ndash;&gt;-->
		<!--                        <div class="text-center mb-6 pb-4 border-b-2 border-gray-200">-->
		<!--                            <div class="flex items-center justify-center space-x-3 mb-3">-->
		<!--                                <img src="assets/images/logo.png" alt="Pahana Edu" class="h-10 w-auto">-->
		<!--                            </div>-->
		<!--                            <p class="text-lg font-semibold text-blue-600" id="invoiceNumber">Invoice #</p>-->
		<!--                        </div>-->

		<!--                        &lt;!&ndash; Customer & Date Info &ndash;&gt;-->
		<!--                        <div class="grid grid-cols-2 gap-4 mb-6 text-sm">-->
		<!--                            <div>-->
		<!--                                <p class="text-gray-500 font-semibold">Bill To:</p>-->
		<!--                                <p class="font-bold text-gray-800" id="previewCustomerName">John Doe</p>-->
		<!--                                <p class="text-gray-600" id="previewCustomerId">CUST001</p>-->
		<!--                            </div>-->
		<!--                            <div>-->
		<!--                                <p class="text-gray-500 font-semibold">Date & Time:</p>-->
		<!--                                <p class="font-bold text-gray-800" id="previewDateTime"></p>-->
		<!--                            </div>-->
		<!--                        </div>-->

		<!--                        &lt;!&ndash; Items Summary &ndash;&gt;-->
		<!--                        <div class="space-y-3 mb-6">-->
		<!--                            <div class="flex justify-between text-sm font-semibold text-gray-700 border-b border-gray-200 pb-2">-->
		<!--                                <span>Item</span>-->
		<!--                                <span>Amount (Rs.)</span>-->
		<!--                            </div>-->
		<!--                            <div id="previewItems" class="space-y-2">-->
		<!--                                &lt;!&ndash; Items will be populated here &ndash;&gt;-->
		<!--                            </div>-->
		<!--                        </div>-->

		<!--                        &lt;!&ndash; Total &ndash;&gt;-->
		<!--                        <div class="pt-4 border-t-2 border-gray-200">-->
		<!--                            <div class="flex justify-between items-center">-->
		<!--                                <span class="text-xl font-bold text-gray-800">Total Amount (Rs.)</span>-->
		<!--                                <span class="text-2xl font-bold text-green-600" id="previewTotal">Rs. 0.00</span>-->
		<!--                            </div>-->
		<!--                        </div>-->

		<!--                        &lt;!&ndash; Footer &ndash;&gt;-->
		<!--                        <div class="mt-8 pt-4 border-t border-gray-200 text-center">-->
		<!--                            <p class="text-xs text-gray-500 mt-1">Powered by Pahana Edu Billing System</p>-->
		<!--                        </div>-->
		<!--                    </div>-->
		<!--                </div>-->
		<!--            </div>-->
		<!--        </div>-->
	</div>

	<!-- Add new customer dialog -->
	<%@ include file="/WEB-INF/views/common/new-customer-dialog.jsp"%>

</div>

<% if (request.getAttribute("openCustomerDialog") != null) { %>
  <script>
    window.addEventListener("DOMContentLoaded", function () {
      document.getElementById("new-customer-modal").showModal();
    });
  </script>
<% } %>

<script type="text/javascript"
	src="/bookshopManagement/assets/js/dialog-controller.js" defer></script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>