package com.scm.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "username is required")
    @Size(min = 3, message = "Min 3 characters is required")
    private String name;
    @Email(message = "Invalid Email Address")
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 6, message = "Min 6 characters required!")
    private String password;
    @NotBlank(message = "About is required")
    private String about;
    @Size(min = 10, max = 10, message = "Invalid mobile number")
    private String phone;
}
