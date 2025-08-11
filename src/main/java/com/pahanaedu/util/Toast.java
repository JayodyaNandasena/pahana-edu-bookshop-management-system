package com.pahanaedu.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Toast {
	public static void setToastCookie(HttpServletResponse response, String type, String message) {
		try {
			String safeMessage = message.replace("\n", " ").replace("\r", " ");
			Cookie typeCookie = new Cookie("toastType", URLEncoder.encode(type, StandardCharsets.UTF_8.toString()));
			Cookie messageCookie = new Cookie("toastMessage",
					URLEncoder.encode(safeMessage, StandardCharsets.UTF_8.toString()));

			typeCookie.setMaxAge(5);
			messageCookie.setMaxAge(5);
			typeCookie.setPath("/");
			messageCookie.setPath("/");

			response.addCookie(typeCookie);
			response.addCookie(messageCookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
