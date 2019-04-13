package projects.kafka;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import projects.model.CardInput;
import projects.model.CardOutput;
import projects.repo.UuidKeyRepository;

@Service
public class Consumer {
	private final static String INPUT_TOPIC = "data-input";

	@Autowired
	private CardOutputProducer cardOutputProducer;
	
	@Autowired
	UuidKeyRepository uuidKeyRepo;
	
	private byte[] generateKey() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[16];
		secureRandom.nextBytes(key);
		return key;
	}
	
    @KafkaListener(topics = INPUT_TOPIC, groupId = "group_id")
    public void consume(CardInput cardInput) throws IOException {
		try {
			String strToEncrypt = String.format("%s,%s,%s", cardInput.getCardNumber(), cardInput.getExpirationDate(), cardInput.getCvv());
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] key = generateKey();
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        String encryptedString = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
	        String keyString = Base64.getEncoder().encodeToString(key);
	        CardOutput cardOutput = new CardOutput(cardInput.getUuid(), encryptedString);

	        // send to output topic
	        cardOutputProducer.produce(cardOutput);
	        
	        // put uuid and key in Redis
	        uuidKeyRepo.setKey(cardInput.getUuid(), keyString);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }    	
    }
}
