package projects.repo;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UuidKeyRepository {
    public static final String KEY = "uuid-key";

    private RedisTemplate<String, String> redisTemplate;
    
    public UuidKeyRepository(RedisTemplate<String, String> redisTemplate) {
    	this.redisTemplate = redisTemplate;
    }
    
    public String getKey(String uuid){
        return (String) redisTemplate.opsForHash().get(KEY, uuid);
    }

    public void setKey(String uuid, String key){
    	redisTemplate.opsForHash().put(KEY, uuid, key);
    }
}
