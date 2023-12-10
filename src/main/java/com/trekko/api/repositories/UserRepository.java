package com.trekko.api.repositories;

import dev.morphia.Datastore;
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
    return datastore.save(user);
  }
}
