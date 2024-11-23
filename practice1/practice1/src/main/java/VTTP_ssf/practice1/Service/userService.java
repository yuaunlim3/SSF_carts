package VTTP_ssf.practice1.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP_ssf.practice1.Model.Users;
import VTTP_ssf.practice1.Repository.userRepository;

@Service
public class userService {
    
   @Autowired
   private userRepository userRepo;

   public void loginUser(Users user){
    String id = UUID.randomUUID().toString().substring(0, 8);
    user.setId(id);
    userRepo.loginUser(user);
   }

   public void newCart(Users user, String cartName){
    userRepo.newCart(user, cartName);
   }

   public void addItems(Users user, String cartName,String item, Integer qty){
    userRepo.addItems(user, cartName, item, qty);
   }

   public Map<String, Integer> getCart(Users user, String cartName){
    return userRepo.getCart(user, cartName);
   }

   public int isExist(Users user, String cartName){
    return userRepo.isExist(user, cartName);
   }

   public List<String> getCarts(Users user){
    return userRepo.getCarts(user);
   }

   public int getTotal(Users user, String cartName){
    return userRepo.getTotal(user, cartName);
   }

   public int numCart(Users user){
      return userRepo.numCart(user);
   }

   public void deleteCart(Users user,String cartName){
      userRepo.deleteCart(user, cartName);
   }
}
