<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/inventory.css">

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

<div class="ml-64 p-8">
	<div class="max-w-7xl mx-auto">
		<!-- Header -->
		<div class="flex items-center justify-between">
			<h1 class="text-3xl font-bold mb-2">Manage Inventory</h1>
			<button id="new-item-btn"
				class="btn-primary px-6 py-2 rounded-xl font-medium transition-all flex items-center justify-center"
				data-dialog-open="new-item-modal">
				<i class="fa-solid fa-plus mr-2 light-icon"></i> <span>New</span>
			</button>
		</div>

		<!-- Search input-->
		<div
			class="sticky top-4 z-50 bg-white rounded-2xl shadow-xl border border-blue-300 overflow-hidden px-6 py-4 my-4">
			<form action="inventory" method="get">
				<label for="itemSearch" class="text-lg font-semibold mb-2 block">
					Enter item ID or name and click search </label>
				<div class="flex gap-3">
					<input type="text" name="search" value="${param.search}"
						placeholder="e.g., ITM0001 or text book" class="px-4 py-2"
						id="itemSearch">
					<button type="submit"
						class="bg-blue-300 text-black font-semibold px-6 rounded-lg hover:bg-blue-400">
						Search</button>
				</div>
			</form>
		</div>

		<!-- Inventory details -->
		<div
			class="bg-white rounded-2xl shadow-xl border border-gray-100 overflow-hidden p-6 my-6">
			<!-- Category details -->
			<div class="mb-6">
				<div class="flex items-center mb-4">
					<i
						class="fa-solid fa-icons text-[var(--color-primary)] text-xl mr-3"></i>
					<h2 class="text-2xl font-semibold text-gray-800">Explore By
						Category</h2>
				</div>
				<div id="categoryList" class="flex flex-wrap gap-2">
					<c:forEach var="category" items="${categories}">
						<form action="inventory" method="get">
							<input type="hidden" name="category" value="${category.id}" />
							<button type="submit"
								class="${selectedCategoryId == category.id ? 'btn-selected' : 'btn-secondary'} px-4 py-2 border border-gray-200 rounded-xl text-sm font-semibold transition duration-200 text-gray-700">
								${category.name}</button>
						</form>
					</c:forEach>

					<!-- "All" button to remove the filter -->
					<form action="inventory" method="get">
						<button type="submit"
							class="${selectedCategoryId == null ? 'btn-selected' : 'btn-secondary'} px-4 py-2 border border-gray-200 rounded-xl text-sm font-semibold transition duration-200 text-gray-700">
							All</button>
					</form>
				</div>
			</div>

			<!-- Items list -->
			<div class="fade-in">
				<div
					class="overflow-hidden rounded-xl border border-gray-200 shadow-md">
					<!-- Header -->
					<div
						class="bg-gradient-to-r from-gray-50 to-gray-100 px-3 py-4 border-b border-gray-200">
						<div
							class="grid grid-cols-12 text-sm font-semibold text-gray-600 uppercase tracking-wide">
							<div class="text-center">Item ID</div>
							<div class="text-center col-span-3">Name</div>
							<div class="text-center col-span-3">Category</div>
							<div class="text-center col-span-2">Price (Rs.)</div>
							<div class="text-center col-span-2">Quantity</div>
							<div class="text-center"></div>
						</div>
					</div>

					<!-- Items -->
					<div id="itemsList" class="divide-y divide-gray-100">
						<c:forEach var="item" items="${items}">
							<div
								class="grid grid-cols-12 px-3 py-4 text-gray-700 hover:bg-gray-50 transition duration-150">
								<div class="text-center font-medium">${item.id}</div>
								<div class="text-center col-span-3">${item.name}</div>
								<div class="text-center col-span-3">${item.category.name}</div>
								<div class="text-center text-green-600 font-semibold col-span-2">
									<fmt:formatNumber value="${item.unitPrice}" type="number"
										minFractionDigits="2" maxFractionDigits="2" />
								</div>
								<div class="text-center col-span-2">${item.quantityAvailable}</div>
								<div class="text-center"
									data-dialog-open="delete-confirmation-modal"
									data-item-id="${item.id}" data-item-name="${item.name}">
									<i
										class="fa-solid fa-trash text-gray-400 hover:text-red-600 cursor-pointer"></i>
								</div>
							</div>
						</c:forEach>
					</div>

					<!-- Empty State -->
					<c:if test="${empty items}">
						<div id="emptyState" class="p-10 text-center text-gray-500">
							<i class="fas fa-box-open text-5xl mb-4 text-gray-300"></i>
							<p class="text-lg font-medium">No Items Found</p>
							<p class="text-sm text-gray-400 text-add-item"
								data-dialog-open="new-item-modal">Add New Item</p>
						</div>
					</c:if>
				</div>

				<!-- Pagination -->
				<div class="flex justify-between items-center mt-6 px-2">
					<div>
						<c:choose>
							<c:when test="${empty items}">
								<span class="text-sm text-gray-600">No Items Found</span>
							</c:when>
							<c:otherwise>
								<span class="text-sm text-gray-600"> Showing 1 -
									${items.size()} of ${items.size()} items </span>
							</c:otherwise>
						</c:choose>
						<p class="text-xs text-gray-400 mt-1">Last updated: Today</p>
					</div>
					<div class="flex items-center space-x-3">
						<button class="pagination-btn disabled">
							<i class="fas fa-chevron-left mr-1"></i> Previous
						</button>
						<div class="flex space-x-2">
							<button class="btn-primary px-4 py-2 rounded-xl font-semibold">1</button>
						</div>
						<button class="pagination-btn disabled">
							Next <i class="fas fa-chevron-right ml-1"></i>
						</button>
					</div>
				</div>
			</div>
		</div>

	</div>

	<!-- Add new item dialog -->
	<dialog id="new-item-modal"
		class="m-auto p-0 border-0 w-full max-w-2xl rounded-xl bg-transparent h-full">
	<div
		class="relative bg-white rounded-xl shadow-2xl border border-gray-100 overflow-hidden">
		<!-- Modal content -->
		<div
			class="relative bg-white rounded-xl shadow-2xl border border-gray-100 overflow-hidden">
			<!-- Modal header -->
			<div
				class="bg-gradient-to-r from-blue-50 to-indigo-50 px-6 py-3 border-b border-gray-200">
				<div class="flex items-center justify-between">
					<div class="flex items-center space-x-3">
						<div
							class="w-8 h-8 bg-blue-100 rounded-lg flex items-center justify-center">
							<i class="fa-solid fa-book text-blue-600 text-sm"></i>
						</div>
						<h3 class="text-xl font-bold">Add New Item</h3>
					</div>
					<button type="button" id="new-item-close-btn"
						class="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 flex items-center justify-center transition-colors group">
						<i class="fas fa-times text-gray-500 group-hover:text-gray-700"></i>
					</button>
				</div>
			</div>

			<!-- Modal body -->
			<div class="px-6 py-5">
				<form class="space-y-4" action="#" id="customer-form">
					<div class="space-y-2">
						<label for="name"
							class="block text-sm font-semibold text-gray-700"> <i
							class="fa-solid fa-font text-gray-400 mr-2"></i> Item Name <span
							class="text-red-500">*</span>
						</label> <input type="text" name="name" id="name"
							class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
							placeholder="Text Book" required />
					</div>

					<div class="space-y-2">
						<label for="category"
							class="block text-sm font-semibold text-gray-700"> <i
							class="fa-solid fa-list-ul text-gray-400 mr-2"></i> Category <span
							class="text-red-500">*</span>
						</label> <select name="category" id="category"
							class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors text-gray-700">
							<option value="" disabled selected>Select a category</option>
							<option value="books">Books</option>
							<option value="stationery">Stationery</option>
							<option value="educational">Educational Material</option>
							<option value="gift">Gift Items</option>
							<option value="misc">Miscellaneous</option>
							<option value="all">All</option>
						</select>
					</div>

					<div class="space-y-2">
						<label for="price"
							class="block text-sm font-semibold text-gray-700"> <i
							class="fa-solid fa-money-bill text-gray-400 mr-2"></i> Unit Price
							in LKR <span class="text-red-500">*</span>
						</label> <input type="number" name="price" id="price"
							class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400"
							placeholder="200" required />
					</div>

					<div class="space-y-2">
						<label for="quantity"
							class="block text-sm font-semibold text-gray-700"> <i
							class="fa-solid fa-hashtag text-gray-400 mr-2"></i> Quantity
							Available <span class="text-red-500">*</span>
						</label> <input type="number" name="quantity" id="quantity"
							class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors placeholder-gray-400 resize-none"
							placeholder="1500" required />
					</div>

					<!-- Action buttons -->
					<div class="flex flex-col sm:flex-row gap-3">
						<button type="button" id="cancel-btn"
							class="flex-1 px-6 py-3 border border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-50 transition-colors"
							data-dialog-close>Cancel</button>
						<button type="submit"
							class="flex-1 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white font-semibold rounded-lg px-6 py-3 focus:ring-4 focus:ring-blue-300 transition-all transform hover:scale-[1.02] active:scale-[0.98]">
							<i class="fa-solid fa-save mr-2"></i> Save Item
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</dialog>

	<dialog id="delete-confirmation-modal"
		class="fixed inset-0 items-center justify-center max-w-xl p-0 bg-transparent backdrop-blur-sm">
	<div
		class="relative bg-white rounded-xl shadow-2xl border border-gray-100 overflow-hidden">

		<!-- Modal header -->
		<div
			class="bg-gradient-to-r from-red-50 to-red-100 px-6 py-3 border-b border-gray-200">
			<div class="flex items-center justify-between">
				<div class="flex items-center space-x-3">
					<div
						class="w-8 h-8 bg-red-100 rounded-lg flex items-center justify-center">
						<i class="fa-solid fa-triangle-exclamation text-red-600 text-sm"></i>
					</div>
					<h3 class="text-xl font-bold text-red-700">Confirm Delete</h3>
				</div>
				<button type="button" id="delete-close-btn"
					class="w-10 h-10 rounded-full bg-gray-100 hover:bg-gray-200 flex items-center justify-center">
					<i class="fas fa-times text-gray-500"></i>
				</button>
			</div>
		</div>

		<!-- Modal body -->
		<div class="px-6 py-5">
			<p class="text-gray-700 text-base mb-4">
				Are you sure you want to delete <span class="font-semibold"
					id="delete-item-name-display">"Item Name"</span>? <br>This
				action cannot be undone.
			</p>

			<!-- Action buttons -->
			<div class="flex flex-col sm:flex-row gap-3">
				<form action="item" method="post" id="delete-form">
					<input type="hidden" name="action" value="delete" /> <input
						type="hidden" name="itemId" id="delete-item-id" />
					<div class="flex gap-3">
						<button type="button" id="cancel-delete-btn"
							class="flex-1 px-6 py-3 border border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-100">
							Cancel</button>
						<button type="submit"
							class="flex-1 bg-gradient-to-r from-red-600 to-red-700 hover:from-red-700 hover:to-red-800 text-white font-semibold rounded-lg px-6 py-3 transition-all transform hover:scale-[1.02] active:scale-[0.98]">
							<i class="fa-solid fa-trash mr-2"></i> Delete
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</dialog>

</div>

<script type="text/javascript"
	src="/bookshopManagement/assets/js/dialog-controller.js" defer></script>
<script type="text/javascript"
	src="/bookshopManagement/assets/js/inventory.js" defer></script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>