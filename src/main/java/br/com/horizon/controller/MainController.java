package br.com.horizon.controller;

import br.com.horizon.security.CurrentUser;
import br.com.horizon.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/api/user/me")
    public String quemSouEu(@CurrentUser UserPrincipal currentUser) {
        return "Seu nome é: " + currentUser.getName();
    }

    @GetMapping("/api/user/whoAmI")
    public String whoAmI() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserPrincipal) authentication.getPrincipal();
        principal.getName();
        return "You are: " + principal.getName();
    }
}

