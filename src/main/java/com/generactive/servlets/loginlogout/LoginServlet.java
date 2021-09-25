package com.generactive.servlets.loginlogout;

import com.generactive.model.enums.Role;
import com.generactive.repository.UserRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final UserRepository repository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (repository.userIsExist(username, password)) {
            HttpSession session = req.getSession();
            Role role = repository.getUserByLoginAndPassword(username,password).get().getRole();
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setAttribute("role", role);
            res.getWriter().write("Login SUCCESS!");
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "wrong username/password");
        }
    }
}
