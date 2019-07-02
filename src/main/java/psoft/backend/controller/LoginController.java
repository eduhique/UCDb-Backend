package psoft.backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psoft.backend.exception.user.UserEmailInvalidoException;
import psoft.backend.exception.user.UserInvalidoException;
import psoft.backend.exception.user.UserNotFoundException;
import psoft.backend.model.User;
import psoft.backend.request.UserRequest;
import psoft.backend.service.UserService;

import java.util.Date;

@RestController
@RequestMapping("/v1/login")
public class LoginController {

    private final String TOKEN_KEY = "aquiAsCoisasFuncionam";

    @Autowired
    private UserService userService;

    @PostMapping("/")
    @ApiOperation(value = "Autententicar um usuário", notes = "Essa Operação recebe um usuário e verifica seu cadastro no" +
            " banco de dados, caso esteja devidamente cadastrado, gera um token único que possui validade de 3 horas.", response = LoginResponse.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Retorna um LoginResponse com o token",
                    response = LoginResponse.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Retorna uma mensagem de erro com uma Exception."
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception."
            ),
            @ApiResponse(
                    code = 409,
                    message = "Retorna uma mensagem de erro com uma Exception."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso exista algum erro do sistemas, será retornado uma Exception."
            )
    })
    public LoginResponse authenticate(@ApiParam(value = "Objeto que representa um usuário que irá se autenticar" +
            " no sistema.") @RequestBody UserRequest userRequest) {
        if (userRequest == null) {
            throw new UserNotFoundException("Usuario não encontrado!");
        }

        if (!userRequest.validarEmail()) throw new UserEmailInvalidoException("Insira um e-mail valido");

        User authUser = userService.findByEmail(userRequest.getEmail().toLowerCase());

        if (authUser == null) {
            throw new UserNotFoundException("Usuario não encontrado!");
        }

        if (!authUser.getSenha().equals(userRequest.getSenha())) {
            throw new UserInvalidoException("Senha Inválida!");
        }

        String token = Jwts.builder().
                setSubject(authUser.getEmail()).
                signWith(SignatureAlgorithm.HS512, TOKEN_KEY).
                setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000)) // 3 horas
                .compact();

        return new LoginResponse(token);

    }

    @ApiModel(value = "Login Response", description = "Modelo de uma Response do login. Esse modelo tem como obejtivo" +
            " estrutar a response da autenticação dos usuários que estão logando")
    private class LoginResponse {

        @ApiModelProperty(value = "Token do usuário logado", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlZHVhcmRvaGVucmlx" +
                "dWUzOEBsaXZlLmNvbSIsImV4cCI6MTU2MTA4NzM3M30.NEJoipQvJ...")
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }

}
