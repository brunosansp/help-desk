package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With
public record CreateUserRequest(
    
    @Schema(description = "User name", example = "Bruno Santos")
    @NotBlank(message = "'name' cannot be empty")
    @Size(min = 3, max = 50, message = "'name' must contain between 3 and 50 characters")
    String name,
    
    @Email(message = "Invalid email")
    @Schema(description = "User email", example = "bruno@email.com")
    @Size(min = 10, max = 50, message = "'email' must contain between 10 and 50 characters")
    @NotBlank(message = "'email' cannot be empty")
    String email,
    
    @Schema(description = "User password", example = "123456")
    @NotBlank(message = "'password' cannot be empty")
    @Size(min = 6, max = 50, message = "'password' must contain between 6 and 50 characters")
    String password,
    
    @Schema(description = "User profiles", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER\", \"ROLE_TECHNICIAN\"]")
    Set<ProfileEnum> profiles
) {
}
