package com.generactive.storage;

import com.generactive.db.connection.DbConnection;
import com.generactive.model.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRepositoryTest {

    @BeforeAll
    public static void initDatabase() throws SQLException {
        Connection databaseConnection = DbConnection.get();
        Statement statement = databaseConnection.createStatement();
        statement.executeUpdate("CREATE sequence item_sequence    start with 1    increment by 1");
        statement.executeUpdate("CREATE sequence group_sequence    start with 1    increment by 1");
        statement.executeUpdate("CREATE TABLE \"group\" (id bigint default nextval('group_sequence'), name varchar not null, parent_id bigint references \"group\"(id))");
        statement.executeUpdate("CREATE TABLE item (id bigint default nextval('item_sequence'),name varchar not null, url varchar, base_price numeric default 0.0, group_id bigint references \"group\"(id))");
        statement.executeUpdate("CREATE TABLE stock_item (id bigint primary key references item(id) ON DELETE CASCADE)");
        statement.executeUpdate("CREATE TABLE generative_item\n" +
                "(\n" +
                "    id         bigint primary key references item(id) ON DELETE CASCADE ,\n" +
                "    complexity varchar check ( complexity in ('ONE', 'TWO') )\n" +
                ")");

        databaseConnection.close();
    }

    @Test
    @DisplayName("Check not null connection")
    public void testConnection() {
        assertDoesNotThrow(DbConnection::get);
    }

    @Test
    @DisplayName("Get an Item by ID")
    public void getItemById() {
        ItemRepository repository = new ItemRepository();
        repository.create(new Item(12, "Test", "URL", 22.6, 3));
        Optional<Item> item = repository.read(1);
        System.out.println(item.get().getName());
        assertTrue(item.isPresent());
        assertEquals(1, item.get().getId());
    }
}
