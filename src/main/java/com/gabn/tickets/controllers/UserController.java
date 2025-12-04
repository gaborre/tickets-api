package com.gabn.tickets.controllers;

import com.gabn.tickets.configs.ModelMapperConfig;
import com.gabn.tickets.controllers.docs.IUserDocs;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.UserDomain;
import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.PageResponseDTO;
import com.gabn.tickets.dtos.UserDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gabn.tickets.mappers.ResponseDTOMapper.buildPaginationDTO;
import static com.gabn.tickets.mappers.ResponseDTOMapper.toBaseResponseDTO;
import static com.gabn.tickets.mappers.ResponseDTOMapper.toPageResponseDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    path = "/api/v1/users",
    produces = APPLICATION_JSON_VALUE
)
public class UserController implements IUserDocs {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(
        consumes = APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<BaseResponseDTO<UserDTO>> createUser(
        @Valid @RequestBody UserDTO userDTO
    ) {
        UserDomain newUserDomain = userService.createUser(
            ModelMapperConfig.getInstance().map(userDTO, UserDomain.class)
        );
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .body(
                toBaseResponseDTO(
                    HttpStatus.CREATED, ModelMapperConfig.getInstance().map(newUserDomain, UserDTO.class)
                )
            );
    }

    @GetMapping(
        path = "/{id}"
    )
    @Override
    public ResponseEntity<BaseResponseDTO<UserDTO>> getUserById(
        @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        UserDomain userDomain = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toBaseResponseDTO(HttpStatus.OK, ModelMapperConfig.getInstance().map(userDomain, UserDTO.class))
            );
    }

    @GetMapping(
        path = "/uuid/{uuid}"
    )
    @Override
    public ResponseEntity<BaseResponseDTO<UserDTO>> getUserByUuid(
        @PathVariable(name = "uuid") String uuid
    ) throws EntityNotFoundException {
        UserDomain userDomain = userService.getUserByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toBaseResponseDTO(HttpStatus.OK, ModelMapperConfig.getInstance().map(userDomain, UserDTO.class))
            );
    }

    @GetMapping
    @Override
    public ResponseEntity<PageResponseDTO<List<UserDTO>>> getUsers(
        @Valid CriteriaDTO criteriaDTO
    ) throws EntityNotFoundException {
        Page<UserDomain> userDomainPage = userService.getUsers(
            ModelMapperConfig.getInstance().map(criteriaDTO, CriteriaDomain.class)
        );
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toPageResponseDTO(
                    HttpStatus.OK,
                    userDomainPage.getContent()
                        .stream()
                        .map((UserDomain userDomain) ->
                            ModelMapperConfig.getInstance().map(userDomain, UserDTO.class)
                        )
                        .toList(),
                    buildPaginationDTO(criteriaDTO, userDomainPage)
                )
            );
    }

    @PutMapping(
        path = "/{id}"
    )
    @Override
    public ResponseEntity<BaseResponseDTO<UserDTO>> updateUserById(
        @PathVariable(name = "id") Long id,
        @Valid @RequestBody UserDTO userDTO
    ) throws EntityNotFoundException {
        userDTO.setId(id);
        UserDomain userDomain = userService.updateUserById(
            ModelMapperConfig.getInstance().map(userDTO, UserDomain.class)
        );
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toBaseResponseDTO(HttpStatus.OK, ModelMapperConfig.getInstance().map(userDomain, UserDTO.class))
            );
    }
}
