package com.example.models;

import com.example.enums.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRateRequest {

	private Currency sourceCurrency;
	private Currency targetCurrency;

}
