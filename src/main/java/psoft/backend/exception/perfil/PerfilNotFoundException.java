package psoft.backend.exception.perfil;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PerfilNotFoundException extends RuntimeException {
    public PerfilNotFoundException(String s) {
        super(s);
    }
}
