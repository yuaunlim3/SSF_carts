package VTTP_ssf.practice1.Controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTP_ssf.practice1.Model.Users;
import VTTP_ssf.practice1.Service.userService;

@Controller
@RequestMapping
public class loginController {

    @Autowired
    private userService userSvc;

    private final Logger logger = Logger.getLogger(loginController.class.getName());

    @PostMapping("/homepage")
    public String login(@RequestParam String username, Model model) {
        if (username == null || username.isEmpty()) {
            logger.info("No name given");
            String errorMsg = "Username cannot be blank";
            model.addAttribute("error", 1);
            model.addAttribute("errorMsg", errorMsg);
            return "index";
        }
        logger.info("%s has login\n".formatted(username));
        return "redirect:/homepage/" + username;
    }

    @GetMapping("/homepage/{username}")
    public String showHomepage(@PathVariable String username, Model model) {




        Users user = new Users();
        user.setName(username);
        userSvc.loginUser(user);
        int numCart = userSvc.numCart(user);
        model.addAttribute("username", username);
        model.addAttribute("numCart", numCart);
        return "homepage";
    }

    @PostMapping("/homepage/{username}")
    public String exitHomepage(@PathVariable String username, Model model) {


        Users user = new Users();
        user.setName(username);
        userSvc.loginUser(user);
        int numCart = userSvc.numCart(user);
        model.addAttribute("username", username);
        model.addAttribute("numCart", numCart);
        return "homepage";
    }
}
