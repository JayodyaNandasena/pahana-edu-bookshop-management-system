<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/bill.css">

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

<div class="ml-64 p-8">
	<div class="max-w-7xl mx-auto">
		<!-- Header -->
		<div class="bg-white rounded-lg shadow-lg overflow-hidden">
			<div class="bg-blue-800 text-white p-6">
				<div class="flex items-center justify-between">
					<div>
						<h1 class="text-blue-100 text-2xl font-bold flex items-center">
							${bill.code}</h1>
					</div>
					<div class="text-right">
						<div class="text-xl font-bold">
							<fmt:formatNumber value="${bill.total}" type="currency"
								currencySymbol="Rs. " />
						</div>
						<div class="text-blue-100 text-sm">Total Amount</div>
					</div>
				</div>
			</div>

			<!-- Bill Information -->
			<div class="p-6">
				<div
					class="grid grid-cols-1 md:grid-cols-1 lg:grid-cols-3 gap-6 mb-8">

					<div class="bg-gray-50 p-4 rounded-lg">
						<div class="flex items-center text-gray-600 mb-2">
							<i class="fas fa-calendar-alt mr-2"></i> <span
								class="font-medium">Date</span>
						</div>
						<div class="text-md font-semibold text-gray-900">${bill.date}</div>
					</div>

					<div class="bg-gray-50 p-4 rounded-lg">
						<div class="flex items-center text-gray-600 mb-2">
							<i class="fas fa-clock mr-2"></i> <span class="font-medium">Time</span>
						</div>
						<div class="text-md font-semibold text-gray-900">${bill.time}</div>
					</div>

					<div class="bg-gray-50 p-4 rounded-lg">
						<div class="flex items-center text-gray-600 mb-2">
							<i class="fas fa-user-tie mr-2"></i> <span class="font-medium">Cashier</span>
						</div>
						<div class="text-md font-semibold text-gray-900">${bill.cashier.name}</div>
					</div>
				</div>

				<!-- Items Table -->
				<div class="mb-6">
					<h2 class="text-xl font-bold text-gray-900 mb-4 flex items-center">
						<i class="fas fa-shopping-cart mr-2"></i> Items Purchased
					</h2>

					<div class="overflow-x-auto">
						<table class="w-full bg-white border border-gray-200 rounded-lg">
							<thead class="bg-gray-50">
								<tr>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
										<i class="fas fa-hashtag mr-1"></i> Item ID
									</th>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
										<i class="fas fa-tag mr-1"></i> Item
									</th>
									<th
										class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
										<i class="fas fa-sort-numeric-up mr-1"></i> Quantity
									</th>
									<th
										class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
										<i class="fas fa-dollar-sign mr-1"></i> Unit Price
									</th>
									<th
										class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
										<i class="fas fa-calculator mr-1"></i> Total
									</th>
								</tr>
							</thead>
							<tbody class="divide-y divide-gray-200">
								<c:forEach var="bi" items="${bill.billItems}">
									<tr>
										<td class="px-6 py-4 text-md text-gray-900">${bi.item.id}</td>
										<td class="px-6 py-4 text-md text-gray-900">${bi.item.name}</td>
										<td class="px-6 py-4 text-md text-center text-gray-900">${bi.quantity}</td>
										<td class="px-6 py-4 text-md text-right text-gray-900"><fmt:formatNumber
												value="${bi.unitPrice}" type="currency"
												currencySymbol="Rs. " /></td>
										<td class="px-6 py-4 text-md text-right text-gray-900"><fmt:formatNumber
												value="${bi.quantity * bi.unitPrice}" type="currency"
												currencySymbol="Rs. " /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<!-- Bill Summary -->
				<div class="bg-gray-50 rounded-lg p-6">
					<div
						class="flex justify-between items-center text-xl font-bold text-gray-900">
						<span class="flex items-center"> <i
							class="fas fa-money-bill-wave mr-2"></i> Total Amount
						</span> <span class="text-green-600"> <fmt:formatNumber
								value="${bill.total}" type="currency" currencySymbol="Rs. " />
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
