package com.gabn.tickets.services;

import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.UserDomain;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;

public interface IUserService {

    UserDomain createUser(UserDomain userDomain);
    
    UserDomain getUserById(Long id) throws EntityNotFoundException;

    UserDomain getUserByUuid(String uuid) throws EntityNotFoundException;

    Page<UserDomain> getUsers(CriteriaDomain criteriaDomain) throws EntityNotFoundException;

    UserDomain updateUserById(UserDomain userDomain) throws EntityNotFoundException;
}
