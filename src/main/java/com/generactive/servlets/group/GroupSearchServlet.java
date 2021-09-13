package com.generactive.servlets.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.Group;
import com.generactive.repository.GroupRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

@WebServlet(name = "groupSearchServlet", value = "groups/search")
public class GroupSearchServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final GroupRepository GROUP_REPOSITORY = new GroupRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String id = req.getParameter("id");

        if(isNumeric(id)) {
            long ID = Long.parseLong(id);
            Optional<Group> optionalGroup = GROUP_REPOSITORY.read(ID);
            if(optionalGroup.isPresent()) {
                resp.getWriter().write(MAPPER.writeValueAsString(optionalGroup.get()));
            } else resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Group with ID: " + ID + " was not found");
        } else if(name!=null) {
            Optional<Group> optionalGroup = GROUP_REPOSITORY.getByName(name);
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
