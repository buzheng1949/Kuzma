package com.buzheng.me.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by buzheng on 18/1/13.
 */
@RequestMapping("/")
@RestController
public class WelcomeController {
    @RequestMapping("index")
    public String index() {
        return "HELLO";
    }
}
