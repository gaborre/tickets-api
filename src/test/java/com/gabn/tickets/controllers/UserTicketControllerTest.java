package com.gabn.tickets.controllers;

import java.util.List;
import static java.util.List.of;
import java.util.Map;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gabn.tickets.TicketsApiApplication;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.security.JwtUtil;
import com.gabn.tickets.services.IUserTicketService;

@SpringBootTest(classes = TicketsApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserTicketControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserTicketService userTicketService;

    private static final String END_POINT_TEST = "/api/v1/users/{userId}/tickets";

    private String getJwt() {
        Map<String, Object> claims = Map.of("type", "tickets");
        return jwtUtil.doGenerateToken(claims, "subject", "audience");
    }

    @Test
    void test_GetTicketByUserIdAndStatus_ShouldBe_Ok_When_ThereAreTickets() throws Exception {
        CriteriaDTO criteriaDTO = new CriteriaDTO();
        criteriaDTO.setStatus("CREADO");
        criteriaDTO.setPage(1);
        criteriaDTO.setLimit(5);

        List<TicketDomain> ticketDomains = of(Instancio.create(TicketDomain.class));
        Page<TicketDomain> page = new PageImpl<>(ticketDomains);

        when(userTicketService.getUserTickets(any(CriteriaDomain.class))).thenReturn(page);

        mockMvc.perform(
                get(END_POINT_TEST, 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .param("status", criteriaDTO.getStatus())
                    .param("page", criteriaDTO.getPage().toString())
                    .param("limit", criteriaDTO.getLimit().toString())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray());

        verify(userTicketService, times(1)).getUserTickets(any(CriteriaDomain.class));
    }

    @Test
    void test_GetTicketByUserIdAndStatus_ShouldBe_BadRequest_When_PageIsLessThanMin() throws Exception {
        mockMvc.perform(
                get(END_POINT_TEST, 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .param("status", "CREADO")
                    .param("page", "0")
                    .param("limit", "5")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void test_GetTicketByUserIdAndStatus_ShouldBe_BadRequest_When_LimitIsGreaterThanMax() throws Exception {
        mockMvc.perform(
                get(END_POINT_TEST, 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .param("status", "CREADO")
                    .param("page", "1")
                    .param("limit", "25")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void test_GetTicketByUserIdAndStatus_ShouldBe_NotFound_When_ThereAreNoTickets() throws Exception {
        when(userTicketService.getUserTickets(any(CriteriaDomain.class)))
            .thenThrow(new EntityNotFoundException("Ticket", "Ticket no encontrado"));

        mockMvc.perform(
                get(END_POINT_TEST, 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .param("status", "CREADO")
                    .param("page", "1")
                    .param("limit", "5")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));

        verify(userTicketService, times(1)).getUserTickets(any(CriteriaDomain.class));
    }
}


