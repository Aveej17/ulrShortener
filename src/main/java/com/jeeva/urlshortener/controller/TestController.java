package com.jeeva.urlshortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test(){
//        System.out.println("END Point Test Gets called!!!!!");
        return "From Test Controller";
    }
}
