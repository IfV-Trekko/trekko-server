package com.trekko.api.repositories;

import dev.morphia.Datastore;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trekko.api.models.User;

@Repository
public class UserRepository {

    private final Datastore datastore;

    @Autowired
    public UserRepository(final Datastore datastore) {
        this.datastore = datastore;
    }

    public User saveUser(final User user) {
        return this.datastore.save(user);
    }

    public User findUserById(final ObjectId id) {
        return this.datastore.find(User.class).stream().filter(user -> user.getId().equals(id)).findFirst()
                .orElse(null);
    }

    public User findUserById(final String id) {
        return this.findUserById(new ObjectId(id));
    }

    public User findUserByEmail(final String email) {
        return this.datastore.find(User.class).stream().filter(user -> user.getEmail().equals(email)).findFirst()
                .orElse(null);
    }

    public void deleteUser(final User user) {
        this.datastore.delete(user);
    }

    public boolean existsByEmail(final String email) {
        return this.findUserByEmail(email) != null;
    }
}
