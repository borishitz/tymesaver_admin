package com.tracker.recordSearch.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String phone;
    @Email
    private String email;
    @Size(min = 6, message = "Minimum password lenght is 6 characters")
    private String password;
    private String confirmPassword;
}
