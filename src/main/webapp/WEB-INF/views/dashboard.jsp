<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/dashboard.css">

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

<div class="ml-64 flex-1 p-8">
	<!-- Welcome Section -->
	<div class="col-span-1 md:col-span-2 mb-4">
		<h1 class="text-3xl font-bold mb-2 pb-3">Welcome
			${sessionScope.user.name}</h1>
	</div>

	<!-- Business Metrics Section -->
	<div class="mb-8">
		<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
			<!-- Total Revenue Card -->
			<div
				class="bg-gradient-to-br from-emerald-50 to-emerald-100 rounded-xl shadow-sm border border-emerald-200 px-4 py-6 hover:shadow-md transition-all hover:scale-[1.02]">
				<div class="flex items-center justify-between mb-2">
					<p class="text-emerald-700 text-xs font-medium">Total Revenue</p>
				</div>
				<p class="text-3xl font-bold text-emerald-800">
					<span class="text-lg text-emerald-600">Rs. </span>${totalBills != null ? totalBills : '0.00'}
				</p>
			</div>

			<!-- Average Order Value Card -->
			<div
				class="bg-gradient-to-br from-blue-50 to-blue-100 rounded-xl shadow-sm border border-blue-200 px-4 py-6 hover:shadow-md transition-all hover:scale-[1.02]">
				<div class="flex items-center justify-between mb-2">
					<p class="text-blue-700 text-xs font-medium">Average Order
						Value</p>
				</div>
				<p class="text-3xl font-bold text-blue-800">
					<span class="text-lg text-blue-600">Rs. </span>${averageBills != null ? averageBills : '0.00'}
				</p>
			</div>

			<!-- Active Customers Card -->
			<div
				class="bg-gradient-to-br from-purple-50 to-purple-100 rounded-xl shadow-sm border border-purple-200 px-4 py-6 hover:shadow-md transition-all hover:scale-[1.02]">
				<div class="flex items-center justify-between mb-2">
					<p class="text-purple-700 text-xs font-medium">Active Customers</p>
				</div>
				<p class="text-3xl font-bold text-purple-800">${activeCustomerCount != null ? activeCustomerCount : '0'}</p>
			</div>

			<!-- Average Revenue Per Customer Card -->
			<div
				class="bg-gradient-to-br from-orange-50 to-orange-100 rounded-xl shadow-sm border border-orange-200 px-4 py-6 hover:shadow-md transition-all hover:scale-[1.02]">
				<div class="flex items-center justify-between mb-2">
					<p class="text-orange-700 text-xs font-medium">Average Revenue
						per Customer</p>
				</div>
				<p class="text-3xl font-bold text-orange-800">
					<span class="text-lg text-orange-600">Rs. </span>${averagePerCustomer != null ? averagePerCustomer : '0.00'}
				</p>
			</div>

		</div>
	</div>


	<!-- Inventory Alerts Section -->
	<div class="grid grid-cols-1 lg:grid-cols-2 gap-4 mb-8">

		<!-- Low Stock Alerts -->
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-4">
				<h3 class="text-lg font-semibold text-gray-800">Low Stock
					Alerts</h3>
				<div
					class="w-8 h-8 bg-yellow-100 rounded-lg flex items-center justify-center">
					<i class="fa-solid fa-exclamation-triangle text-yellow-600"></i>
				</div>
			</div>
			<div class="space-y-4">
				<c:choose>
					<c:when test="${not empty lowStockItems}">
						<div class="grid gap-3">
							<c:forEach var="item" items="${lowStockItems}">
								<div
									class="group relative bg-gradient-to-r from-amber-50 via-yellow-50 to-orange-50 rounded-xl border-l-4 border-amber-400 p-4 shadow-sm hover:shadow-md transition-all duration-200">
									<div class="flex items-center justify-between">
										<div class="flex-1">
											<div class="flex items-center gap-2 mb-1">
												<span
													class="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-amber-100 text-amber-800">
													ID: ${item.id} </span> <span
													class="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-gray-100 text-gray-700">
													${item.category.name} </span>
											</div>
											<h4 class="font-semibold text-gray-900 text-lg leading-tight">${item.name}</h4>
										</div>

										<div class="text-right ml-4">
											<div
												class="inline-flex items-center px-3 py-1 rounded-full bg-red-100 border border-red-200">
												<span class="w-2 h-2 bg-red-500 rounded-full mr-2"></span> <span
													class="text-sm font-bold text-red-700">${item.quantityAvailable}
													left</span>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<div
							class="text-center py-12 bg-gradient-to-br from-emerald-50 to-green-50 rounded-xl border border-emerald-200">
							<div
								class="inline-flex items-center justify-center w-16 h-16 bg-emerald-100 rounded-full mb-4">
								<div
									class="w-8 h-8 bg-emerald-500 rounded-full flex items-center justify-center">
									<i class="w-4 h-4 text-white fa-solid fa-check"></i>
								</div>
							</div>
							<h3 class="text-lg font-semibold text-emerald-800 mb-2">All
								Stock Levels Good</h3>
							<p class="text-emerald-600">All items are well stocked and
								available</p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<!-- Out of Stock Items -->
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-4">
				<h3 class="text-lg font-semibold text-gray-800">Out of Stock</h3>
				<div
					class="w-8 h-8 bg-red-100 rounded-lg flex items-center justify-center">
					<i class="fa-solid fa-times-circle text-red-600"></i>
				</div>
			</div>
			<div class="space-y-4">
				<c:choose>
					<c:when test="${not empty outOfStockItems}">
						<div class="grid gap-3">
							<c:forEach var="item" items="${outOfStockItems}">
								<div
									class="group relative bg-gradient-to-r from-red-50 via-rose-50 to-pink-50 rounded-xl border-l-4 border-red-500 p-4 shadow-sm hover:shadow-md transition-all duration-200">
									<div class="flex items-center justify-between">
										<div class="flex-1">
											<div class="flex items-center gap-2 mb-1">
												<span
													class="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-red-100 text-red-800">
													ID: ${item.id} </span> <span
													class="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-gray-100 text-gray-700">
													${item.category.name} </span>
											</div>
											<h4 class="font-semibold text-gray-900 text-lg leading-tight">${item.name}</h4>
										</div>

										<div class="text-right ml-4">
											<div
												class="inline-flex items-center px-3 py-1 rounded-full bg-red-500 border border-red-600">
												<i
													class="fa-solid fa-triangle-exclamation text-white text-xs mr-2"></i>
												<span class="text-sm font-bold text-white">Out of
													Stock</span>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<div
							class="text-center py-12 bg-gradient-to-br from-emerald-50 to-green-50 rounded-xl border border-emerald-200">
							<div
								class="inline-flex items-center justify-center w-16 h-16 bg-emerald-100 rounded-full mb-4">
								<div
									class="w-8 h-8 bg-emerald-500 rounded-full flex items-center justify-center">
									<i class="w-4 h-4 text-white fa-solid fa-check"></i>
								</div>
							</div>
							<h3 class="text-lg font-semibold text-emerald-800 mb-2">Inventory
								Status Excellent</h3>
							<p class="text-emerald-600">No items are currently out of
								stock</p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<!-- Sales Trends Chart -->
	<div class="mb-8">
		<div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
			<div class="flex items-center justify-between mb-6">
				<h3 class="text-lg font-semibold text-gray-800">Sales Trends
					(Monthly)</h3>
				<span class="text-sm text-gray-500 font-medium border-2 px-3 py-1 rounded-lg">This Year</span>
			</div>

			<!-- Chart Container -->
			<div class="relative h-64">
				<canvas id="salesChart" class="w-full h-full"></canvas>
			</div>
		</div>
	</div>
</div>

<!-- Chart.js Library -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>

<script>
// Month labels
const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

// Convert server-side list to JavaScript array
const monthlyRevenue = [
    <c:forEach var="rev" items="${monthlyRevenues}" varStatus="status">
        ${rev}<c:if test="${!status.last}">, </c:if>
    </c:forEach>
];

// Sales Chart Configuration
const ctx = document.getElementById('salesChart').getContext('2d');
const salesChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: months,
        datasets: [{
            label: 'Revenue (LKR)',
            data: monthlyRevenue,
            borderColor: '#3B82F6',
            backgroundColor: 'rgba(59, 130, 246, 0.1)',
            borderWidth: 3,
            fill: true,
            tension: 0.4,
            pointBackgroundColor: '#3B82F6',
            pointBorderColor: '#ffffff',
            pointBorderWidth: 2,
            pointRadius: 5,
            pointHoverRadius: 7
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
            y: {
                beginAtZero: true,
                grid: { color: '#F3F4F6' },
                ticks: { callback: value => `${value}` }
            },
            x: { grid: { display: false } }
        }
    }
});
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>