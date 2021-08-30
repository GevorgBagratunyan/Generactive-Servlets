package com.generactive.servlets.loginlogout;

import com.generactive.storage.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        UserRepository repository = new UserRepository();
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (repository.userIsExist(username, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("isLoggedIn", username);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            res.sendRedirect("/hello-servlet");
        } else {
            res.sendRedirect("/login");
        }
    }
}
