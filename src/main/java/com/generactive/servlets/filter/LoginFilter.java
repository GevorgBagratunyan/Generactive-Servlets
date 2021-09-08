package com.generactive.servlets.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authFilter", value = "/auth/*")
public class LoginFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        boolean isLoggedIn = req.getSession().getAttribute("isLoggedIn") != null;
        String logInURI = req.getContextPath() + "/login";
        String currentReq = req.getRequestURI();
        boolean loginRequest = currentReq.equals(logInURI);

        if (isLoggedIn || loginRequest ) {
            chain.doFilter(req, res);
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access is denied");
        }
    }
}
