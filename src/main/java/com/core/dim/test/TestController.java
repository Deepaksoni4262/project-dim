package com.core.dim.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/greet")
    public String greet(){
        return "Hello User";
    }

    @GetMapping("/test-pr")
    public String testPr(){
        return "Jai Mahakaal";
    }
}
