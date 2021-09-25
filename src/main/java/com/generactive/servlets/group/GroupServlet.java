package com.generactive.servlets.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.Group;
import com.generactive.service.GroupService;
import com.generactive.config.ApplicationContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "groupServlet", urlPatterns = "/groups")
public class GroupServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final GroupService groupService = ApplicationContainer.context.getBean(GroupService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write(MAPPER.writeValueAsString(groupService.getAll()));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!req.getContentType().equals("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "not_supported_format");
        }
        String body = req.getReader().lines().collect(Collectors.joining());
        Group group = MAPPER.readValue(body, Group.class);
        Group saved = groupService.create(group);
        resp.getWriter().write(MAPPER.writeValueAsString(saved));
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
