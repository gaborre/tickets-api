package com.gabn.tickets.controllers.admin;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.JwtRequestDTO;
import com.gabn.tickets.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.gabn.tickets.mappers.ResponseDTOMapper.toBaseResponseDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    path = "/api/v1/admin",
    produces = APPLICATION_JSON_VALUE
)
public class AdminController {
    private final JwtUtil jwtUtil;

    public AdminController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(
        path = "/get-valid-jwt",
        consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BaseResponseDTO<String>> getValidJwt(@Valid @RequestBody JwtRequestDTO jwtRequestDTO) {
        Map<String, Object> claims = Map.of("type", "tickets");
        String jwt = jwtUtil.doGenerateToken(claims, jwtRequestDTO.getSubject(), jwtRequestDTO.getAudience());
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .body(toBaseResponseDTO(HttpStatus.CREATED, jwt));
    }
}
