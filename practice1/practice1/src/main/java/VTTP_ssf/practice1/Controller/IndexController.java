package VTTP_ssf.practice1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/index.html"})
public class IndexController {
    
    @GetMapping("/loginpage")
    public String loginpage(){
        return "index";
    }
}
