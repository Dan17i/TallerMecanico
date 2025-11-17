package co.edu.uniquindio.tallermacanico.dto;

import org.springframework.web.bind.annotation.RequestParam;

public record LoginDTO(String username,
                       String password) {
}
