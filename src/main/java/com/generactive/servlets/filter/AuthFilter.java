package com.generactive.servlets.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "authFilter", value = "/*")
public class AuthFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        boolean isLoggedIn = req.getSession().getAttribute("isLoggedIn") != null;

        //For logged users checking if they have cookie attributes for authentication
        if (isLoggedIn) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect("/login");
        }
    }
}
