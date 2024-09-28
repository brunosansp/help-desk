package br.com.brunosansp.userserviceapi.controller;

import br.com.brunosansp.userserviceapi.service.UserService;
import entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "UserController", description = "Controller responsible for user operations")
@RestController
@RequestMapping("/api")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.findAll();
    }
    
    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404",
            description = "User not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StandardError.class))
        ),
        @ApiResponse(responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StandardError.class))
        )
    })
    @GetMapping("user/{id}")
    public ResponseEntity<UserResponse> byId(
        @Parameter(description = "User id", required = true, example = "66b168c5d0cce9437645cbe2")
        @PathVariable String id) {
        UserResponse user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
