package com.gabn.tickets.security;

import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;

@Getter
public class SessionUser extends User {
    @Serial
    private static final long serialVersionUID = 2669173972148657636L;

    public SessionUser(
        String username,
        String password
    ) {
        super(username, password, AuthorityUtils.NO_AUTHORITIES);
    }

}
