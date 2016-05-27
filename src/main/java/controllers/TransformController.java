package controllers;

import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TransformController {

    @RequestMapping("/transform")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}

