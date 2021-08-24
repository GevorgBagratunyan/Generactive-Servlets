package com.generactive.servlets.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.Configuration;
import com.generactive.model.Item;
import com.generactive.storage.ItemRepository;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "itemServlet", value = "/items")
public class ItemServletNoParams extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ItemRepository ITEM_REPOSITORY = new ItemRepository();

    @Override
    public void init() {
        Item simpleItem = new Item(1, "SimpleItem", "google.com", 20.0, null) {
            @Override
            public double calculatePrice(Configuration configuration) {
                return 0;
            }
        };
        ITEM_REPOSITORY.create(simpleItem);
    }

    //Get all Items http://localhost:8080/items
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write(MAPPER.writeValueAsString(ITEM_REPOSITORY.all()));
    }

    //Create Item Based on JSON Post http://localhost:8080/items
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!req.getContentType().equals("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "not_supported_format");
        }
        String body = req.getReader().lines().collect(Collectors.joining());
        Item item = MAPPER.readValue(body, Item.class);
        Item saved = ITEM_REPOSITORY.create(item);
        resp.getWriter().write(MAPPER.writeValueAsString(saved));
    }
}
