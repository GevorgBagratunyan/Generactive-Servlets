package com.generactive.servlets.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.Group;
import com.generactive.repository.GroupRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

@WebServlet(name = "groupParentInitializer", value = "/groups/parent")
public class GroupParentInitializerServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final GroupRepository GROUP_REPOSITORY = new GroupRepository();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String groupId = req.getParameter("groupId");
        String parentId = req.getParameter("parentId");
        long grId = -1;
        long parId = -1;

        if(isNumeric(groupId) && isNumeric(parentId)) {
            grId = Long.parseLong(groupId);
            parId = Long.parseLong(parentId);
            Optional<Group> optionalGroup = GROUP_REPOSITORY.setParent(grId,parId);
            if(optionalGroup.isPresent()) {
                Group group = optionalGroup.get();
                resp.getWriter().write(MAPPER.writeValueAsString(group));
            } else resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Group not found");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "incorrect parameters");
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(strNum).matches();
    }
}
