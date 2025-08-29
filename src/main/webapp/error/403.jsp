<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html class="h-full">
<head>
<title>Page Not Found</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/styles.css">
</head>
<body class="h-full">
	<main
		class="grid min-h-full place-items-center px-6 py-24 sm:py-32 lg:px-8">
		<div class="text-center bg-white py-12 px-20 rounded-lg shadow-lg">
			<p class="text-base font-semibold text-indigo-600">403</p>
			<h1
				class="mt-4 text-5xl font-semibold tracking-tight text-balance text-gray-900 sm:text-7xl">Access
				Denied</h1>
			<p
				class="mt-6 text-lg font-medium text-pretty text-gray-500 sm:text-xl/8">Sorry,
				You do not have permission to access this page.</p>
			<div class="mt-10 flex items-center justify-center gap-x-6">
				<a href="<c:url value='/dashboard' />"
					class="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Go
					back to dashboard</a> <a href="<c:url value='/user-guide' />"
					class="text-sm font-semibold text-gray-900">Contact support <span
					aria-hidden="true">&rarr;</span>
				</a>
			</div>
		</div>
	</main>
</body>
</html>
