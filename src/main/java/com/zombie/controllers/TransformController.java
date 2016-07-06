package com.zombie.controllers;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformController {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(TransformController.class);

    @RequestMapping("/transform")
    public String index() {
        log.trace("In tranformController");
        return "Greetings from Spring Boot!";
    }

}

