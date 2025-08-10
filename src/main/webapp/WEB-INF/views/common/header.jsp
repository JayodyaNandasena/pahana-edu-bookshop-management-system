<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.net.URLDecoder"%>
<%
String toastType = null;
String toastMessage = null;

if (request.getCookies() != null) {
    for (Cookie cookie : request.getCookies()) {
        if ("toastType".equals(cookie.getName())) {
            toastType = URLDecoder.decode(cookie.getValue(), "UTF-8");
        }
        if ("toastMessage".equals(cookie.getName())) {
            toastMessage = URLDecoder.decode(cookie.getValue(), "UTF-8");
        }
    }
}

// Escape message for safe JavaScript usage
String escapedMessage = null;
if (toastMessage != null) {
    escapedMessage = toastMessage
        .replace("\\", "\\\\")   // escape backslashes
        .replace("\"", "\\\"")   // escape double quotes
        .replace("'", "\\'")     // escape single quotes
        .replace("\n", "\\n")    // escape newlines
        .replace("\r", "\\r");   // escape carriage returns
}
%>

<c:set var="isAdmin" value="${sessionScope.user.userType == 'ADMIN'}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Pahana Edu</title>

<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/bookshopManagement/assets/css/styles.css">

<!-- Include Toastr -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

<%
if (toastType != null && escapedMessage != null) {
%>
<script>
    $(function() {
        toastr.options = {
            closeButton: true,
            progressBar: true,
            positionClass: "toast-bottom-right",
            timeOut: "5000"
        };
        toastr["<%= toastType %>"]("<%= escapedMessage %>");
    });
</script>
<%
    // Clear cookies after displaying the toast
    Cookie clearType = new Cookie("toastType", "");
    clearType.setPath("/");
    clearType.setMaxAge(0);
    response.addCookie(clearType);

    Cookie clearMessage = new Cookie("toastMessage", "");
    clearMessage.setPath("/");
    clearMessage.setMaxAge(0);
    response.addCookie(clearMessage);
}
%>

</head>

<body class="bg-gray-50 min-h-screen">