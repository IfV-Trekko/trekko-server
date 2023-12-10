package com.trekko.api.models;

import java.util.Date;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.PrePersist;
import dev.morphia.annotations.Property;
import jakarta.validation.constraints.Email;

@Entity("users")
public class User {

  @Id
  private final ObjectId id;

  @Email(message = "Email should be valid")
  private final String email;

  private final String passwordHash;

  @Property("createdAt")
  private Date createdAt;
  @Property("updatedAt")
  private Date updatedAt;

  public User(final String email, final String passwordHash) {
    this.id = new ObjectId();
    this.email = email;
    this.passwordHash = passwordHash;
  }

  @PrePersist
  protected void prePersist() {
    final Date now = new Date();

    if (createdAt == null) {
      createdAt = now;
    }

    updatedAt = now;
  }
}
