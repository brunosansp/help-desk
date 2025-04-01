package br.com.brunosansp.userserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "UserController", description = "Controller responsible for user operations")
@RequestMapping("/api/users")
public interface IUserController {
    
    @Operation(summary = "Show all users")
    @ApiResponse(
        responseCode = "200", description = "Success",
        content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
        )
    )
    @ApiResponse(
        responseCode = "500", description = "Internal Server Error",
        content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = StandardError.class)
        )
    )
    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();
    
    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404",
            description = "User not found",
            content = @Content(
                mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
        ),
        @ApiResponse(responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
        )
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
        @Parameter(description = "User id", required = true, example = "66b168c5d0cce9437645cbe2")
        @PathVariable(name = "id") final String id
    );
    
    @Operation(summary = "Save nw user")
    @ApiResponse(responseCode = "201", description = "User created")
    @ApiResponse(
        responseCode = "400", description = "Bad request",
        content = @Content(
            mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class)
        )
    )
    @ApiResponse(
        responseCode = "500", description = "Internal Server Error",
        content = @Content(
            mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class)
        )
    )
    @PostMapping
    ResponseEntity<Void> save(
        @Valid @RequestBody final CreateUserRequest createUserRequest
    );
}
