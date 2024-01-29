package com.trekko.api.models;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trekko.api.utils.JsonUtils;
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
    private final String passwordHash;

    private String emailConfirmationCode;
    private boolean emailConfirmed = false;

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

    @JsonIgnore
    public String getPasswordHash() {
        return this.passwordHash;
    }

    @JsonIgnore
    public String getEmailConfirmationCode() {
        return this.emailConfirmationCode;
    }

    public boolean isEmailConfirmed() {
        return this.emailConfirmed;
    }

    public String getProfile() {
        return this.profile;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setProfile(final String profile) {
        this.profile = profile;
    }

    public void updateProfile(final String updatedProfile) {
        final String currentProfile = this.getProfile();

        if (currentProfile == null) {
            this.setProfile(updatedProfile);
        } else {
            this.setProfile(JsonUtils.merge(currentProfile, updatedProfile));
        }
    }

    public void setEmailConfirmationCode(final String emailConfirmationCode) {
        this.emailConfirmationCode = emailConfirmationCode;
    }

    public void setEmailConfirmed(final boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
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
