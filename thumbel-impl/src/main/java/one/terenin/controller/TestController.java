package one.terenin.controller;


import one.terenin.dto.user.UserResponse;
import one.terenin.dto.util.Position;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String mth(Model model){
        List<UserResponse> responses = new ArrayList<>();
        responses.add(UserResponse.builder().biography("[]").createdDate(null).id(UUID.randomUUID()).login("lof").position(Position.CREATOR).build());
        responses.add(UserResponse.builder().biography("{}").createdDate(null).id(UUID.randomUUID()).login("lof1").position(Position.CREATOR).build());
        responses.add(UserResponse.builder().biography("()").createdDate(null).id(UUID.randomUUID()).login("lof12").position(Position.CREATOR).build());
        model.addAttribute("responses", responses);
        return "fragments/index";
    }

}
