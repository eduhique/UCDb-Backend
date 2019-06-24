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
import psoft.backend.model.User;
import psoft.backend.service.UserService;

import javax.servlet.ServletException;
import java.util.Date;

@RestController
@RequestMapping("/v1/login")
public class LoginController {

    private final String TOKEN_KEY = "aquiAsCoisasFuncionam";

    @Autowired
    private UserService userService;

    @PostMapping("/")
    @ApiOperation(value = "Autententicar um usuário", notes = "Essa Operação recebe um usuário verifica seu cadastro no" +
            " banco de dados e, caso esteja cadastrado, gera um token único", response = LoginResponse.class)
    @ApiResponses(value= {
            @ApiResponse(
                    code=201,
                    message="Retorna um LoginResponse com o token",
                    response=LoginResponse.class
            )
    })
    public LoginResponse authenticate(@RequestBody User user) throws ServletException {
        if (user == null) {
            throw new ServletException("Usuario não encontrado!");
        }

        if (!user.validarEmail()) throw new UserEmailInvalidoException("Insira um e-mail valido");

        // Recupera o usuario
        User authUser = userService.findByEmail(user.getEmail().toLowerCase());

        if (authUser == null) {
            throw new ServletException("Usuario não encontrado!");
        }

        if (!authUser.getSenha().equals(user.getSenha())) {
            throw new ServletException("Senha Inválida!");
        }

        String token = Jwts.builder().
                setSubject(authUser.getEmail()).
                signWith(SignatureAlgorithm.HS512, TOKEN_KEY).
                setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .compact();

        return new LoginResponse(token);
        
    }

    @ApiModel(value = "Login Response", description = "Modelo de uma Response do login. Esse modelo tem como obejtivo" +
            " estrutar a response da autenticação dos usuários que estão logando")
    private class LoginResponse {

        @ApiModelProperty(value = "Token do usuário logado",example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlZHVhcmRvaGVucmlx" +
                "dWUzOEBsaXZlLmNvbSIsImV4cCI6MTU2MTA4NzM3M30.NEJoipQvJ...")
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }

}
