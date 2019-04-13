package projects.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import projects.model.CardOutput;

@Service
public class CardOutputProducer {
	private final static String TOPIC = "data-output";
	
	@Autowired
	private KafkaTemplate<String, CardOutput> kafkaCardOutputTemplate;
	
	public void produce(CardOutput card) {
		kafkaCardOutputTemplate.send(TOPIC, card);
	}
}
