package psoft.backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public LoginResponse authenticate(@RequestBody User user) throws ServletException {

        // Recupera o usuario
        User authUser = userService.findByEmail(user.getEmail().toLowerCase());

        // verificacoes
        if (!user.validarEmail()) throw new UserEmailInvalidoException("Insira um e-mail valido");
        if (authUser == null) {
            throw new ServletException("Usuario nao encontrado!");
        }

        if (!authUser.getSenha().equals(user.getSenha())) {
            throw new ServletException("Senha invalida!");
        }

        String token = Jwts.builder().
                setSubject(authUser.getEmail()).
                signWith(SignatureAlgorithm.HS512, TOKEN_KEY).
                setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .compact();

        return new LoginResponse(token);


    }

    private class LoginResponse {
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }

}
