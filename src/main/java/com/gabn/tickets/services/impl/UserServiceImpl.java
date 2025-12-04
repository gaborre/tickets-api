package com.gabn.tickets.services.impl;

import com.gabn.tickets.configs.ModelMapperConfig;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.UserDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.models.User;
import com.gabn.tickets.repositories.UserRepository;
import com.gabn.tickets.services.IUserService;

import static com.gabn.tickets.constants.UserValidationMessage.USER_ENTITY_TYPE;
import static com.gabn.tickets.constants.UserValidationMessage.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDomain createUser(UserDomain userDomain) {
        return ModelMapperConfig.getInstance().map(
            userRepository.save(ModelMapperConfig.getInstance().map(userDomain, User.class)),
            UserDomain.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserDomain getUserById(Long id) throws EntityNotFoundException {
        return ModelMapperConfig.getInstance().map(
            userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_ENTITY_TYPE, USER_NOT_FOUND)),
            UserDomain.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserDomain getUserByUuid(String uuid) throws EntityNotFoundException {
        return ModelMapperConfig.getInstance().map(
            userRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(USER_ENTITY_TYPE, USER_NOT_FOUND)),
            UserDomain.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDomain> getUsers(CriteriaDomain criteriaDomain) throws EntityNotFoundException {
        Page<User> userPage = userRepository.findAll(
            PageRequest.of(criteriaDomain.getPage() - 1, criteriaDomain.getLimit())
        );
        if (userPage.isEmpty()) {
            throw new EntityNotFoundException(USER_ENTITY_TYPE, USER_NOT_FOUND);
        }

        return userPage
            .map((User user) -> ModelMapperConfig.getInstance().map(user, UserDomain.class));
    }

    @Override
    @Transactional
    public UserDomain updateUserById(UserDomain userDomain) throws EntityNotFoundException {
        User existingUser = userRepository.findById(userDomain.getId())
            .orElseThrow(() -> new EntityNotFoundException(USER_ENTITY_TYPE, USER_NOT_FOUND));
        
        existingUser.setFirstName(userDomain.getFirstName());
        existingUser.setLastName(userDomain.getLastName());
        
        return ModelMapperConfig.getInstance().map(
            userRepository.save(existingUser), UserDomain.class
        );
    }

}
