package com.generactive.storage;


import com.generactive.db.connection.DbConnection;
import com.generactive.model.GenerativeItem;
import com.generactive.model.Item;
import com.generactive.model.StockItem;
import com.generactive.model.enums.Complexity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepository implements CRUD<Item> {

    @Override
    public Item create(Item item) {

        String name = item.getName();
        String url = item.getUrl();
        double basePrice = item.getBasePrice();
        int groupId = item.getGroupId();
        Connection connection = DbConnection.get();
        Item savedItem = null;

        try {

            //Creating and initializing Prepared Statement
            String[] generatedColumns = {"id"}; //this to further get the id of the created item
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO item (name,url,base_price,group_id) values (?,?,?,?)", generatedColumns);
            statement.setString(1, name);
            statement.setString(2, url);
            statement.setDouble(3, basePrice);
            statement.setInt(4, groupId);

            //saving item to DB
            statement.executeUpdate();

            //getting the automatically generated id of the added item
            int savedItemID = -1;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                savedItemID = generatedKeys.getInt(1);
            }

            //Adding inherited type
            Statement stmt = connection.createStatement();
            if (item instanceof StockItem) {
                stmt.executeUpdate("INSERT  INTO stock_item values (" + savedItemID + ")");
                savedItem = new StockItem();
            } else if (item instanceof GenerativeItem) {
                Complexity complexity = ((GenerativeItem) item).getComplexity();
                String complex = complexity.toString().toUpperCase();
                stmt.executeUpdate("INSERT  INTO generative_item values (" + savedItemID + ",'" + complex + "')");
                savedItem = new GenerativeItem();
            }


            //Get saved item from DB to return to servlet response
            //In this method also trying to close connection
            initializeItemToReturn(savedItemID, connection, savedItem);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return savedItem;
    }

    @Override
    public Optional<Item> read(int id) {
        Connection connection = DbConnection.get();
        Item item = new Item();
        initializeItemToReturn(id, connection, item);

        return Optional.of(item);
    }


    @Override
    public Optional<Item> update(int id, Item item) {
        String name = item.getName();
        String url = item.getUrl();
        double basePrice = item.getBasePrice();
        int groupId = item.getGroupId();
        Connection connection = DbConnection.get();
        Item updatedItem = null;

        try {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE item SET name=?, url=?, base_price=?, group_id=? WHERE id=" + id);
            statement.setString(1, name);
            statement.setString(2, url);
            statement.setDouble(3, basePrice);
            statement.setInt(4, groupId);

            int rows = statement.executeUpdate();

            Statement stmt = connection.createStatement();
            if (item instanceof StockItem) {
                //Here there is nothing to update yet
                updatedItem = new StockItem();
            } else if (item instanceof GenerativeItem) {
                Complexity complexity = ((GenerativeItem) item).getComplexity();
                String complex = complexity.toString().toUpperCase();
                stmt.executeUpdate("UPDATE generative_item SET complexity= '" + complex + "' WHERE id=" + id);
                updatedItem = new GenerativeItem();
            }

            initializeItemToReturn(id, connection, updatedItem);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(updatedItem);
    }

    @Override
    public Integer delete(int id) {
        Connection connection = DbConnection.get();
        int rows = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM item WHERE id=?");
            statement.setInt(1, id);
            rows = statement.executeUpdate(); //This will also remove the child (CASCADE)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        Connection connection = DbConnection.get();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item " +
                    "LEFT JOIN generative_item gi on item.id = gi.id " +
                    "LEFT JOIN stock_item si on item.id = si.id");
            addItemsToListFromResultSet(list, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Item> allByPriceRange(double from, double to) {
        List<Item> list = new ArrayList<>();
        Connection connection = DbConnection.get();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item " +
                    "JOIN  generative_item gi ON item.id = gi.id " +
                    "AND item.base_price BETWEEN " + from + " AND " + to + " ORDER BY base_price");
            addItemsToListFromResultSet(list, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Optional<Item> getByName(String name) {
        Connection connection = DbConnection.get();
        Item item = new Item();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item  WHERE name = '" + name +"'");

            while (resultSet.next()) {
                item.setId(resultSet.getInt(1));
                item.setName(resultSet.getString(2));
                item.setUrl(resultSet.getString(3));
                item.setBasePrice(resultSet.getDouble(4));
                item.setGroupID(resultSet.getInt(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(item);
    }

    private void addItemsToListFromResultSet(List<Item> list, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String url = resultSet.getString(3);
            double basePrice = resultSet.getInt(4);
            int groupId = resultSet.getInt(5);
            String complexity = resultSet.getString(7);

            if (complexity != null) {
                list.add(new GenerativeItem(id, name, url, basePrice, groupId, complexity));
            } else {
                list.add(new StockItem(id, name, url, basePrice, groupId));
            }

        }
    }

    private void initializeItemToReturn(int id, Connection connection, Item item) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item " +
                    "JOIN (SELECT * FROM generative_item WHERE id =" + id + ") " +
                    "AS gi on item.id = gi.id");

            while (resultSet.next()) {
                item.setId(resultSet.getInt(1));
                item.setName(resultSet.getString(2));
                item.setUrl(resultSet.getString(3));
                item.setBasePrice(resultSet.getDouble(4));
                item.setGroupID(resultSet.getInt(5));
                if (item instanceof GenerativeItem) {
                    ((GenerativeItem) item).setComplexity(Complexity.valueOf(resultSet.getString(7)));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
