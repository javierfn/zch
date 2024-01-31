package com.inditex.zarachallenge.infrastructure;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.inditex.zarachallenge.infrastructure.model.ProductAvailabilityEvent;

@Component
public class KafkaListener {

	@Bean
	public Consumer<Message<ProductAvailabilityEvent>> KafkaConsumer() {
		return message -> {
			// TODO: Insert code in this Consumer

		};
	}

}
