<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/user-guide.css">

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

<div class="ml-64 p-8">
	<div class="max-w-7xl mx-auto">
		<!-- Header -->
		<div class="text-start mb-8">
			<h1 class="text-3xl font-bold mb-2">User Guide</h1>
			<p class="text-lg text-gray-600 max-w-3xl mx-auto">Welcome to
				Pahana Edu Bookshop Management System. This comprehensive guide will
				help you navigate through all features and functionalities of the
				system.</p>
		</div>

		<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
			<!-- Welcome Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<div class="grid md:grid-cols-3 gap-6">
					<div class="text-center p-6 bg-blue-50 rounded-lg">
						<div
							class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center mx-auto mb-4">
							<svg class="w-6 h-6 text-blue-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                        </svg>
						</div>
						<h3 class="text-lg font-semibold text-gray-900 mb-2">Inventory
							Management</h3>
						<p class="text-gray-600">Efficiently manage your book
							inventory with advanced search and categorization.</p>
					</div>

					<div class="text-center p-6 bg-green-50 rounded-lg">
						<div
							class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center mx-auto mb-4">
							<svg class="w-6 h-6 text-green-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                        </svg>
						</div>
						<h3 class="text-lg font-semibold text-gray-900 mb-2">Customer
							Management</h3>
						<p class="text-gray-600">Keep track of customer information
							and purchase history seamlessly.</p>
					</div>

					<div class="text-center p-6 bg-purple-50 rounded-lg">
						<div
							class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center mx-auto mb-4">
							<svg class="w-6 h-6 text-purple-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                        </svg>
						</div>
						<h3 class="text-lg font-semibold text-gray-900 mb-2">Billing
							System</h3>
						<p class="text-gray-600">Generate professional bills and
							manage transactions with ease.</p>
					</div>
				</div>
			</div>

			<!-- Getting Started Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
					<span
						class="step-number w-8 h-8 rounded-full text-white text-sm font-semibold flex items-center justify-center mr-3">1</span>
					Getting Started
				</h2>

				<div class="grid md:grid-cols-2 gap-8">
					<div>
						<h3 class="text-xl font-semibold text-gray-900 mb-4">System
							Login</h3>
						<div class="space-y-4">
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-blue-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Enter your assigned username in the
									login form</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-blue-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Input your secure password</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-blue-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Click "Get Started" to access the
									dashboard</p>
							</div>
						</div>
					</div>

					<div class="bg-gray-50 rounded-lg p-3">
						<div class="bg-white rounded-lg shadow-sm border p-3">
							<div class="space-y-3">
								<img alt="Login Page"
									src="/bookshopManagement/assets/images/snapshot-login.png">
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Dashboard Navigation Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
					<span
						class="step-number w-8 h-8 rounded-full text-white text-sm font-semibold flex items-center justify-center mr-3">2</span>
					Dashboard Navigation
				</h2>

				<p class="text-gray-600 mb-6">The main dashboard provides quick
					access to all system features. Here's what each section offers:</p>

				<div class="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
					<div class="bg-blue-50 rounded-lg p-6 text-center">
						<div
							class="feature-icon w-12 h-12 rounded-lg flex items-center justify-center mx-auto mb-4">
							<svg class="w-6 h-6 text-blue-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                        </svg>
						</div>
						<h3 class="font-semibold text-gray-900 mb-2">Create Bill</h3>
						<p class="text-sm text-gray-600">Generate new customer bills
							with item selection and pricing</p>
					</div>

					<div class="bg-green-50 rounded-lg p-6 text-center">
						<div
							class="feature-icon w-12 h-12 rounded-lg flex items-center justify-center mx-auto mb-4">
							<svg class="w-6 h-6 text-green-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                        </svg>
						</div>
						<h3 class="font-semibold text-gray-900 mb-2">Manage Customers</h3>
						<p class="text-sm text-gray-600">Add, edit, and view customer
							information and purchase history</p>
					</div>

					<div class="bg-purple-50 rounded-lg p-6 text-center">
						<div
							class="feature-icon w-12 h-12 rounded-lg flex items-center justify-center mx-auto mb-4">
							<svg class="w-6 h-6 text-purple-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                        </svg>
						</div>
						<h3 class="font-semibold text-gray-900 mb-2">Search Inventory</h3>
						<p class="text-sm text-gray-600">Quick search through your
							entire book inventory</p>
					</div>

					<div class="bg-orange-50 rounded-lg p-6 text-center">
						<div
							class="feature-icon w-12 h-12 rounded-lg flex items-center justify-center mx-auto mb-4">
							<svg class="w-6 h-6 text-orange-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
						</div>
						<h3 class="font-semibold text-gray-900 mb-2">User Guide</h3>
						<p class="text-sm text-gray-600">Access comprehensive help
							documentation and tutorials</p>
					</div>
				</div>
			</div>

			<!-- Inventory Management Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
					<span
						class="step-number w-8 h-8 rounded-full text-white text-sm font-semibold flex items-center justify-center mr-3">3</span>
					Inventory Management
				</h2>

				<div class="grid lg:grid-cols-2 gap-8">
					<div>
						<h3 class="text-xl font-semibold text-gray-900 mb-4">Adding
							New Items</h3>
						<div class="space-y-3 mb-6">
							<div class="flex items-start space-x-3">
								<div
									class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mt-0.5">
									<span class="text-xs font-semibold text-blue-600">1</span>
								</div>
								<p class="text-gray-700">Click "+ New Item" button in the
									inventory section</p>
							</div>
							<div class="flex items-start space-x-3">
								<div
									class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mt-0.5">
									<span class="text-xs font-semibold text-blue-600">2</span>
								</div>
								<p class="text-gray-700">Fill in item details: ID, name,
									category, quantity, and price</p>
							</div>
							<div class="flex items-start space-x-3">
								<div
									class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mt-0.5">
									<span class="text-xs font-semibold text-blue-600">3</span>
								</div>
								<p class="text-gray-700">Select appropriate category (Books,
									Stationery, Educational Materials, etc.)</p>
							</div>
							<div class="flex items-start space-x-3">
								<div
									class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mt-0.5">
									<span class="text-xs font-semibold text-blue-600">4</span>
								</div>
								<p class="text-gray-700">Save the item to update inventory</p>
							</div>
						</div>

						<h3 class="text-xl font-semibold text-gray-900 mb-4">Searching
							& Filtering</h3>
						<div class="space-y-3 mb-6">
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-green-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Use the search bar to find items by
									ID or name</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-green-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Filter by category tabs (All,
									Books, Stationery, etc.)</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-green-600 rounded-full mt-2"></div>
								<p class="text-gray-700">View quantity available and unit
									price for each item</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-green-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Click on respective buttons to edit
									or view details</p>
							</div>
						</div>

						<h3 class="text-xl font-semibold text-gray-900 mb-4">Item
							Management</h3>
						<div class="space-y-3">
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Edit & Delete Options:</strong> Each item row includes
									two buttons, one for editing details and another for deleting
									the item.
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Deleted Items View:</strong> Access a dedicated list of
									deleted items where you can restore them. Each deleted record
									displays details such as quantity available, price, and
									deletion date.
								</p>
							</div>
						</div>

					</div>

					<div class="bg-gray-50 rounded-lg p-6">
						<h4 class="font-semibold text-gray-900 mb-4">Sample Inventory
							View</h4>
						<div class="bg-white rounded-lg shadow-sm border overflow-hidden">
							<div class="bg-blue-600 text-white p-3">
								<h5 class="font-medium">Manage Inventory</h5>
							</div>
							<div class="p-4">
								<div class="flex space-x-2 mb-4">
									<button
										class="px-3 py-1 bg-blue-100 text-blue-700 rounded text-sm">All</button>
									<button
										class="px-3 py-1 bg-gray-100 text-gray-700 rounded text-sm">Books</button>
									<button
										class="px-3 py-1 bg-gray-100 text-gray-700 rounded text-sm">Stationery</button>
								</div>
								<div class="space-y-2">
									<div
										class="flex justify-between items-center p-2 bg-gray-50 rounded">
										<span class="text-sm">Text Book</span> <span
											class="text-sm font-medium">Qty: 250</span>
									</div>
									<div
										class="flex justify-between items-center p-2 bg-gray-50 rounded">
										<span class="text-sm">Pencil Box</span> <span
											class="text-sm font-medium">Qty: 100</span>
									</div>
									<div
										class="flex justify-between items-center p-2 bg-gray-50 rounded">
										<span class="text-sm">Water Bottle</span> <span
											class="text-sm font-medium">Qty: 350</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Customer Management Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
					<span
						class="step-number w-8 h-8 rounded-full text-white text-sm font-semibold flex items-center justify-center mr-3">4</span>
					Customer Management
				</h2>

				<div class="grid lg:grid-cols-2 gap-8">
					<div>
						<h3 class="text-xl font-semibold text-gray-900 mb-4">Adding
							New Customers</h3>
						<div class="bg-blue-50 rounded-lg p-4 mb-6">
							<div class="flex items-center space-x-2 mb-3">
								<svg class="w-5 h-5 text-blue-600" fill="none"
									stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round"
										stroke-linejoin="round" stroke-width="2"
										d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
								<h4 class="font-medium text-blue-900">Required Information</h4>
							</div>
							<div class="grid grid-cols-2 gap-4 text-sm">
								<div class="space-y-1">
									<p class="font-medium text-gray-700">Personal Details:</p>
									<ul class="text-gray-600 space-y-1">
										<li>• First Name</li>
										<li>• Last Name</li>
									</ul>
								</div>
								<div class="space-y-1">
									<p class="font-medium text-gray-700">Contact Information:</p>
									<ul class="text-gray-600 space-y-1">
										<li>• Phone Number</li>
										<li>• Email Address</li>
										<li>• Home Address</li>
									</ul>
								</div>
							</div>
							<p class="mt-4 text-xs text-gray-500">
								<em>Note: Customer ID will be automatically generated by
									the system upon saving.</em>
							</p>
						</div>

						<h3 class="text-xl font-semibold text-gray-900 mb-4">Customer
							Search & History</h3>
						<div class="space-y-3">
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-purple-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Search customers by ID or mobile
									number</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-purple-600 rounded-full mt-2"></div>
								<p class="text-gray-700">View complete purchase history and
									bill details</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-purple-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Track total units consumed and
									spending</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-purple-600 rounded-full mt-2"></div>
								<p class="text-gray-700">Edit customer information as needed</p>
							</div>
						</div>
					</div>

					<div class="bg-gray-50 rounded-lg p-6">
						<h4 class="font-semibold text-gray-900 mb-4">Customer Profile
							Example</h4>
						<div class="bg-white rounded-lg shadow-sm border p-4">
							<div class="space-y-4">
								<div>
									<label class="block text-sm font-medium text-gray-700 mb-1">Customer
										ID</label> <input type="text" value="C0123"
										class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm bg-gray-50"
										readonly>
								</div>
								<div class="grid grid-cols-2 gap-3">
									<div>
										<label class="block text-sm font-medium text-gray-700 mb-1">First
											Name</label> <input type="text" value="John"
											class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
									</div>
									<div>
										<label class="block text-sm font-medium text-gray-700 mb-1">Last
											Name</label> <input type="text" value="Doe"
											class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
									</div>
								</div>
								<div>
									<label class="block text-sm font-medium text-gray-700 mb-1">Phone</label>
									<input type="text" value="0700000000"
										class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
								</div>
								<div>
									<label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
									<input type="email" value="john@gmail.com"
										class="w-full px-3 py-2 border border-gray-300 rounded-md text-sm">
								</div>
								<button
									class="w-full bg-blue-600 text-white py-2 rounded-md text-sm font-medium">Save
									Changes</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Billing System Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
					<span
						class="step-number w-8 h-8 rounded-full text-white text-sm font-semibold flex items-center justify-center mr-3">5</span>
					Billing System
				</h2>

				<div class="grid lg:grid-cols-2 gap-8">
					<div>
						<h3 class="text-xl font-semibold text-gray-900 mb-4">Creating
							a New Bill</h3>
						<div class="space-y-4 mb-6">
							<div class="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
								<div class="flex items-center space-x-2 mb-2">
									<svg class="w-5 h-5 text-yellow-600" fill="none"
										stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round"
											stroke-linejoin="round" stroke-width="2"
											d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L5.082 16.5c-.77.833-.192 2.5 1.732 2.5z" />
                                </svg>
									<h4 class="font-medium text-yellow-800">Step-by-Step
										Process</h4>
								</div>
								<div class="text-sm text-yellow-700 space-y-1">
									<p>1. Enter customer mobile number to search</p>
									<p>2. Confirm customer details or add new customer</p>
									<p>3. Add items to the bill using "Add to List" button</p>
									<p>4. Specify quantity and verify unit prices</p>
									<p>5. Review total amount and confirm the bill</p>
								</div>
							</div>
						</div>

						<h3 class="text-xl font-semibold text-gray-900 mb-4">Bill
							Management Features</h3>
						<div class="space-y-3">
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Quick Customer Addition:</strong> Add a new customer
									instantly with the Quick Add button next to customer search
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Duplicate Item Handling:</strong> If the same item is
									added twice, the quantity automatically increases instead of
									creating a duplicate line
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Item Removal:</strong> Cashiers can remove items from
									the list before generating the bill
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Real-time Inventory Updates:</strong> Stock levels
									adjust automatically when bills are confirmed
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Automatic Customer Emails:</strong> Each confirmed bill
									is sent to the customer via email
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Customer Usage Tracking:</strong> Units consumed by the
									customer are updated automatically
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Bill History:</strong> All transactions are stored and
									can be viewed in customer profiles
								</p>
							</div>
							<div class="flex items-start space-x-3">
								<div class="w-2 h-2 bg-indigo-600 rounded-full mt-2"></div>
								<p class="text-gray-700">
									<strong>Print/Export Options:</strong> Generate printable
									receipts for customers
								</p>
							</div>
						</div>
					</div>

					<div class="bg-gray-50 rounded-lg p-6">
						<h4 class="font-semibold text-gray-900 mb-4">Sample Bill
							Interface</h4>
						<div class="bg-white rounded-lg shadow-sm border overflow-hidden">
							<div class="bg-blue-600 text-white p-3">
								<h5 class="font-medium">Create Bill</h5>
							</div>
							<div class="p-4 space-y-4">
								<div>
									<label class="block text-sm font-medium text-gray-700 mb-1">Customer
										Details</label>
									<div class="flex space-x-2">
										<input type="text" placeholder="eg: 0700000000"
											class="flex-1 px-3 py-2 border border-gray-300 rounded-md text-sm">
										<button
											class="px-4 py-2 bg-blue-600 text-white rounded-md text-sm">Search</button>
									</div>
									<div class="mt-2 p-2 bg-gray-50 rounded text-sm">
										<p>
											<strong>Customer ID:</strong> C0123
										</p>
										<p>
											<strong>Name:</strong> John Doe
										</p>
									</div>
								</div>

								<div>
									<label class="block text-sm font-medium text-gray-700 mb-2">Items</label>
									<div class="space-y-2">
										<div
											class="flex justify-between items-center p-2 bg-gray-50 rounded text-sm">
											<span>Text Book</span>
											<div class="flex items-center space-x-2">
												<span>Qty: 5</span> <span>₹200</span> <span>₹1000</span>
											</div>
										</div>
										<div
											class="flex justify-between items-center p-2 bg-gray-50 rounded text-sm">
											<span>Text Book</span>
											<div class="flex items-center space-x-2">
												<span>Qty: 5</span> <span>₹200</span> <span>₹1000</span>
											</div>
										</div>
									</div>
								</div>

								<div class="border-t pt-3">
									<button
										class="w-full bg-blue-600 text-white py-2 rounded-md text-sm font-medium mb-2">Add
										to List</button>
									<button
										class="w-full bg-green-600 text-white py-2 rounded-md text-sm font-medium">Confirm</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Troubleshooting Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
					<span
						class="step-number w-8 h-8 rounded-full text-white text-sm font-semibold flex items-center justify-center mr-3">6</span>
					Troubleshooting & FAQs
				</h2>

				<div class="grid md:grid-cols-2 gap-8">
					<!-- Common Issues -->
					<div>
						<h3 class="text-xl font-semibold text-gray-900 mb-4">Common
							Issues & Solutions</h3>
						<div class="space-y-6">

							<!-- Login Issues -->
							<div class="border-l-4 border-red-400 pl-4">
								<h4 class="font-semibold text-gray-900 mb-2">Login Problems</h4>
								<p class="text-gray-600 text-sm mb-2">
									<strong>Issue:</strong> Unable to log in to the system.
								</p>
								<p class="text-gray-600 text-sm">
									<strong>Solution:</strong> Double-check your username and
									password. If the problem continues, contact the administrator
									to reset your password or resolve account issues.
								</p>
							</div>

							<!-- Inventory Issues -->
							<div class="border-l-4 border-yellow-400 pl-4">
								<h4 class="font-semibold text-gray-900 mb-2">Inventory Not
									Updating</h4>
								<p class="text-gray-600 text-sm mb-2">
									<strong>Issue:</strong> Stock levels do not reflect recent
									transactions.
								</p>
								<p class="text-gray-600 text-sm">
									<strong>Solution:</strong> Make sure bills are confirmed and
									check for pending transactions. Try refreshing the page or
									logging out and back in.
								</p>
							</div>

							<!-- Customer Search -->
							<div class="border-l-4 border-blue-400 pl-4">
								<h4 class="font-semibold text-gray-900 mb-2">Customer
									Search Not Working</h4>
								<p class="text-gray-600 text-sm mb-2">
									<strong>Issue:</strong> Cannot locate an existing customer.
								</p>
								<p class="text-gray-600 text-sm">
									<strong>Solution:</strong> Try searching with alternative
									details such as customer ID or phone number. Check for spelling
									errors.
								</p>
							</div>

							<!-- Bill Errors -->
							<div class="border-l-4 border-green-400 pl-4">
								<h4 class="font-semibold text-gray-900 mb-2">Bill Creation
									or Printing Errors</h4>
								<p class="text-gray-600 text-sm mb-2">
									<strong>Issue:</strong> Unable to generate or print a bill.
								</p>
								<p class="text-gray-600 text-sm">
									<strong>Solution:</strong> Ensure all required customer details
									are filled in and that items are in stock. If printing, check
									printer connections and settings.
								</p>
							</div>

						</div>
					</div>

					<!-- FAQs -->
					<div>
						<h3 class="text-xl font-semibold text-gray-900 mb-4">Frequently
							Asked Questions</h3>
						<div class="space-y-4">

							<div class="bg-gray-50 rounded-lg p-4">
								<h4 class="font-medium text-gray-900 mb-2">Q: Can I change
									prices after a bill is created?</h4>
								<p class="text-gray-600 text-sm">A: No. Once a bill is
									confirmed, prices cannot be changed. Create a new bill with the
									correct prices or contact the administrator for assistance.</p>
							</div>

							<div class="bg-gray-50 rounded-lg p-4">
								<h4 class="font-medium text-gray-900 mb-2">Q: What if I
									accidentally deactivate a customer?</h4>
								<p class="text-gray-600 text-sm">
									A: You can reactivate them. Search for the customer, click <strong>Reactivate</strong>,
									and confirm the action.
								</p>
							</div>

							<div class="bg-gray-50 rounded-lg p-4">
								<h4 class="font-medium text-gray-900 mb-2">Q: What if I
									delete an item by mistake?</h4>
								<p class="text-gray-600 text-sm">
									A: Deleted items can be restored. Go to the <strong>View
										Deleted Items</strong> page, click <strong>Restore</strong>, and
									confirm.
								</p>
							</div>

						</div>
					</div>
				</div>
			</div>


			<!-- Best Practices Section -->
			<div class="section-card rounded-xl p-8 mb-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 flex items-center">
					<span
						class="step-number w-8 h-8 rounded-full text-white text-sm font-semibold flex items-center justify-center mr-3">7</span>
					Best Practices & Tips
				</h2>

				<div class="grid md:grid-cols-3 gap-6">

					<!-- Daily Operations -->
					<div
						class="bg-gradient-to-br from-emerald-50 to-emerald-100 rounded-lg p-6">
						<div
							class="w-10 h-10 bg-emerald-600 rounded-lg flex items-center justify-center mb-4">
							<svg class="w-5 h-5 text-white" fill="none" stroke="currentColor"
								viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
						</div>
						<h3 class="font-semibold text-gray-900 mb-3">Daily Operations</h3>
						<ul class="space-y-2 text-sm text-gray-600">
							<li>• Check inventory levels at the start of each day</li>
							<li>• Add and update stock for new arrivals promptly</li>
						</ul>
					</div>

					<!-- Performance Tips -->
					<div
						class="bg-gradient-to-br from-amber-50 to-amber-100 rounded-lg p-6">
						<div
							class="w-10 h-10 bg-amber-600 rounded-lg flex items-center justify-center mb-4">
							<svg class="w-5 h-5 text-white" fill="none" stroke="currentColor"
								viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
        </svg>
						</div>
						<h3 class="font-semibold text-gray-900 mb-3">Performance Tips</h3>
						<ul class="space-y-2 text-sm text-gray-600">
							<li>• Use specific keywords (title, ID) for faster searches</li>
							<li>• Archive old records to keep the database responsive</li>
							<li>• Keep customer profiles updated with current details</li>
							<li>• Learn and use keyboard shortcuts for common actions</li>
							<li>• Close unused tabs or windows to improve speed</li>
						</ul>
					</div>

					<!-- Security Guidelines -->
					<div
						class="bg-gradient-to-br from-rose-50 to-rose-100 rounded-lg p-6">
						<div
							class="w-10 h-10 bg-rose-600 rounded-lg flex items-center justify-center mb-4">
							<svg class="w-5 h-5 text-white" fill="none" stroke="currentColor"
								viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
        </svg>
						</div>
						<h3 class="font-semibold text-gray-900 mb-3">Security
							Guidelines</h3>
						<ul class="space-y-2 text-sm text-gray-600">
							<li>• Never share your login credentials</li>
							<li>• Log out before leaving your workstation</li>
							<li>• Use strong passwords</li>
							<li>• Keep the system updated to the latest version</li>
						</ul>
					</div>

				</div>
			</div>


			<!-- Support & Contact Section -->
			<div class="section-card rounded-xl p-8 shadow-lg">
				<h2 class="text-2xl font-bold text-gray-900 mb-6 text-center">Need
					Additional Help?</h2>

				<div class="grid md:grid-cols-2 gap-8">
					<div class="text-center">
						<div
							class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
							<svg class="w-8 h-8 text-blue-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M18.364 5.636l-3.536 3.536m0 5.656l3.536 3.536M9.172 9.172L5.636 5.636m3.536 9.192L5.636 18.364M12 2.196l3.536 3.536M12 21.804L8.464 18.268M2.196 12l3.536-3.536M21.804 12l-3.536 3.536" />
                        </svg>
						</div>
						<h3 class="text-xl font-semibold text-gray-900 mb-3">Technical
							Support</h3>
						<p class="text-gray-600 mb-4">Get help with technical issues,
							system errors, and feature questions from our support team.</p>
						<div class="space-y-2 text-sm">
							<p class="text-gray-700">
								<strong>Email:</strong> support@pahanaedu.com
							</p>
							<p class="text-gray-700">
								<strong>Phone:</strong> +94 11 234 5678
							</p>
							<p class="text-gray-700">
								<strong>Hours:</strong> Mon-Fri, 9 AM - 6 PM
							</p>
						</div>
					</div>

					<div class="text-center">
						<div
							class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
							<svg class="w-8 h-8 text-green-600" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                        </svg>
						</div>
						<h3 class="text-xl font-semibold text-gray-900 mb-3">Training
							& Resources</h3>
						<p class="text-gray-600 mb-4">Access video tutorials,
							documentation, and training materials to master the system.</p>
						<div class="space-y-2 text-sm">
							<p class="text-gray-700">
								<strong>Online Portal:</strong> help.pahanaedu.com
							</p>
							<p class="text-gray-700">
								<strong>Video Tutorials:</strong> Available 24/7
							</p>
							<p class="text-gray-700">
								<strong>Live Training:</strong> By appointment
							</p>
						</div>
					</div>
				</div>

				<div class="mt-8 text-center">
					<div
						class="inline-flex items-center space-x-2 text-sm text-gray-500">
						<svg class="w-4 h-4" fill="none" stroke="currentColor"
							viewBox="0 0 24 24">
                        <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2"
								d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
						<span>This user guide was last updated on August 2025 •
							Version 2.1</span>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>