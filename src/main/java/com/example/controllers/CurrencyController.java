package com.example.controllers;

import com.example.persistence.Conversion;
import com.example.models.ConversionFilterBean;
import com.example.models.ConversionRequest;
import com.example.models.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.models.ExchangeRateRequest;
import com.example.services.CurrencyService;

@RestController
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;

	@PostMapping(value = "/exchangeRate")
	public Double getExchangeRate(@RequestBody ExchangeRateRequest exchangeRateRequest) throws Exception {

		 	return currencyService.getExchangeRate(exchangeRateRequest.getSourceCurrency(),
					exchangeRateRequest.getTargetCurrency());
	}

	@PostMapping(value = "/convert")
	public ConversionResponse convertValue(@RequestBody ConversionRequest conversionRequest) {

			Conversion conversion = currencyService.convert(conversionRequest.getSourceAmount(), conversionRequest.getSourceCurrency(), conversionRequest.getTargetCurrency());
			ConversionResponse response = new ConversionResponse();
			response.setId(conversion.getId());
			response.setAmount(conversion.getConvertedAmount());
			return response;
	}

	@GetMapping(value = "/search", params = { "page", "size" })
	public Page<Conversion> search(@RequestParam("page") int page,
								   @RequestParam("size") int size, @RequestBody ConversionFilterBean filterBean) throws Exception {

     return currencyService.search(filterBean, page, size);

	}
}
