package VTTP_ssf.practice1.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTP_ssf.practice1.Model.Users;
import VTTP_ssf.practice1.Service.userService;

@Controller
public class userController {

    private final Logger logger = Logger.getLogger(userController.class.getName());

    @Autowired
    private userService userSvc;

    @PostMapping("/loginpage")
    public String logout() {
        return "index";
    }

    // while inside the cart page
    @PostMapping("/cart/{username}")
    public String newCart(@PathVariable String username,
            @RequestParam(required = false) String cartName,
            @RequestParam(required = false) Integer created,
            Model model) {
        Users user = new Users();
        user.setName(username);
        int checker = userSvc.isExist(user, cartName);
        
        if (cartName == null || cartName.isEmpty()) {
            logger.info("Name not given");
            model.addAttribute("created", 0);
            
        } else if (checker == 0) {
            logger.info("Name given: %s".formatted(username));
            logger.info("Cartname given: %s".formatted(cartName));
            userSvc.newCart(user, cartName);
            Map<String, Integer> cart = userSvc.getCart(user, cartName);
            model.addAttribute("cartlist", cart);
            model.addAttribute("cartName", cartName);
            model.addAttribute("created", 1);
            model.addAttribute("username", username);
        } else {
            logger.info("Cart already exist: %d\n".formatted(checker));
            model.addAttribute("created", 0);
            model.addAttribute("checker", checker);
        }

        return "addCart";
    }

    // for the link
    @GetMapping("/cart/{username}")
    public String newCartPage(@PathVariable String username,
            @RequestParam(required = false) String cartName,
            Model model) {
        Users user = new Users();
        user.setName(username);
        Map<String, Integer> cart = userSvc.getCart(user, cartName);
        model.addAttribute("cartlist", cart);
        model.addAttribute("created", 0);
        model.addAttribute("username", username);

        return "addCart";
    }

    @PostMapping("/cart/{username}/{cartName}")
    public String addItemsNew(@PathVariable String username,
            @PathVariable String cartName,
            @RequestParam(required = false) String item,
            @RequestParam(required = false) Integer qty,
            Model model) {
        Users user = new Users();
        user.setName(username);
        userSvc.addItems(user, cartName, item, qty);
        model.addAttribute("created", 1);
        Map<String, Integer> cart = userSvc.getCart(user, cartName);
        logger.info("this is the cartlist: %s".formatted(cart.toString()));

        int total = userSvc.getTotal(user, cartName);
        model.addAttribute("numItems", total);
        model.addAttribute("itemlist", cart);
        model.addAttribute("username", username);
        model.addAttribute("cartName", cartName);
        return "addCart";
    }

    @GetMapping("/cart/{username}/{cartName}")
    public String addItemsCurrent(@PathVariable String username,
            @PathVariable String cartName,
            @RequestParam(required = false) String item,
            @RequestParam(required = false) Integer qty,
            Model model) {
        Users user = new Users();
        user.setName(username);
        userSvc.addItems(user, cartName, item, qty);
        model.addAttribute("created", 1);
        Map<String, Integer> cart = userSvc.getCart(user, cartName);
        logger.info("this is the cartlist: %s".formatted(cart.toString()));

        int total = userSvc.getTotal(user, cartName);
        model.addAttribute("numItems", total);
        model.addAttribute("itemlist", cart);
        model.addAttribute("username", username);
        model.addAttribute("cartName", cartName);
        return "addCart";
    }

    @GetMapping("/cartlist/{username}")
    public String cartList(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        Users user = new Users();
        user.setName(username);
        List<String> carts = userSvc.getCarts(user);
        Map<String, Integer> cartList = new HashMap<>();
        for (String cart : carts) {
            int total = userSvc.getTotal(user, cart);
            cartList.put(cart, total);
        }
        model.addAttribute("cartlist", cartList);
        return "cartlist";
    }

    @PostMapping("/cartlist/{username}")
    public String deleteCart(@PathVariable String username,
            @RequestParam(required = false) String cartName,
            Model model) {
        logger.info(cartName);
        Users user = new Users();
        user.setName(username);
        userSvc.deleteCart(user, cartName);
        List<String> carts = userSvc.getCarts(user);
        Map<String, Integer> cartList = new HashMap<>();
        for (String cart : carts) {
            int total = userSvc.getTotal(user, cart);
            cartList.put(cart, total);
        }
        model.addAttribute("cartlist", cartList);
        return "cartlist";
    }

}
