package com.generactive.servlets.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.GenerativeItem;
import com.generactive.model.StockItem;
import com.generactive.repository.ItemRepository;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "itemServlet", value = "/items")
public class ItemServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ItemRepository ITEM_REPOSITORY = new ItemRepository();


    //Get all Items http://localhost:8080/items
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write(MAPPER.writeValueAsString(ITEM_REPOSITORY.getAll()));
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
            GenerativeItem saved = (GenerativeItem) ITEM_REPOSITORY.create(item);
            resp.getWriter().write(MAPPER.writeValueAsString(saved));
        } else {
            StockItem item = MAPPER.readValue(body, StockItem.class);
            StockItem saved = (StockItem) ITEM_REPOSITORY.create(item);
            resp.getWriter().write(MAPPER.writeValueAsString(saved));
        }

    }
}
