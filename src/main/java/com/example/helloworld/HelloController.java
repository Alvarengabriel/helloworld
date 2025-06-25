package com.example.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Teste";
    }

    @GetMapping("/c2f")
    public String celsiusToFahrenheit(@RequestParam("celsius") double celsius) {
        double resultado = (celsius * 1.8) + 32;
        return Double.toString(resultado);
    }

    @GetMapping("/f2c") 
    public String fahrenheitToCelsius(@RequestParam("fahrenheit") double fahrenheit) {
        double resultado = (fahrenheit - 32) / 1.8;
        return Double.toString(resultado);
    }
}