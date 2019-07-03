package psoft.backend.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.backend.exception.user.UserExistsException;
import psoft.backend.model.User;
import psoft.backend.service.UserService;

import javax.servlet.ServletException;

@RestController
@RequestMapping({"/v1/users"})
public class UserController {

    private UserService userService;


    UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Cria um usuário", notes = "Essa Operação cria um usuário a partir de suas informações básicas."
            , response = User.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Retorna o usuário criado.",
                    response = User.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Retorna uma mensagem de erro com uma Exception."
            ),
            @ApiResponse(
                    code = 409,
                    message = "Retorna uma mensagem de erro com uma Exception."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar uma Exception."
            )

    })
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user) throws UserExistsException {
        User newUser = userService.create(user);
        if (newUser == null) {
            throw new InternalError("Algo deu errado");
        }
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retornar usuário da sessão", notes = "Essa Operação retorna o usuário logado no sistema."
            , response = User.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna o usuário Logado."

            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com a UserNotFoundException"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar uma Exception."
            )

    })
    @GetMapping(value = "/getuser")
    @ResponseBody
    public ResponseEntity<User> getUserLogado(@RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
