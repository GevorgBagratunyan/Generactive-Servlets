package com.generactive.storage;

import com.generactive.db.connection.DbConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRepositoryTest {

    @BeforeAll
    public static void initDatabase() throws SQLException {
        Connection databaseConnection = DbConnection.get();
        Statement statement = databaseConnection.createStatement();
        statement.executeUpdate("CREATE sequence item_sequence    start with 1    increment by 1");
        statement.executeUpdate("CREATE sequence group_sequence    start with 1    increment by 1");
        statement.executeUpdate("CREATE TABLE group" +
                "(" +
                "    id          bigint primary key default nextval('group_sequence')," +
                "    name        varchar not null," +
                "    parent_id   bigint references group(id)" +
                "" +
                ")");
//        statement.executeUpdate("CREATE TABLE item" +
//                "(" +
//                "    id         bigint primary key default nextval('item_sequence')," +
//                "    name       varchar not null," +
//                "    url        varchar," +
//                "    base_price numeric             default 0.0," +
//                "    group_id   bigint references group (id)" +
//                ")");

        databaseConnection.close();
    }

    @Test
    public void testConnection() {
        assertDoesNotThrow(DbConnection::get);
    }

    @Test
    @DisplayName("Get an Item by ID")
    public void getItemById() {

    }
}
