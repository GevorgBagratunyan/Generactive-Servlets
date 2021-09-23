package com.generactive.servlets.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generactive.model.GenerativeItem;
import com.generactive.model.Item;
import com.generactive.model.StockItem;
import com.generactive.service.ItemService;
import com.generactive.util.ApplicationContainer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@WebServlet(name = "itemSearchServlet", urlPatterns = "/items/*")
public class ItemSearchServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ItemService ITEM_SERVICE = ApplicationContainer.context.getBean(ItemService.class);

    //Get item if passing Numeric path http://localhost:8080/items/{id},
    //or SEARCH item when passing parametrized path http://localhost:8080/items/search?priceFrom=0&priceTo=20
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo().substring(1); //Removing slash "/" from path info.
        String priceFrom = req.getParameter("priceFrom");
        String priceTo = req.getParameter("priceTo");
        String name = req.getParameter("name");

        if (pathInfo.startsWith("search")) {
            if (priceFrom != null && priceTo != null) {
                double from = Double.parseDouble(priceFrom);
                double to = Double.parseDouble(priceTo);
                resp.getWriter().write(MAPPER.writeValueAsString(ITEM_SERVICE.allByPriceRange(from, to)));
            } else if (name != null) {
                Optional<Item> optionalItem = ITEM_SERVICE.getByName(name);
                Item item;
                if (optionalItem.isPresent()) {
                    item = optionalItem.get();
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No such Item");
                    return;
                }
                resp.getWriter().write(MAPPER.writeValueAsString(item));
            }
        }  else if (isNumeric(pathInfo)) {
            long id = Long.parseLong(pathInfo);
            Optional<Item> optionalItem = ITEM_SERVICE.read(id);
            if (optionalItem.isPresent()) {
                resp.getWriter().write(MAPPER.writeValueAsString(optionalItem.get()));
            }
        } else if (pathInfo.isEmpty()) {
            resp.getWriter().write(MAPPER.writeValueAsString(ITEM_SERVICE.getAll()));
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request, check URL");

    }

    //Update item with given JSON protocol fields http://localhost:8080/items/{id}
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo().substring(1);
        if (isNumeric(pathInfo)) {
            int id = Integer.parseInt(pathInfo);
            String body = req.getReader().lines().collect(Collectors.joining());

            if (body.contains("complexity")) {
                GenerativeItem item = MAPPER.readValue(body, GenerativeItem.class);
                GenerativeItem updated = (GenerativeItem) ITEM_SERVICE.update(item).get();
                resp.getWriter().write(MAPPER.writeValueAsString(updated));
            } else {
                StockItem item = MAPPER.readValue(body, StockItem.class);
                StockItem updated = (StockItem) ITEM_SERVICE.update(item).get();
                resp.getWriter().write(MAPPER.writeValueAsString(updated));
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");

    }

    //Delete Item using {id} in URL path info (not parameter) http://localhost:8080/items/{id},
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo().substring(1);
        if (isNumeric(pathInfo)) {
            long id = Long.parseLong(pathInfo);
            Optional<Item> optionalItem = ITEM_SERVICE.delete(id);
            if (!optionalItem.isPresent()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
            } else resp.getWriter().write("Item  with ID " + pathInfo + " is removed successfully\n"
                    + MAPPER.writeValueAsString(optionalItem.get()));
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(strNum).matches();
    }
}
