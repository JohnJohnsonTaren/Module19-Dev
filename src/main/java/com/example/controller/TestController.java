// Завдання №1 - додай модуль Spring MVC
// Додай до проєкту модуль Spring MVC.
//
// Створи тестовий контролер (наприклад, TestController),
//  створи у цьому контролері тестовий метод (зі шляхом, наприклад, /test) та переконайсь,
//  що програма запускається, та за адресою на кшталт http://localhost:8080/test
//  в браузері можна доступитись до метода контролера.

package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test(Model model) {
        model.addAttribute("message", "Hello, World");
        return "test";
    }
}
