package com.codewithanurag.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String address;
    private Long phoneNumber;
    private String panCard;
    private String firstName;
    private String lastName;
}
