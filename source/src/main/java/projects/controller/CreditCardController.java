package projects.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projects.kafka.CardInputProducer;
import projects.model.CardInput;
import projects.model.CardInputResponse;
import projects.model.CardInputVO;

@RestController
@RequestMapping(value = "/api")
public class CreditCardController {
	@Autowired
	private CardInputProducer cardInputProducer;
	
	
	private void validate(CardInputVO card) {
		// TODO: validate the input
	}

	@PostMapping(value = "/auth")
	public CardInputResponse getCreditCard(@RequestBody CardInputVO card) {
		validate(card);
	    UUID uuid = UUID.randomUUID();
	    String randomUUIDString = uuid.toString();
		CardInput ci = new CardInput(randomUUIDString, card.getCardNumber(), card.getExpirationDate(), card.getCvv());
		cardInputProducer.produce(ci);
		return new CardInputResponse(randomUUIDString);
	}
}
