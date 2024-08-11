package Config.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class User {
    @PostMapping("/info")
    public void info() {
        System.out.println("dsflkjsdlkf");
    }
}
