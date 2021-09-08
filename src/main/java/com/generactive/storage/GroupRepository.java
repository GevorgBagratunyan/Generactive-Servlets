package com.generactive.storage;


import com.generactive.db.connection.DbConnection;
import com.generactive.model.Group;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GroupRepository implements CRUD<Group> {

    @Override
    public Group create(Group group) {
        return null;
    }

    @Override
    public Optional<Group> read(int id) {
        return null;
    }

    //This will be implemented soon, after realizing REST requests
    @Override
    public Optional<Group> update(int id, Group group) {
        return null;
    }

    @Override
    public Integer delete(int id) {
        return 0;
    }

    public List<Group> getAll() {
        List<Group> list = new ArrayList<>();
        Connection connection = DbConnection.get();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"group\" ");
            while (resultSet.next()) {
                Group group = new Group();

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int parentId = resultSet.getInt(3);

                group.setId(id);
                group.setName(name);
                group.setParentId(parentId);

                list.add(group);
            }

            //Collecting subgroups by looping on groups and checking their parent IDs
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (j != i) {
                        if (list.get(i).getId() == list.get(j).getParentId()) {
                            list.get(i).addSubGroup(list.get(j));
                        }
                    }
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
        return list;
    }

    //In this method return is not Optional, for testing purposes
    public Group getByName(String name) throws NoSuchElementException {
        return null;
    }

    public List<Group> getSubgroupsByParent(Group parent) {
        return null;
    }

}
