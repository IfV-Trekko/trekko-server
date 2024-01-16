package com.trekko.api.dtos;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trekko.api.models.User;
import com.trekko.api.utils.ObjectIdSerializer;

public class UserDto {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    private String email;
    private boolean emailConfirmed;
    private String profile;

    private Date createdAt;
    private Date updatedAt;

    public UserDto(final ObjectId id, final String email, final boolean emailConfirmed, final String profile,
            final Date createdAt,
            final Date updatedAt) {
        this.id = id;
        this.emailConfirmed = emailConfirmed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
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

    public static UserDto from(final User user) {
        return new UserDto(user.getId(), user.getEmail(), user.isEmailConfirmed(), user.getProfile(),
                user.getCreatedAt(), user.getUpdatedAt());
    }
}
