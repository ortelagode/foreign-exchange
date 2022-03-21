package com.example.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConversionRequest extends ExchangeRateRequest {

    private BigDecimal sourceAmount;

}
