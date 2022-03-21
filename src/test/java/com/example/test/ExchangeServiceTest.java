package com.example.test;

import com.example.app.ServletLoader;
import com.example.enums.Currency;
import com.example.models.ConversionFilterBean;
import com.example.persistence.Conversion;
import com.example.persistence.ConversionRepository;
import com.example.services.CurrencyService;
import com.example.services.CurrencyServiceImpl;
import com.example.services.SearchSpecificationService;
import com.example.util.exceptions.ExchangeApiException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServletLoader.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExchangeServiceTest {

	@Autowired
	ConversionRepository conversionRepository;

	@Autowired
	CurrencyService currencyService;


	@Test
	public void getExchangeRate_success() {
		Double exchangeRate = currencyService.getExchangeRate(Currency.ALL, Currency.USD);
		assertNotNull(exchangeRate);
	}

	@Test
	public void getExchangeRate_wrongAccessKey() {
		ReflectionTestUtils.setField(currencyService, "accessKey", "efsefwefwr");
		ExchangeApiException exception = (ExchangeApiException) catchThrowable(() -> {
			currencyService.getExchangeRate(Currency.ALL, Currency.USD);
		});
		assertThat(exception).isInstanceOf(Exception.class).hasMessageContaining("You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]");
	}

	@Test
	public void convert_success() {
		List<Conversion> conversionListBeforeSave = conversionRepository.findAll();
		Conversion conversion = currencyService.convert(new BigDecimal(123.6), Currency.ALL, Currency.USD);
		List<Conversion> conversionListAfterSave = conversionRepository.findAll();
        assertThat(conversionListAfterSave.size() - conversionListBeforeSave.size()).isEqualTo(1);
		assertThat(conversion).isNotNull();
	}

	@Test
	public void search_success() {
		Conversion conversion = currencyService.convert(new BigDecimal(123.6), Currency.ALL, Currency.USD);
		ConversionFilterBean filterBean = new ConversionFilterBean();
		filterBean.setDate(new Date());
		Page<Conversion> conversionListAfterSave = currencyService.search(filterBean, 0,2);
		assertThat(conversionListAfterSave.getTotalElements()).isEqualTo(1);
	}

}
