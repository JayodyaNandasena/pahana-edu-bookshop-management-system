package com.pahanaedu.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {
       
    public AuthFilter() {
        super();
    }

	public void destroy() {
	}

	@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getServletPath();

        // Allow public resources without auth
        if (path.equals("/") || path.equals("/index.jsp") || path.equals("/login") || path.startsWith("/assets/")) {
            chain.doFilter(req, res);
            return;
        }

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (!loggedIn) {
            // Redirect to login page
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Prevent caching of protected pages to avoid access after logout
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        // Proceed with the request
        chain.doFilter(req, res);
    }

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
