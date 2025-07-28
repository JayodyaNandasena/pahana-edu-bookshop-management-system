<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<div class="sidebar fixed left-0 top-0 h-full w-64 shadow-lg z-50">
    <!-- Logo -->
    <div class="p-6 border-b border-gray-200">
        <div class="flex items-center space-x-2">
            <img src="/bookshopManagement/assets/images/logo.png" alt="Pahana Edu" class="h-12 w-auto">
        </div>
    </div>

    <!-- Navigation -->
    <nav class="mt-6">
        <ul class="space-y-2 px-4">
            <li>
                <a href="<c:url value='/dashboard' />"
                   class="${activePage == 'dashboard' ? 'sidebar-active shadow-sm' : 'hover:bg-gray-300 hover:text-gray-900'} flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600">
                    <i class="fa-solid fa-house fa-fw text-base"></i>
                    <span>Dashboard</span>
                </a>
            </li>

            <li>
                <a href="<c:url value='/bill' />"
                   class="${activePage == 'bill' ? 'sidebar-active shadow-sm' : 'hover:bg-gray-300 hover:text-gray-900'} flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600">
                    <i class="fa-solid fa-file-invoice-dollar fa-fw text-base"></i>
                    <span>Create Bill</span>
                </a>
            </li>

            <li>
                <a href="<c:url value='/customers' />"
                   class="${activePage == 'customers' ? 'sidebar-active shadow-sm' : 'hover:bg-gray-300 hover:text-gray-900'} flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600">
                    <i class="fa-solid fa-users fa-fw text-base"></i>
                    <span>Manage Customers</span>
                </a>
            </li>

            <li>
                <a href="<c:url value='/inventory' />"
                   class="${activePage == 'inventory' ? 'sidebar-active shadow-sm' : 'hover:bg-gray-300 hover:text-gray-900'} flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600">
                    <i class="fa-solid fa-magnifying-glass fa-fw text-base"></i>
                    <span>Search Inventory</span>
                </a>
            </li>

            <li>
                <a href="<c:url value='/user-guide' />"
                   class="${activePage == 'user-guide' ? 'sidebar-active shadow-sm' : 'hover:bg-gray-300 hover:text-gray-900'} flex items-center space-x-3 px-4 py-3 rounded-lg text-gray-600">
                    <i class="fa-solid fa-circle-question fa-fw text-base"></i>
                    <span>User Guide</span>
                </a>
            </li>
        </ul>
    </nav>

    <!-- Logout -->
    <div class="absolute bottom-6 left-6">
        <button class="flex items-center space-x-2 text-gray-600 hover:text-red-600 transition-colors">
            <i class="fa-solid fa-right-from-bracket fa-fw"></i>
            <span>Logout</span>
        </button>
    </div>
</div>