package com.codewithanurag.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String panCard;
}
