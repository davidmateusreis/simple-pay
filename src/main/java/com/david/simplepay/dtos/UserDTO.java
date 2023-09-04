package com.david.simplepay.dtos;

import java.math.BigDecimal;

import com.david.simplepay.entities.UserType;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email,
        String password, UserType userType) {

}
