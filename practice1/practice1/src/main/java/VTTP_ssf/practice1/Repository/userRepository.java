package VTTP_ssf.practice1.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTP_ssf.practice1.Model.Users;

@Repository
public class userRepository {

    @Autowired
    @Qualifier("redis-object")
    private RedisTemplate<String, Object> template;

    private final Logger logger = Logger.getLogger(userRepository.class.getName());

    // hexist userID
    // hset userID name username
    public void loginUser(Users user) {
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        // check if username is exist in database
        logger.info("Username: %s".formatted(user.getName()));
        if (checkUser(user)) {
            logger.info("User exist\n");
            
        }
        else{
            logger.info("User does not exist\n");
            Map<String, Object> infos = new HashMap<>();
            infos.put("username", user.getName());
            hashOps.putAll(user.getId(), infos);
        }

    }

    private boolean checkUser(Users user){
        //Get all the ids in data
        Set<String> userIds = template.keys("*");
        for(String userId: userIds){
            HashOperations<String, String, Object> hashOps = template.opsForHash();
            Object storedUsername = hashOps.get(userId, "username");

            if (storedUsername != null && storedUsername.equals(user.getName())) {
                return true;
            }
        }
        return false;
    }

    private String getID(Users user) {
        Set<String> userIds = template.keys("*");
        if (userIds == null || userIds.isEmpty()) {
            return null;
        }
        for (String userId : userIds) {
            HashOperations<String, String, Object> hashOps = template.opsForHash();
            Object storedUsername = hashOps.get(userId, "username");
            if (storedUsername != null && storedUsername.equals(user.getName())) {
                return userId;
            }
        }
        return null;
    }
    

    //hset userID cartName cart
    public void newCart(Users user, String cartName){
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        Map<String, Object> infos = hashOps.entries(id);
        logger.info("this is the info: %s\n".formatted(infos.toString()));
        Map<String , Integer> cart = new HashMap<>();
        infos.put(cartName, cart);

        logger.info("this is the info after: %s\n".formatted(infos.toString()));
        hashOps.putAll(id, infos);
    }

    //hset userID cartName cart item qty
    public void addItems(Users user, String cartName,String item, Integer qty){
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        Map<String, Object> infos = hashOps.entries(id);

        logger.info("this is the info: %s\n".formatted(infos.toString()));
        Map<String , Integer> cart = (Map<String, Integer>) infos.get(cartName);
        cart.computeIfPresent(item, (k,v)->v+qty);
        cart.computeIfAbsent(item, v->qty);
        infos.put(cartName, cart);

        logger.info("this is the info after: %s\n".formatted(infos.toString()));
        hashOps.putAll(id, infos);
    }

    //hget userId cartName
    public Map<String,Integer> getCart(Users user, String cartName){
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        Map<String, Object> infos = hashOps.entries(id);
        Map<String, Integer> cart = (Map<String, Integer>) infos.get(cartName);
    
        return cart;
    }

    //hexist userId cartName
    public int isExist(Users user, String cartName){
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        Map<String, Object> infos = hashOps.entries(id);
        if(infos.containsKey(cartName)){
            return 1;
        }
        if(cartName == null || cartName.isEmpty()){
            return 2;
        }
        return 0;
    } 

    //hgetall userId
    public List<String> getCarts(Users user){
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        Map<String, Object> infos = hashOps.entries(id);
        //Get all carts except username
        List<String> carts  = infos.keySet().stream().filter(key -> !key.equalsIgnoreCase("username")).collect(Collectors.toList());
        return carts;

    }

    public int getTotal(Users user, String cartName){
        int total = 0; 
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        Map<String, Object> infos = hashOps.entries(id);
        Map<String, Integer> cart = (Map<String, Integer>) infos.get(cartName);
        for(int val: cart.values()){
            total+= val;
        }
        return total;

    }

    public int numCart(Users user){
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        Map<String, Object> infos = hashOps.entries(id);
        List<String> carts  = infos.keySet().stream().filter(key -> !key.equalsIgnoreCase("username")).collect(Collectors.toList());
        logger.info(carts.toString());
        return carts.size();
    }

    public void deleteCart(Users user, String cartName){
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String id = getID(user);
        hashOps.delete(id, cartName);

    }


}
