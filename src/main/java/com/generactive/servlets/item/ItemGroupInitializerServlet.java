package com.generactive.servlets.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.Item;
import com.generactive.service.ItemService;
import com.generactive.util.ApplicationContainer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

@WebServlet(name = "itemGroupInitializer", urlPatterns = "/items/group")
public class ItemGroupInitializerServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ItemService ITEM_SERVICE = ApplicationContainer.context.getBean(ItemService.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String itemId = req.getParameter("itemId");
        String groupId = req.getParameter("groupId");
        long itId = -1;
        long grId = -1;

       Item item;
        if(isNumeric(itemId) && isNumeric(groupId)) {
            itId = Long.parseLong(itemId);
            grId = Long.parseLong(groupId);
            Optional<Item> optionalItem = ITEM_SERVICE.setGroup(itId,grId);
            if(optionalItem.isPresent()) {
                item = optionalItem.get();
                resp.getWriter().write(MAPPER.writeValueAsString(item));
            } else resp.sendError(HttpServletResponse.SC_NOT_FOUND, "item not found");
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
