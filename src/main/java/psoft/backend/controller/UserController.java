package psoft.backend.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.backend.exception.user.UserExistsException;
import psoft.backend.model.User;
import psoft.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping({"/v1/users"})
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<User> create(@RequestBody User user) throws UserExistsException {

        User newUser = userService.create(user);

        if (newUser == null) {
            throw new InternalError("Algo deu errado");
        }

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();

        if (users == null) {
            throw new UserExistsException("Não existe usuarios");
        }

        return new ResponseEntity<List<User>>( users, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity deleteAll(){
        try {
            userService.deleteAll();
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalError("Algo deu errado");
        }
    }

}
