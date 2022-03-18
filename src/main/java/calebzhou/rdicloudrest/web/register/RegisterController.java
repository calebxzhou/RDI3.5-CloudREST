package calebzhou.rdicloudrest.web.register;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("index")
    public String getindex(Model model){
        model.addAttribute("name","123");
        return "index";
    }

    @GetMapping("register")
    public String register(Model model){
        return "register";
    }
}
