package one.terenin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/show")
@Controller
public class ShowController {

    /**
     * @apiNote
     * This controller need for showing some pages
     * in the core package you can see the infrastructure controllers on REST
     * */

    @GetMapping("/login-page")
    public String getLoginPage(){
        return "/fragments/login-page";
    }
    @GetMapping("/profile-page")
    public String getProfilePage(){
        return "/fragments/profile-page";
    }
    @GetMapping("/error-page")
    public String getErrorPage(){
        return "/fragments/error-page";
    }
}
