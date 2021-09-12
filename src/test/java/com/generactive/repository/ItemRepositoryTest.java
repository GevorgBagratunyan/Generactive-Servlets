package com.generactive.repository;

import com.generactive.model.GenerativeItem;
import com.generactive.model.Group;
import com.generactive.model.Item;
import com.generactive.model.enums.Complexity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRepositoryTest {

    private static final ItemRepository REPOSITORY = new ItemRepository();

    @BeforeAll
    public static void initDatabase() throws SQLException {
//        Connection databaseConnection = DbConnection.get();
//        Statement statement = databaseConnection.createStatement();
//        statement.executeUpdate("CREATE TABLE \"group\"" +
//                "(" +
//                "    id          bigint auto_increment primary key ," +
//                "    name        varchar not null," +
//                "    parent_id   bigint references \"group\"(id)" +
//                "" +
//                ");");
//        statement.executeUpdate("INSERT INTO \"group\"(name) values ('Nature')");
//        statement.executeUpdate("INSERT INTO \"group\"(name,parent_id) values ('forest',1)");
//        statement.executeUpdate("INSERT INTO \"group\"(name,parent_id) values ('Landscape',1)");
//        statement.executeUpdate("CREATE TABLE item" +
//                "(" +
//                "    id         bigint auto_increment primary key," +
//                "    name       varchar not null," +
//                "    url        varchar," +
//                "    base_price numeric             default 0.0," +
//                "    group_id   bigint references \"group\"(id)" +
//                ")");
//        statement.executeUpdate("CREATE TABLE stock_item" +
//                "(" +
//                "    id         bigint primary key references item(id) ON DELETE CASCADE" +
//                ");");
//        statement.executeUpdate("CREATE TABLE generative_item" +
//                "(" +
//                "    id         bigint primary key references item(id) ON DELETE CASCADE ," +
//                "    complexity varchar check ( complexity in ('ONE', 'TWO') )\n" +
//                ");");
//        statement.executeUpdate("INSERT INTO item(name, url, base_price, group_id) values ('TestItem', 'generactive.com/items',112.4,2)");
//        statement.executeUpdate("INSERT INTO generative_item(id, complexity) values (1,'TWO')");
//
//        databaseConnection.close();
    }

    @Test
    @DisplayName("Create Item")
    public void create() {
        Item item = new GenerativeItem(Complexity.TWO);
        Item fetched = REPOSITORY.create(item);
        assertNotNull(fetched);
        assertEquals(2, fetched.getId());
        assertTrue(item instanceof GenerativeItem);
    }

    @Test
    @DisplayName("Get Item By ID")
    public void getById() {
        Optional<Item> optionalItem = REPOSITORY.read(1);
        assertTrue(optionalItem.isPresent());
        assertEquals(1, optionalItem.get().getId());
    }

    @Test
    @DisplayName("Delete Item By ID")
    public void deleteById() {
        Optional<Item> optionalItem = REPOSITORY.delete(1);
        assertTrue(optionalItem.isPresent());
    }

    @Test
    @DisplayName("Update Item")
    public void update() {
        Item item = new GenerativeItem(Complexity.ONE);
        Optional<Item> updatedOptionalItem = REPOSITORY.update(item);
        assertTrue(updatedOptionalItem.isPresent());
        Item updatedItem = updatedOptionalItem.get();
        assertEquals("ONE", ((GenerativeItem) updatedItem).getComplexity().toString());
        assertTrue(updatedItem instanceof GenerativeItem);
    }

    @Test
    @DisplayName("Get All Items")
    public void getAll() {
        List<Item> list = REPOSITORY.getAll();
        assertTrue(list.size() > 0);
        assertEquals("TestItem", list.get(0).getName());
        assertTrue(list.get(0) instanceof GenerativeItem);
    }

    @Test
    @DisplayName("Get Items in Given Price Range")
    public void getItemsListByGivenPriceRange() {
        List<Item> list = REPOSITORY.allByPriceRange(100.0, 1200.0);
        assertTrue(list.size() > 0);
        assertEquals("TestItem", list.get(0).getName());
        assertTrue(list.get(0) instanceof GenerativeItem);
        assertTrue(list.get(0).getBasePrice() < 1200.0 && list.get(0).getBasePrice() > 100.0);
    }

}
