package psoft.backend.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserEmailInvalidoException extends RuntimeException {
    public UserEmailInvalidoException(String s) {
        super(s);
    }
}
