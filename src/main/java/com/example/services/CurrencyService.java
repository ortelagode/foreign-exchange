package com.example.services;

import com.example.persistence.Conversion;
import com.example.enums.Currency;
import com.example.models.ConversionFilterBean;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface CurrencyService {

	public Double getExchangeRate(Currency fromCurrency, Currency toCurrency);
	public Conversion convert(BigDecimal sourceAmount, Currency sourceCurrency, Currency targetCurrency);
	public Page search(ConversionFilterBean conversionFilterBean, int page, int size);

}
