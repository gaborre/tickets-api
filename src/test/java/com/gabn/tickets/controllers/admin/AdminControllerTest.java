package com.gabn.tickets.controllers.admin;

import com.gabn.tickets.TicketsApiApplication;
import com.gabn.tickets.configs.ObjectMapperConfig;
import com.gabn.tickets.dtos.JwtRequestDTO;
import com.gabn.tickets.security.JwtUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TicketsApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AdminControllerTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    private static final String END_POINT_TEST = "/api/v1/admin/get-valid-jwt";

    @Test
    void test_GetValidJwt_ShouldBe_Ok_When_DataIsComplete() throws Exception {
        JwtRequestDTO jwtRequestDTO = Instancio.create(JwtRequestDTO.class);

        mockMvc.perform(
                post(END_POINT_TEST)
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(jwtRequestDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value(201));
    }
}