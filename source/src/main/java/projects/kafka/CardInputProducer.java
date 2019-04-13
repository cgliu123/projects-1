package projects.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import projects.model.CardInput;

@Service
public class CardInputProducer {
	private final static String TOPIC = "data-input";
	
	@Autowired
	private KafkaTemplate<String, CardInput> kafkaCardInputTemplate;
	
	public void produce(CardInput card) {
		kafkaCardInputTemplate.send(TOPIC, card);
	}
}
