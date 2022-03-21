package com.example.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.example.persistence.Conversion;
import com.example.models.ConversionFilterBean;
import com.example.models.ExchangeRateErrorResponse;
import com.example.persistence.ConversionRepository;
import com.example.util.exceptions.ExchangeApiException;
import com.example.util.exceptions.ServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.enums.Currency;
import com.example.models.ExchangeRateResponse;
import com.example.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	private static final String SUCCESS = "success";
	ObjectMapper objectMapper;

	@Value("${currency.url}")
	private String currencyUrl;

	@Value("${access.key}")
	private String accessKey;

	@Autowired
	ConversionRepository conversionRepository;

	@Autowired
	 SearchSpecificationService searchSpecificationService;

	public CurrencyServiceImpl(){
		objectMapper = new ObjectMapper();
	}

	@Override
	public Double getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
		CloseableHttpClient httpClientBuilder = HttpClients.createDefault();
		String url = currencyUrl + accessKey;
		HttpGet httpGetExchangeRate = new HttpGet(url);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			HttpResponse response = httpClientBuilder.execute(httpGetExchangeRate);
			String responseBodyString = Util.processHttpResponse(response);

			JsonNode jsonResponseBody = objectMapper.readValue(responseBodyString, JsonNode.class);

			if(!jsonResponseBody.get(SUCCESS).asBoolean()){
				ExchangeRateErrorResponse errorResponseBody = objectMapper.readValue(responseBodyString, ExchangeRateErrorResponse.class);
               throw new ExchangeApiException(errorResponseBody.getError().getCode(), errorResponseBody.getError().getInfo());
			}
			ExchangeRateResponse responseBody = objectMapper.readValue(responseBodyString, ExchangeRateResponse.class);
			Map<Currency, Double> rates = responseBody.getRates();
			return rates.get(targetCurrency) / rates.get(sourceCurrency);

		} catch (IOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Conversion convert(BigDecimal sourceAmount, Currency sourceCurrency, Currency targetCurrency) {
		try {
			Conversion conversion = new Conversion();
			conversion.setSourceCurrency(sourceCurrency);
			conversion.setTargetCurrency(targetCurrency);
			conversion.setSourceAmount(sourceAmount);
			conversion.setConvertedAmount(sourceAmount.multiply(new BigDecimal(getExchangeRate(sourceCurrency, targetCurrency))));

			return conversionRepository.save(conversion);
		} catch(Exception e){
          throw new ServiceException("Error on saving the transaction");
		}
	}

	@Override
	public Page<Conversion> search(ConversionFilterBean conversionFilterBean, int page, int size) {
		try {
			return conversionRepository.findAll(searchSpecificationService.search(conversionFilterBean),
					PageRequest.of(page, size));
		}catch(Exception e){
			throw new ServiceException("Error on filtering transactions");
		}
	}
}
