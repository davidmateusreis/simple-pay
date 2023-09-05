package com.david.simplepay.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long sender, Long receiver) {

}
