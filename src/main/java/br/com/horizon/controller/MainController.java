package br.com.horizon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/home")
    public String home() {
        return "Agora só falta atualizar os códigos né";
    }

    @GetMapping("/test")
    public String test() {
        return "Testand o testando testando";
    }
}

