package com.example.models;

import java.util.Date;
import java.util.Map;

import com.example.enums.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRateResponse {

	private boolean success;
	private long timestamp;
	private Currency base;
	private Date date;
	private Map<Currency, Double> rates;

}
