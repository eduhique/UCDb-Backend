package psoft.backend.exception.disciplina;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DisciplinaNotFoundException extends RuntimeException {
    public DisciplinaNotFoundException(String s) {
        super(s);
    }
}
