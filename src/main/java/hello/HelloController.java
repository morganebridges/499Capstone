package main.java.hello;

import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index(String input) {
        return "\nGreetings from Spring Boot: Your input was: " + input;
    }

}

