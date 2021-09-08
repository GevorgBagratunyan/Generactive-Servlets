package com.generactive.servlets.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.storage.GroupRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "groupServlet", value = "/groups")
public class GroupServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final GroupRepository GROUP_REPOSITORY = new GroupRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(MAPPER.writeValueAsString(GROUP_REPOSITORY.getAll()));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}