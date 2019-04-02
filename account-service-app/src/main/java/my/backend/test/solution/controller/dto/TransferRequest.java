package my.backend.test.solution.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    private String sourceNumber;
    private String targetNumber;
    private BigDecimal amount;


}
