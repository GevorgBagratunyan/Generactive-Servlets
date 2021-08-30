package com.generactive.servlets.filter;

import com.generactive.model.User;
import com.generactive.model.enums.Role;
import com.generactive.storage.UserRepository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.nonNull;

@WebFilter(filterName = "authFilter", value = "/auth")
public class AuthFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException {

        UserRepository repository = new UserRepository();
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<User> user = repository.getUserByLoginAndPassword(username, password);

        //For logged users checking if they have cookie attributes for authentication
        if (nonNull(session.getAttribute("username")) && nonNull(session.getAttribute("password"))) {
            Role role = (Role) session.getAttribute("role");
            res.getWriter().write("You are already logged in as : " + role);
            openMenuFor(role, req, res);
        } else if (user.isPresent()) { //If the session is new and has no attributes  for authentication

            Role role = user.get().getRole();
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setAttribute("role", role);
            openMenuFor(role, req, res);
        } else {
            openMenuFor(Role.GUEST, req, res);
        }
    }

    private void openMenuFor(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.getWriter().write("You entered web page as : " + role);
        //Granting access to some resources dependant of role passed through the method
        //It's for indicative purposes only

    }
}
