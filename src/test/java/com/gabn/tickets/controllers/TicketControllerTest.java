package com.gabn.tickets.controllers;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.List.of;
import java.util.Map;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gabn.tickets.TicketsApiApplication;
import com.gabn.tickets.configs.ObjectMapperConfig;
import com.gabn.tickets.domains.CreateTicketDomain;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.domains.UpdateTicketDomain;
import com.gabn.tickets.dtos.CreateTicketDTO;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.UpdateTicketDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.security.JwtUtil;
import com.gabn.tickets.services.ITicketService;

@SpringBootTest(classes = TicketsApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TicketControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ITicketService ticketService;

    private static final String END_POINT_TEST = "/api/v1/tickets";

    private String getJwt() {
        Map<String, Object> claims = Map.of("type", "tickets");
        return jwtUtil.doGenerateToken(claims, "subject", "audience");
    }

    @Test
    void test_CreateTicket_ShouldBe_Created_When_DataIsValid() throws Exception {
        CreateTicketDTO request = CreateTicketDTO.builder()
            .description("Soporte para error al iniciar sesión")
            .userId(1L)
            .status("CREADO")
            .build();

        TicketDomain ticketDomain = TicketDomain.builder()
            .id(1L)
            .uuid("uuid-ticket-1234")
            .description(request.getDescription())
            .createdOn(LocalDateTime.now())
            .build();

        when(ticketService.createTicket(any(CreateTicketDomain.class))).thenReturn(ticketDomain);

        mockMvc.perform(
                post(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(request)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value(201));

        verify(ticketService, times(1)).createTicket(any(CreateTicketDomain.class));
    }

    @Test
    void test_CreateTicket_ShouldBe_BadRequest_When_DescriptionIsBlank() throws Exception {
        CreateTicketDTO request = CreateTicketDTO.builder()
            .description(" ")
            .userId(1L)
            .status("CREADO")
            .build();

        mockMvc.perform(
                post(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(request)
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
    void test_CreateTicket_ShouldBe_BadRequest_When_StatusIsBlank() throws Exception {
        CreateTicketDTO request = CreateTicketDTO.builder()
            .description("Soporte para error al iniciar sesión")
            .userId(1L)
            .status(" ")
            .build();

        mockMvc.perform(
                post(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(request)
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
    void test_CreateTicket_ShouldBe_BadRequest_When_UserIdIsNull() throws Exception {
        CreateTicketDTO request = CreateTicketDTO.builder()
            .description("Soporte para error al iniciar sesión")
            .userId(null)
            .status("CREADO")
            .build();

        mockMvc.perform(
                post(END_POINT_TEST)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(request)
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
    void test_GetTicketById_ShouldBe_Ok_When_TicketExists() throws Exception {
        TicketDomain ticketDomain = Instancio.create(TicketDomain.class);

        when(ticketService.getTicketById(anyLong())).thenReturn(ticketDomain);

        mockMvc.perform(
                get(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));

        verify(ticketService, times(1)).getTicketById(anyLong());
    }

    @Test
    void test_GetTicketById_ShouldBe_NotFound_When_TicketDoesNotExist() throws Exception {
        when(ticketService.getTicketById(anyLong()))
            .thenThrow(new EntityNotFoundException("Ticket", "Ticket no encontrado"));

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

        verify(ticketService, times(1)).getTicketById(anyLong());
    }

    @Test
    void test_GetTicketByUuid_ShouldBe_Ok_When_TicketExists() throws Exception {
        TicketDomain ticketDomain = Instancio.create(TicketDomain.class);

        when(ticketService.getTicketByUuid(anyString())).thenReturn(ticketDomain);

        mockMvc.perform(
                get(END_POINT_TEST + "/uuid/{uuid}", "uuid-1234")
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));

        verify(ticketService, times(1)).getTicketByUuid(anyString());
    }

    @Test
    void test_GetTicketByUuid_ShouldBe_NotFound_When_TicketDoesNotExist() throws Exception {
        when(ticketService.getTicketByUuid(anyString()))
            .thenThrow(new EntityNotFoundException("Ticket", "Ticket no encontrado"));

        mockMvc.perform(
                get(END_POINT_TEST + "/uuid/{uuid}", "uuid-1234")
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));

        verify(ticketService, times(1)).getTicketByUuid(anyString());
    }

    @Test
    void test_GetTicketByStatus_ShouldBe_Ok_When_ThereAreTickets() throws Exception {
        CriteriaDTO criteriaDTO = new CriteriaDTO();
        criteriaDTO.setStatus("CREADO");
        criteriaDTO.setPage(1);
        criteriaDTO.setLimit(5);

        List<TicketDomain> ticketDomains = of(Instancio.create(TicketDomain.class));
        Page<TicketDomain> page = new PageImpl<>(ticketDomains);

        when(ticketService.getTicketByStatus(any(CriteriaDomain.class))).thenReturn(page);

        mockMvc.perform(
                get(END_POINT_TEST)
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

        verify(ticketService, times(1)).getTicketByStatus(any(CriteriaDomain.class));
    }

    @Test
    void test_GetTicketByStatus_ShouldBe_BadRequest_When_PageIsLessThanMin() throws Exception {
        mockMvc.perform(
                get(END_POINT_TEST)
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
    void test_GetTicketByStatus_ShouldBe_BadRequest_When_LimitIsGreaterThanMax() throws Exception {
        mockMvc.perform(
                get(END_POINT_TEST)
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
    void test_UpdateTicketById_ShouldBe_Ok_When_DataIsValid() throws Exception {
        UpdateTicketDTO updateTicketDTO = UpdateTicketDTO.builder()
            .description("Nueva descripción")
            .status("EN_PROCESO")
            .build();

        TicketDomain ticketDomain = Instancio.create(TicketDomain.class);

        when(ticketService.updateTicketById(any(UpdateTicketDomain.class))).thenReturn(ticketDomain);

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(updateTicketDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));

        verify(ticketService, times(1)).updateTicketById(any(UpdateTicketDomain.class));
    }

    @Test
    void test_UpdateTicketById_ShouldBe_BadRequest_When_DescriptionIsBlank() throws Exception {
        UpdateTicketDTO updateTicketDTO = UpdateTicketDTO.builder()
            .description(" ")
            .status("EN_PROCESO")
            .build();

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(updateTicketDTO)
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
    void test_UpdateTicketById_ShouldBe_BadRequest_When_StatusIsBlank() throws Exception {
        UpdateTicketDTO updateTicketDTO = UpdateTicketDTO.builder()
            .description("Nueva descripción")
            .status(" ")
            .build();

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(updateTicketDTO)
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
    void test_UpdateTicketById_ShouldBe_NotFound_When_TicketDoesNotExist() throws Exception {
        UpdateTicketDTO updateTicketDTO = UpdateTicketDTO.builder()
            .description("Nueva descripción")
            .status("EN_PROCESO")
            .build();

        when(ticketService.updateTicketById(any(UpdateTicketDomain.class)))
            .thenThrow(new EntityNotFoundException("Ticket", "Ticket no encontrado"));

        mockMvc.perform(
                put(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .content(
                        ObjectMapperConfig.getInstance().writeValueAsString(updateTicketDTO)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));

        verify(ticketService, times(1)).updateTicketById(any(UpdateTicketDomain.class));
    }

    @Test
    void test_DeleteTicketById_ShouldBe_Ok_When_TicketExists() throws Exception {
        TicketDomain ticketDomain = Instancio.create(TicketDomain.class);

        when(ticketService.deleteTicketById(anyLong())).thenReturn(ticketDomain);

        mockMvc.perform(
                delete(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));

        verify(ticketService, times(1)).deleteTicketById(anyLong());
    }

    @Test
    void test_DeleteTicketById_ShouldBe_NotFound_When_TicketDoesNotExist() throws Exception {
        doThrow(new EntityNotFoundException("Ticket", "Ticket no encontrado"))
            .when(ticketService).deleteTicketById(anyLong());

        mockMvc.perform(
                delete(END_POINT_TEST + "/{id}", 1L)
                    .header("Authorization", "Bearer " + getJwt())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));

        verify(ticketService, times(1)).deleteTicketById(anyLong());
    }
}


