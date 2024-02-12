package com.inditex.zarachallenge.infrastructure;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.inditex.zarachallenge.application.SizeService;
import com.inditex.zarachallenge.infrastructure.model.ProductAvailabilityEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaListener {

	private final SizeService sizeService;

	@Bean
	public Consumer<Message<ProductAvailabilityEvent>> KafkaConsumer() {
		return message -> {
			log.info(message.getHeaders().toString());
			var productAvailabilityEvent = message.getPayload();

			sizeService.updateAvailability(productAvailabilityEvent.getSizeId(),
					productAvailabilityEvent.isAvailability(),
					productAvailabilityEvent.getUpdate());

		};
	}

}
