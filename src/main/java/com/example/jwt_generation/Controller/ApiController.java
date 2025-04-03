package com.example.jwt_generation.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ApiController {

    private List<String> list = Arrays.asList("helllo","this","is","jayadheep");

    @GetMapping("/list")
    public List<String> getList(){
        return list;
    }

    @PostMapping("/list")
    public void addList(@RequestBody List<String> strings){

        list.addAll(strings);
    }


}
