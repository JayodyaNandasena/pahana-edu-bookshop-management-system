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

        // Allow public resources (index, login, assets, etc.)
        if (path.equals("/index.jsp") || path.equals("/") || path.startsWith("/assets/") || path.equals("/login")) {
            chain.doFilter(req, res);
            return;
        }

        // Check authentication
        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (!loggedIn) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Continue request
        chain.doFilter(req, res);
    }

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
