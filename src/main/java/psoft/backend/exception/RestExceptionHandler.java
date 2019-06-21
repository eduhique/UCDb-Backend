package psoft.backend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import psoft.backend.exception.comentario.ComentarioInvalidoException;
import psoft.backend.exception.comentario.ComentarioNullException;
import psoft.backend.exception.disciplina.DisciplinaNotFoundException;
import psoft.backend.exception.perfil.PerfilNotFoundException;
import psoft.backend.exception.user.*;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomRestError> handleAnyException(Exception ex, WebRequest request) {
        CustomRestError errorMessage = new CustomRestError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DisciplinaNotFoundException.class, PerfilNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<CustomRestError> notFound(Exception ex, WebRequest request) {
        CustomRestError errorMessage = new CustomRestError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserEmailInvalidoException.class, UserExistsException.class,})
    public ResponseEntity<CustomRestError> conflict(Exception ex, WebRequest request) {
        CustomRestError errorMessage = new CustomRestError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserInvalidoException.class, UserNullException.class, ComentarioInvalidoException.class, ComentarioNullException.class})
    public ResponseEntity<CustomRestError> badRequest(Exception ex, WebRequest request) {
        CustomRestError errorMessage = new CustomRestError(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}