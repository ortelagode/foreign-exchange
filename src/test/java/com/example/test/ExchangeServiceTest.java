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
import org.apache.http.client.CredentialsProvider;
import org.jets3t.service.Jets3tProperties;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.security.AWSCredentials;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServletLoader.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExchangeServiceTest {

	@Autowired
	ConversionRepository conversionRepository;

	@Autowired
	CurrencyService currencyService;


	DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:0.11.3");

	@Rule
	public LocalStackContainer localstack = new LocalStackContainer(localstackImage)
			.withServices(S3);

	@Before
	public void before() throws S3ServiceException {
     RestS3Service s3Service = new RestS3Service(new AWSCredentials(localstack.getAccessKey(), localstack.getSecretKey()));
		Jets3tProperties jets3 = s3Service.getJetS3tProperties();
		jets3.setProperty("s3service.s3-endpoint", localstack.getEndpointOverride(S3).getHost());
		jets3.setProperty("s3service.https-only", "false");
		jets3.setProperty("s3service.s3-endpoint-http-port", String.valueOf(localstack.getEndpointOverride(S3).getPort()));

		CredentialsProvider credentials = s3Service.getCredentialsProvider();
		s3Service = new RestS3Service(new AWSCredentials(localstack.getAccessKey(), localstack.getSecretKey()), "test", credentials, jets3);

		S3Bucket[] myBuckets = s3Service.listAllBuckets();


	}
	@Test
	public void getExchangeRate_success() throws S3ServiceException {
		//Double exchangeRate = currencyService.getExchangeRate(Currency.ALL, Currency.USD);

		//assertNotNull(exchangeRate);
		localstack.start();
		RestS3Service s3Service = new RestS3Service(new AWSCredentials(localstack.getAccessKey(), localstack.getSecretKey()));

		Jets3tProperties jets3 = s3Service.getJetS3tProperties();
		//jets3.setProperty("httpclient.proxy-autodetect", "false");
		//jets3.setProperty("httpclient.proxy-host", String.valueOf(localstack.getEndpointOverride(S3).getHost()));
		//jets3.setProperty("httpclient.proxy-port", String.valueOf(localstack.getEndpointOverride(S3).getPort()));

		jets3.setProperty("s3service.s3-endpoint", "localhost");
		jets3.setProperty("s3service.https-only", "false");
		jets3.setProperty("s3service.s3-endpoint-http-port", String.valueOf(localstack.getEndpointOverride(S3).getPort()));


		CredentialsProvider credentials = s3Service.getCredentialsProvider();
		s3Service = new RestS3Service(new AWSCredentials(localstack.getAccessKey(), localstack.getSecretKey()), "test", credentials, jets3);
		s3Service.createBucket("test");
		//S3Bucket[] myBuckets = s3Service.listAllBuckets();

//		S3Client s3 = S3Client
//				.builder()
//				.endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.S3))
//				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
//						localstack.getAccessKey(), localstack.getSecretKey()
//				)))
//				.region(Region.of(localstack.getRegion()))
//				.build();
//
//		s3.createBucket(b -> b.bucket("foo"));
//		s3.putObject(b -> b.bucket("foo").key("bar"), RequestBody.fromBytes("baz".getBytes()));
//		ListBucketsResponse buckets = s3.listBuckets();
//		System.out.println("TEST " + buckets.buckets());
	}

//	@Test
//	public void getExchangeRate_wrongAccessKey() {
//		ReflectionTestUtils.setField(currencyService, "accessKey", "efsefwefwr");
//		ExchangeApiException exception = (ExchangeApiException) catchThrowable(() -> {
//			currencyService.getExchangeRate(Currency.ALL, Currency.USD);
//		});
//		assertThat(exception).isInstanceOf(Exception.class).hasMessageContaining("You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]");
//	}
//
//	@Test
//	public void convert_success() {
//		List<Conversion> conversionListBeforeSave = conversionRepository.findAll();
//		Conversion conversion = currencyService.convert(new BigDecimal(123.6), Currency.ALL, Currency.USD);
//		List<Conversion> conversionListAfterSave = conversionRepository.findAll();
//        assertThat(conversionListAfterSave.size() - conversionListBeforeSave.size()).isEqualTo(1);
//		assertThat(conversion).isNotNull();
//	}
//
//	@Test
//	public void search_success() {
//		Conversion conversion = currencyService.convert(new BigDecimal(123.6), Currency.ALL, Currency.USD);
//		ConversionFilterBean filterBean = new ConversionFilterBean();
//		filterBean.setDate(new Date());
//		Page<Conversion> conversionListAfterSave = currencyService.search(filterBean, 0,2);
//		assertThat(conversionListAfterSave.getTotalElements()).isEqualTo(1);
//	}

}
