package projects.kafka;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import projects.model.CardOutput;
import projects.repo.UuidKeyRepository;

@Service
public class Consumer {
	private final static String OUTPUT_TOPIC = "data-output";
	
	@Autowired
	UuidKeyRepository uuidKeyRepo;
	
    @KafkaListener(topics = OUTPUT_TOPIC, groupId = "group_id")
    public void consume(CardOutput cardOutput) throws IOException {
 		try {
			String strKey = uuidKeyRepo.getKey(cardOutput.getUuid());
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			byte[] key = Base64.getDecoder().decode(strKey);
				
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			String decryptedStr = new String(cipher.doFinal(Base64.getDecoder().decode(cardOutput.getToken())));

			StringTokenizer st = new StringTokenizer(decryptedStr, ",");
			System.out.println("On proof after decryption: ");
			System.out.println(" Card number : "+ st.nextToken());
			System.out.println(" Expiration Date : "+ st.nextToken());
			System.out.println(" CVV : "+ st.nextToken());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }    	
    }
}
