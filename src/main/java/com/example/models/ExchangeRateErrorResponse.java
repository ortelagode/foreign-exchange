package com.example.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExchangeRateErrorResponse {
    private boolean success;
    private Error error;
}
