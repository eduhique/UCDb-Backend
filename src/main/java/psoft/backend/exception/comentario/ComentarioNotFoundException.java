package psoft.backend.exception.comentario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ComentarioNotFoundException extends RuntimeException {
    public ComentarioNotFoundException(String s) {
        super(s);
    }
}
