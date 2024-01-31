package com.inditex.zarachallenge.config;

import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.netty.MockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.inditex.zarachallenge.infrastructure.KafkaListener;
import com.inditex.zarachallenge.infrastructure.model.ProductAvailabilityEvent;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Configuration
@EnableWebMvc
public class ZaraInitialConfig implements CommandLineRunner {
	@Autowired
	KafkaListener kafka;

	@Value("classpath:events.csv")
	Resource resourceFile;

	MockServer mockServer;

	MockServerClient mockServerClient;

	public void startMockServer() {
		mockServer = new MockServer(3000);
		mockServerClient = new MockServerClient("localhost", 3000);
	}

	@Override
	public void run(String... args) throws Exception {
		Consumer<Message<ProductAvailabilityEvent>> consumer = kafka.KafkaConsumer();
		try {
			List<ProductAvailabilityEvent> stocks = Files
					.readAllLines(ResourceUtils.getFile(resourceFile.getURL()).toPath()).stream()
					.map(line -> convertStock(Arrays.asList(line.trim().split(",")))).toList();
			stocks.forEach(stock -> consumer.accept(new GenericMessage<ProductAvailabilityEvent>(stock)));
		} catch (Exception e) {
			new Exception("Error in consumer");
		}
	}

	@PostConstruct
	public void init() {
		startMockServer();
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/1/similarids"))
				.respond(HttpResponse.response().withBody("[2,3,4]").withStatusCode(HttpStatus.OK.value())
						.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/2/similarids"))
				.respond(HttpResponse.response().withBody("[3,7,8]").withStatusCode(HttpStatus.OK.value())
						.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/3/similarids"))
				.respond(HttpResponse.response().withBody("[7,8,9]").withStatusCode(HttpStatus.OK.value())
						.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/4/similarids"))
				.respond(HttpResponse.response().withBody("[1,2,5]").withStatusCode(HttpStatus.OK.value())
						.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/5/similarids"))
				.respond(HttpResponse.response().withBody("[1,2,6]").withStatusCode(HttpStatus.OK.value())
						.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/26/similarids"))
		.respond(HttpResponse.response().withBody("[1,100,6]").withStatusCode(HttpStatus.OK.value())
				.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/37/similarids"))
		.respond(HttpResponse.response().withBody("[1000,7,8]").withStatusCode(HttpStatus.OK.value())
				.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/18/similarids"))
		.respond(HttpResponse.response().withBody("[1,2,10000]").withStatusCode(HttpStatus.OK.value())
				.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/12/similarids"))
		.respond(HttpResponse.response().withBody("[20,18,19]").withStatusCode(HttpStatus.OK.value())
				.withHeader(Header.header("Content-Type", "application/json")));
		mockServerClient.when(HttpRequest.request().withMethod("GET").withPath("/product/9/similarids"))
		.respond(HttpResponse.response().withBody("[11,15,19]").withStatusCode(HttpStatus.OK.value())
				.withHeader(Header.header("Content-Type", "application/json")));
	}
	@PreDestroy
	public void onDestroy() {
		mockServer.stop();
	}

	public ProductAvailabilityEvent convertStock(List<String> stock) {
		return ProductAvailabilityEvent.builder().sizeId(Long.parseLong(stock.get(0)))
				.availability(Boolean.parseBoolean(stock.get(1)))
				.update(Timestamp.valueOf(
						LocalDateTime.parse(stock.get(2), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"))))
				.build();
	}
}
