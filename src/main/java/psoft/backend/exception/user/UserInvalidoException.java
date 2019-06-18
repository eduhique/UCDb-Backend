package psoft.backend.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserInvalidoException extends RuntimeException {
    public UserInvalidoException(String s) {
        super(s);
    }
}
