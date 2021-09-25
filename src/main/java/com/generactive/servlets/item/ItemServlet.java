package com.generactive.servlets.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.GenerativeItem;
import com.generactive.model.StockItem;
import com.generactive.service.ItemService;
import com.generactive.config.ApplicationContainer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "itemServlet", urlPatterns = "/items")
public class ItemServlet extends HttpServlet {

    private ObjectMapper MAPPER = new ObjectMapper();
    private final ItemService itemService = ApplicationContainer.context.getBean(ItemService.class);


    //Get all Items http://localhost:8080/items
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write(MAPPER.writeValueAsString(itemService.getAll()));
    }

    //DONE
    //Create Item Based on JSON Post http://localhost:8080/items
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!req.getContentType().equals("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "not_supported_format");
        }
        String body = req.getReader().lines().collect(Collectors.joining());

        //If JSON contains complexity it means that we want to add GenerativeItem
        if (body.contains("complexity")) {
            GenerativeItem item = MAPPER.readValue(body, GenerativeItem.class);
            GenerativeItem saved = (GenerativeItem) itemService.create(item);
            resp.getWriter().write(MAPPER.writeValueAsString(saved));
        } else {
            StockItem item = MAPPER.readValue(body, StockItem.class);
            StockItem saved = (StockItem) itemService.create(item);
            resp.getWriter().write(MAPPER.writeValueAsString(saved));
        }

    }
}
