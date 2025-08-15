<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css"
href="/bookshopManagement/assets/css/dashboard.css">

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

<div class="ml-64 flex-1 p-8">
    <!-- Welcome Section -->
    <div class="col-span-1 md:col-span-2 mb-8">
        <h1 class="text-3xl font-bold mb-2 pb-3">Welcome ${sessionScope.user.name}</h1>
    </div>

    <!-- Business Metrics Section -->
    <div class="mb-8">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            
            <!-- Total Revenue Card -->
            <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-gray-500 text-sm font-medium">Total Revenue</p>
                        <p class="text-3xl font-bold text-green-600">$${totalRevenue != null ? totalRevenue : '0.00'}</p>
                    </div>
                    <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                        <i class="fa-solid fa-dollar-sign text-green-600 text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Average Order Value Card -->
            <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-gray-500 text-sm font-medium">Average Order Value</p>
                        <p class="text-3xl font-bold text-blue-600">$${averageOrderValue != null ? averageOrderValue : '0.00'}</p>
                    </div>
                    <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                        <i class="fa-solid fa-shopping-cart text-blue-600 text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Active Customers Card -->
            <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-gray-500 text-sm font-medium">Active Customers</p>
                        <p class="text-3xl font-bold text-purple-600">${activeCustomers != null ? activeCustomers : '0'}</p>
                    </div>
                    <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
                        <i class="fa-solid fa-users text-purple-600 text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Average Revenue Per Customer Card -->
            <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-gray-500 text-sm font-medium">Avg Revenue/Customer</p>
                        <p class="text-3xl font-bold text-orange-600">$${averageRevenuePerCustomer != null ? averageRevenuePerCustomer : '0.00'}</p>
                    </div>
                    <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
                        <i class="fa-solid fa-user-dollar text-orange-600 text-xl"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Inventory Alerts Section -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
        
        <!-- Low Stock Alerts -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
            <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800">Low Stock Alerts</h3>
                <div class="w-8 h-8 bg-yellow-100 rounded-lg flex items-center justify-center">
                    <i class="fa-solid fa-exclamation-triangle text-yellow-600"></i>
                </div>
            </div>
            <div class="space-y-3 max-h-64 overflow-y-auto">
                <c:choose>
                    <c:when test="${not empty lowStockBooks}">
                        <c:forEach var="book" items="${lowStockBooks}">
                            <div class="flex items-center justify-between p-3 bg-yellow-50 rounded-lg border border-yellow-200">
                                <div>
                                    <p class="font-medium text-gray-800">${book.title}</p>
                                    <p class="text-sm text-gray-600">${book.author}</p>
                                </div>
                                <div class="text-right">
                                    <p class="text-sm font-semibold text-yellow-700">${book.stock} left</p>
                                    <p class="text-xs text-gray-500">Min: ${book.minStock}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-8">
                            <i class="fa-solid fa-check-circle text-green-500 text-2xl mb-2"></i>
                            <p class="text-gray-500">All items are well stocked</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Out of Stock Items -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
            <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800">Out of Stock</h3>
                <div class="w-8 h-8 bg-red-100 rounded-lg flex items-center justify-center">
                    <i class="fa-solid fa-times-circle text-red-600"></i>
                </div>
            </div>
            <div class="space-y-3 max-h-64 overflow-y-auto">
                <c:choose>
                    <c:when test="${not empty outOfStockBooks}">
                        <c:forEach var="book" items="${outOfStockBooks}">
                            <div class="flex items-center justify-between p-3 bg-red-50 rounded-lg border border-red-200">
                                <div>
                                    <p class="font-medium text-gray-800">${book.title}</p>
                                    <p class="text-sm text-gray-600">${book.author}</p>
                                </div>
                                <div class="text-right">
                                    <span class="px-2 py-1 bg-red-100 text-red-700 text-xs rounded-full font-medium">
                                        Out of Stock
                                    </span>
                                    <p class="text-xs text-gray-500 mt-1">Last sold: ${book.lastSoldDate}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-8">
                            <i class="fa-solid fa-check-circle text-green-500 text-2xl mb-2"></i>
                            <p class="text-gray-500">No items out of stock</p>
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
                <h3 class="text-lg font-semibold text-gray-800">Sales Trends</h3>
                <div class="flex space-x-2">
                    <button class="px-3 py-1 text-sm bg-blue-100 text-blue-600 rounded-lg font-medium">7 Days</button>
                    <button class="px-3 py-1 text-sm text-gray-600 hover:bg-gray-100 rounded-lg">30 Days</button>
                    <button class="px-3 py-1 text-sm text-gray-600 hover:bg-gray-100 rounded-lg">3 Months</button>
                </div>
            </div>
            
            <!-- Chart Container -->
            <div class="relative h-64">
                <canvas id="salesChart" class="w-full h-full"></canvas>
            </div>
            
            <!-- Chart Legend/Summary -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mt-6 pt-4 border-t border-gray-200">
                <div class="text-center">
                    <p class="text-2xl font-bold text-blue-600">${weeklyTotal != null ? weeklyTotal : '0'}</p>
                    <p class="text-sm text-gray-600">This Week</p>
                </div>
                <div class="text-center">
                    <p class="text-2xl font-bold text-green-600">${dailyAverage != null ? dailyAverage : '0'}</p>
                    <p class="text-sm text-gray-600">Daily Average</p>
                </div>
                <div class="text-center">
                    <p class="text-2xl font-bold text-purple-600">${bestDay != null ? bestDay : 'N/A'}</p>
                    <p class="text-sm text-gray-600">Best Day</p>
                </div>
            </div>
        </div>
    </div>    
