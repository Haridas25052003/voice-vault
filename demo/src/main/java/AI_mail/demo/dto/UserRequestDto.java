package AI_mail.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserRequestDto {

    @NotNull
    private String name;

    @Email
    private String email;
}