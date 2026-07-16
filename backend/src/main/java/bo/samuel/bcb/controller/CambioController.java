package bo.samuel.bcb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CambioController {

    @GetMapping("/api/saludo")
    public String saludo() {
        return "Bienvenido a BCB Cambio Oficial";
    }
}