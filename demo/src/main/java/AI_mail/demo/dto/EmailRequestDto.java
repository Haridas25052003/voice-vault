package AI_mail.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class EmailRequestDto {

    @NotNull(message = "Company name is required")
    private String company;

    @NotNull(message = "Role is required")
    private String role;

    @Email(message = "Invalid email format")
    private String userEmail;
}