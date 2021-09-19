package com.mydaytodo.covid.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("persons")
public class TestController {

    @GetMapping("")
    public String persons() {
        return "Persons";
    }
}
