package main.java.controllers;

import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TransformController {

    @RequestMapping("/transform")
    public String index(String input) {
        return "\nGreetings from Spring Boot: Your input was: " + input;
    }

}

