package com.codewithanurag.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String role;
}
