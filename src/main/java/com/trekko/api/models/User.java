package com.trekko.api.models;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trekko.api.utils.ObjectIdSerializer;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.PrePersist;
import dev.morphia.annotations.Property;

@Entity("users")
public class User {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private final ObjectId id;
    private final String email;
    @JsonIgnore
    private final String passwordHash;

    private String profile;

    @Property("createdAt")
    private Date createdAt;
    @Property("updatedAt")
    private Date updatedAt;

    public User() {
        this.id = new ObjectId();
        this.email = null;
        this.passwordHash = null;
    }

    public User(final String email, final String passwordHash) {
        this.id = new ObjectId();
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setProfile(final String profile) {
        this.profile = profile;
    }

    @PrePersist
    protected void prePersist() {
        final Date now = new Date();

        if (this.createdAt == null) {
            this.createdAt = now;
        }

        this.updatedAt = now;
    }
}
