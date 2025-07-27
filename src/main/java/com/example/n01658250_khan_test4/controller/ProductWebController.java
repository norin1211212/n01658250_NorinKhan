package com.example.n01658250_khan_test4.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ProductWebController {
    @GetMapping("/")
    public String getHomePage() {
        return "index";

    }
}