</div>

<!-- Chart.js Library -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>

<script>
// Sales Chart Configuration
const ctx = document.getElementById('salesChart').getContext('2d');
const salesChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        datasets: [{
            label: 'Daily Sales ($)',
            data: [${salesData != null ? salesData : '120, 190, 300, 500, 200, 300, 450'}],
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
        plugins: {
            legend: {
                display: false
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                grid: {
                    color: '#F3F4F6'
                },
                ticks: {
                    callback: function(value) {
                        return ' + value;
                    }
                }
            },
            x: {
                grid: {
                    display: false
                }
            }
        },
        elements: {
            point: {
                hoverBackgroundColor: '#1D4ED8'
            }
        }
    }
});

// Time period button functionality
document.querySelectorAll('.bg-blue-100, .hover\\:bg-gray-100').forEach(button => {
    button.addEventListener('click', function() {
        // Remove active class from all buttons
        document.querySelectorAll('.bg-blue-100, .hover\\:bg-gray-100').forEach(btn => {
            btn.classList.remove('bg-blue-100', 'text-blue-600');
            btn.classList.add('text-gray-600', 'hover:bg-gray-100');
        });
        
        // Add active class to clicked button
        this.classList.remove('text-gray-600', 'hover:bg-gray-100');
        this.classList.add('bg-blue-100', 'text-blue-600');
        
        // Update chart data based on selection
        const period = this.textContent;
        updateChartData(period);
    });
});

function updateChartData(period) {
    // You can implement AJAX calls here to fetch data for different periods
    let newData, newLabels;
    
    switch(period) {
        case '7 Days':
            newLabels = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
            newData = [${salesData != null ? salesData : '120, 190, 300, 500, 200, 300, 450'}];
            break;
        case '30 Days':
            newLabels = ['Week 1', 'Week 2', 'Week 3', 'Week 4'];
            newData = [1200, 1500, 1800, 1600];
            break;
        case '3 Months':
            newLabels = ['Month 1', 'Month 2', 'Month 3'];
            newData = [5000, 6200, 5800];
            break;
    }
    
    salesChart.data.labels = newLabels;
    salesChart.data.datasets[0].data = newData;
    salesChart.update();
}
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>