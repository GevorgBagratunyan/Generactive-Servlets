package com.generactive.servlets.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.Group;
import com.generactive.service.GroupService;
import com.generactive.config.ApplicationContainer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

@WebServlet(name = "groupSearchServlet", urlPatterns = "/groups/search")
public class GroupSearchServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final GroupService groupService = ApplicationContainer.context.getBean(GroupService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String id = req.getParameter("id");

        if(isNumeric(id)) {
            long iD = Long.parseLong(id);
            Optional<Group> optionalGroup = groupService.read(iD);
            if(optionalGroup.isPresent()) {
                resp.getWriter().write(MAPPER.writeValueAsString(optionalGroup.get()));
            } else resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Group with id: " + iD + " was not found");
        } else if(name!=null) {
            Optional<Group> optionalGroup = groupService.getByName(name);
            if(optionalGroup.isPresent()) {
                resp.getWriter().write(MAPPER.writeValueAsString(optionalGroup.get()));
            } else resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Group with name: " + name + " was not found");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect parameters");

    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(strNum).matches();
    }
}
