package com.gabn.tickets.mappers;

import com.gabn.tickets.domains.UserDomain;
import com.gabn.tickets.models.User;

public final class UserMapper {

    private UserMapper() {}

    public static User mapUserDomainToUser(UserDomain userDomain) {
        return User.builder()
            .id(userDomain.getId())
            .uuid(userDomain.getUuid())
            .firstName(userDomain.getFirstName())
            .lastName(userDomain.getLastName())
            .createdOn(userDomain.getCreatedOn())
            .updatedOn(userDomain.getUpdatedOn())
            .build();
    }
}
