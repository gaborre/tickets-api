package com.gabn.tickets.services.impl;

import org.instancio.Instancio;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gabn.tickets.domains.UserDomain;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.models.User;
import com.gabn.tickets.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void test_CreateUser_ShouldBe_Created_When_DataIsValid() {

        when(userRepository.save(any(User.class)))
            .thenReturn(Instancio.create(User.class));

        UserDomain userDomain = Instancio.create(UserDomain.class);

        assertAll(
            () -> userService.createUser(userDomain)
        );

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void test_GetUserById_ShouldReturn_User_When_Exists() throws EntityNotFoundException {
        User user = Instancio.create(User.class);

        when(userRepository.findById(1L))
            .thenReturn(java.util.Optional.of(user));

        UserDomain result = userService.getUserById(1L);

        assertAll(
            () -> assertEquals(user.getId(), result.getId())
        );

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void test_GetUserById_ShouldThrow_EntityNotFoundException_When_NotExists() {
        when(userRepository.findById(1L))
            .thenReturn(java.util.Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> userService.getUserById(1L)
        );

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void test_GetUserByUuid_ShouldReturn_User_When_Exists() throws EntityNotFoundException {
        User user = Instancio.create(User.class);

        when(userRepository.findByUuid("uuid-1234"))
            .thenReturn(java.util.Optional.of(user));

        UserDomain result = userService.getUserByUuid("uuid-1234");

        assertAll(
            () -> assertEquals(user.getUuid(), result.getUuid())
        );

        verify(userRepository, times(1)).findByUuid("uuid-1234");
    }

    @Test
    void test_GetUserByUuid_ShouldThrow_EntityNotFoundException_When_NotExists() {
        when(userRepository.findByUuid("uuid-1234"))
            .thenReturn(java.util.Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> userService.getUserByUuid("uuid-1234")
        );

        verify(userRepository, times(1)).findByUuid("uuid-1234");
    }

    @Test
    void test_GetUsers_ShouldReturn_Page_When_HasData() throws EntityNotFoundException {
        org.springframework.data.domain.Page<User> userPage =
            new org.springframework.data.domain.PageImpl<>(java.util.List.of(Instancio.create(User.class)));

        when(userRepository.findAll(any(org.springframework.data.domain.Pageable.class)))
            .thenReturn(userPage);

        com.gabn.tickets.domains.CriteriaDomain criteriaDomain = com.gabn.tickets.domains.CriteriaDomain.builder()
            .page(1)
            .limit(5)
            .build();

        assertAll(
            () -> userService.getUsers(criteriaDomain)
        );

        verify(userRepository, times(1)).findAll(any(org.springframework.data.domain.Pageable.class));
    }

    @Test
    void test_GetUsers_ShouldThrow_EntityNotFoundException_When_EmptyPage() {
        org.springframework.data.domain.Page<User> userPage =
            new org.springframework.data.domain.PageImpl<>(java.util.List.of());

        when(userRepository.findAll(any(org.springframework.data.domain.Pageable.class)))
            .thenReturn(userPage);

        com.gabn.tickets.domains.CriteriaDomain criteriaDomain = com.gabn.tickets.domains.CriteriaDomain.builder()
            .page(1)
            .limit(5)
            .build();

        assertThrows(
            EntityNotFoundException.class,
            () -> userService.getUsers(criteriaDomain)
        );

        verify(userRepository, times(1)).findAll(any(org.springframework.data.domain.Pageable.class));
    }

    @Test
    void test_UpdateUserById_ShouldBe_Ok_When_UserExists() throws EntityNotFoundException {
        User existingUser = Instancio.create(User.class);

        when(userRepository.findById(1L))
            .thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(User.class)))
            .thenReturn(existingUser);

        UserDomain userDomain = Instancio.create(UserDomain.class);
        userDomain.setId(1L);

        assertAll(
            () -> userService.updateUserById(userDomain)
        );

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void test_UpdateUserById_ShouldThrow_EntityNotFoundException_When_UserDoesNotExist() {
        when(userRepository.findById(1L))
            .thenReturn(java.util.Optional.empty());

        UserDomain userDomain = Instancio.create(UserDomain.class);
        userDomain.setId(1L);

        assertThrows(
            EntityNotFoundException.class,
            () -> userService.updateUserById(userDomain)
        );

        verify(userRepository, times(1)).findById(1L);
    }
}
