package psoft.backend.exception.user;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String s) {
        super(s);
    }
}
