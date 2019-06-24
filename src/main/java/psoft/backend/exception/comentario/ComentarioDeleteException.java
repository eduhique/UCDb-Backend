package psoft.backend.exception.comentario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ComentarioDeleteException extends RuntimeException {
    public ComentarioDeleteException(String s) {
        super(s);
    }
}
