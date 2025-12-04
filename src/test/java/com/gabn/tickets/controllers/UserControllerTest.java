package com.gabn.tickets.controllers;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.List.of;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gabn.tickets.TicketsApiApplication;
import com.gabn.tickets.configs.ObjectMapperConfig;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.UserDomain;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.UserDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.security.JwtUtil;
import com.gabn.tickets.services.IUserService;

@SpringBootTest(classes = TicketsApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    private static final String END_POINT_TEST = "/api/v1/users";

    private String getJwt() {
        Map<String, Object> claims = Map.of("type", "tickets");
        return jwtUtil.doGenerateToken(claims, "subject", "audience");
    }

    @Test
    void test_CreateUser_ShouldBe_Created_When_DataIsValid() throws Exception {
        UserDTO userDTO = UserDTO.builder()
            .firstName("Alfred")
            .lastName("Guttemberg")
            .build();

        UserDomain userDomain = UserDomain.builder()
            .id(1L)
            .uuid("uuid-1234-5678")
            .firstName("Alfred")
            .lastName("Guttemberg")
            .createdOn(LocalDateTime.parse("2025-12-04T05:07:36.068512"))
            .build();

        when(userService.createUser(any(UserDomain.class)))
            .thenReturn(userDomain);

        mockMvc.perform(
                post(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(userDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value(201));

        verify(userService, times(1)).createUser(any(UserDomain.class));
    }

    @Test
    void test_GetUserById_ShouldBe_Ok_When_UserExists() throws Exception {
        UserDomain userDomain = UserDomain.builder()
            .id(1L)
            .uuid("uuid-1234-5678")
            .firstName("Alfred")
            .lastName("Guttemberg")
            .createdOn(LocalDateTime.now())
            .build();

        when(userService.getUserById(anyLong())).thenReturn(userDomain);

        mockMvc.perform(
                get(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void test_GetUserById_ShouldBe_NotFound_When_UserDoesNotExist() throws Exception {
        when(userService.getUserById(anyLong()))
            .thenThrow(new EntityNotFoundException("User", "User not found"));

        mockMvc.perform(
                get(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404))
            .andExpect(jsonPath("$.error.type").exists())
            .andExpect(jsonPath("$.error.description").exists());

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void test_GetUserByUuid_ShouldBe_Ok_When_UserExists() throws Exception {
        UserDomain userDomain = UserDomain.builder()
            .id(1L)
            .uuid("uuid-1234-5678")
            .firstName("Alfred")
            .lastName("Guttemberg")
            .createdOn(LocalDateTime.now())
            .build();

        when(userService.getUserByUuid(anyString())).thenReturn(userDomain);

        mockMvc.perform(
                get(END_POINT_TEST + "/uuid/{uuid}", "uuid-1234-5678")
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));

        verify(userService, times(1)).getUserByUuid(anyString());
    }

    @Test
    void test_GetUserByUuid_ShouldBe_NotFound_When_UserDoesNotExist() throws Exception {
        when(userService.getUserByUuid(anyString()))
            .thenThrow(new EntityNotFoundException("User", "User not found"));

        mockMvc.perform(
                get(END_POINT_TEST + "/uuid/{uuid}", "uuid-1234-5678")
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));

        verify(userService, times(1)).getUserByUuid(anyString());
    }

    @Test
    void test_GetUsers_ShouldBe_Ok_When_ThereAreUsers() throws Exception {
        CriteriaDTO criteriaDTO = new CriteriaDTO();
        criteriaDTO.setPage(1);
        criteriaDTO.setLimit(5);

        List<UserDomain> userDomains = of(
            UserDomain.builder()
                .id(1L)
                .uuid("uuid-1")
                .firstName("Alfred")
                .lastName("Guttemberg")
                .createdOn(LocalDateTime.now())
                .build()
        );
        Page<UserDomain> page = new PageImpl<>(userDomains);

        when(userService.getUsers(any(CriteriaDomain.class))).thenReturn(page);

        mockMvc.perform(
                get(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .param("page", criteriaDTO.getPage().toString())
                    .param("limit", criteriaDTO.getLimit().toString())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray());

        verify(userService, times(1)).getUsers(any(CriteriaDomain.class));
    }

    @Test
    void test_GetUsers_ShouldBe_BadRequest_When_PageIsLessThanMin() throws Exception {
        mockMvc.perform(
                get(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .param("page", "0")
                    .param("limit", "5")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void test_GetUsers_ShouldBe_BadRequest_When_LimitIsGreaterThanMax() throws Exception {
        mockMvc.perform(
                get(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .param("page", "1")
                    .param("limit", "25")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void test_UpdateUserById_ShouldBe_Ok_When_DataIsValid() throws Exception {
        UserDTO userDTO = UserDTO.builder()
            .firstName("Alfred")
            .lastName("Guttemberg")
            .build();

        UserDomain userDomain = UserDomain.builder()
            .id(1L)
            .uuid("uuid-1234-5678")
            .firstName("Alfred")
            .lastName("Guttemberg")
            .createdOn(LocalDateTime.now())
            .build();

        when(userService.updateUserById(any(UserDomain.class))).thenReturn(userDomain);

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(userDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));

        verify(userService, times(1)).updateUserById(any(UserDomain.class));
    }

    @Test
    void test_UpdateUserById_ShouldBe_BadRequest_When_FirstNameIsTooShort() throws Exception {
        UserDTO userDTO = UserDTO.builder()
            .firstName("Al")
            .lastName("Guttemberg")
            .build();

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(userDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void test_UpdateUserById_ShouldBe_BadRequest_When_LastNameIsBlank() throws Exception {
        UserDTO userDTO = UserDTO.builder()
            .firstName("Alfred")
            .lastName(" ")
            .build();

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(userDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void test_UpdateUserById_ShouldBe_NotFound_When_UserDoesNotExist() throws Exception {
        UserDTO userDTO = UserDTO.builder()
            .firstName("Alfred")
            .lastName("Guttemberg")
            .build();

        when(userService.updateUserById(any(UserDomain.class)))
            .thenThrow(new EntityNotFoundException("User", "User not found"));

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(userDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));

        verify(userService, times(1)).updateUserById(any(UserDomain.class));
    }
}