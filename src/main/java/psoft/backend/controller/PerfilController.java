package psoft.backend.controller;


import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.backend.exception.comentario.ComentarioInvalidoException;
import psoft.backend.exception.comentario.ComentarioNullException;
import psoft.backend.exception.disciplina.DisciplinaNotFoundException;
import psoft.backend.exception.perfil.PerfilNotFoundException;
import psoft.backend.exception.user.UserNotFoundException;
import psoft.backend.model.*;
import psoft.backend.service.ComentarioService;
import psoft.backend.service.DisciplinaService;
import psoft.backend.service.PerfilService;
import psoft.backend.service.UserService;

import javax.servlet.ServletException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/v1/perfil"})
public class PerfilController {

    private final DisciplinaService disciplinaService;

    private final PerfilService perfilService;
    private final UserService userService;
    private final ComentarioService comentarioService;
    private TokenFilter tokenFilter;

    public PerfilController(DisciplinaService disciplinaService, PerfilService perfilService, UserService userService, ComentarioService comentarioService) {
        this.disciplinaService = disciplinaService;
        this.perfilService = perfilService;
        this.userService = userService;
        this.comentarioService = comentarioService;
        this.tokenFilter = new TokenFilter();
    }

    @ApiOperation(value = "Cria Disciplinas", notes = "Essa Operação cria uma disciplina com suas informações a partir" +
            " de uma lista", response = Disciplina.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Retorna uma lista com as Disciplinas criadas",
                    response = Disciplina.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar um ResponseModel com a Exception",
                    response = Disciplina.class
            )

    })
    @ApiParam
    @PostMapping(value = "/disciplina/createall")
    @ResponseBody
    public ResponseEntity<List<Disciplina>> createAll(@RequestBody List<Disciplina> disciplina) {
        List<Disciplina> disciplinas = disciplinaService.createAll(disciplina);

        if (disciplinas.isEmpty()) {
            throw new InternalError("Algo deu errado!!");
        }

        return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Buscar uma Disciplina", notes = "Essa Operação pesquisa uma disciplina através do seu" +
            " identificador único(ID) e o retorna", response = Disciplina.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma Disciplina",
                    response = Disciplina.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com a DisciplinaNotFoundException",
                    response = Disciplina.class
            )

    })
    @GetMapping(value = "/disciplina/{id}")
    @ResponseBody
    public ResponseEntity<Disciplina> findById(@PathVariable long id) {
        Disciplina dis = disciplinaService.findById(id);

        if (dis == null) {
            throw new DisciplinaNotFoundException("Disciplina não encontrada!");
        }

        return new ResponseEntity<Disciplina>(dis, HttpStatus.OK);
    }

    @GetMapping(value = "/disciplina/search")
    public ResponseEntity<List<Disciplina>> searchString(@RequestParam(name = "substring", required = false, defaultValue = "não existe") String substring) {
        List<Disciplina> searchForString = disciplinaService.searchForString(substring.toUpperCase());
        if (searchForString.isEmpty()) {
            throw new DisciplinaNotFoundException("Disciplina não encontrada!");
        }
        return new ResponseEntity<List<Disciplina>>(searchForString, HttpStatus.OK);
    }

    @GetMapping(value = "/disciplina/all")
    public ResponseEntity<List<Disciplina>> findAll() {
        List<Disciplina> disciplinas = disciplinaService.findAll();

        if (disciplinas.isEmpty()) {
            throw new DisciplinaNotFoundException("Não há disciplinas cadastradas");
        }

        return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.OK);
    }


    //perfil


    @PostMapping(value = "/createall")
    @ResponseBody
    public ResponseEntity<List<Perfil>> createPerfilAll(@RequestBody List<Disciplina> disciplina) {

        List<Perfil> perfil = perfilService.createPerfilAll(disciplina);

        if (perfil.isEmpty()) {
            throw new InternalError("Algo deu errado!!");
        }

        return new ResponseEntity<List<Perfil>>(perfil, HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Perfil> findPerfil(@RequestParam(name = "id", required = false, defaultValue = "0") long id, @RequestHeader("Authorization") String token) throws ServletException {
        Disciplina dis = disciplinaService.findById(id);
        if (dis == null) {
            throw new PerfilNotFoundException("Perfil não encontrado!");
        }

        Perfil perfil = perfilService.findById(dis.getPerfil().getId());
        User user = userService.findByEmail(tokenFilter.getLogin(token));
        if (user == null) {
            throw new UserNotFoundException("Perfil não encontrado!");
        }
        perfil.setUserAtual(user);

        return new ResponseEntity<Perfil>(perfil, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Perfil>> findPerfilAll() {
        List<Perfil> perfil = perfilService.findAll();

        if (perfil.isEmpty()) {
            throw new PerfilNotFoundException("Não há disciplinas cadastradas");
        }

        return new ResponseEntity<List<Perfil>>(perfil, HttpStatus.OK);
    }

    // Comentario

    @PostMapping(value = "/comentario/create")
    @ResponseBody
    public ResponseEntity<Comentario> createComentario(@RequestParam(name = "id", required = false, defaultValue = "0") long id, @RequestHeader("Authorization") String token, @RequestBody Comentario comentario) throws ServletException {
        Perfil perfil = perfilService.findById(id);
        User user = userService.findByEmail(tokenFilter.getLogin(token));
        if (user == null) {
            throw new UserNotFoundException("Perfil não encontrado!");
        }
        if (perfil == null) {
            throw new PerfilNotFoundException("Perfil não encontrado!");
        }
        if (comentario == null) {
            throw new ComentarioNullException("Comentário não é valido");
        }
        if (comentario.getText() == null) throw new ComentarioNullException("O comentário não pode ser Null");
        if (comentario.getText().trim().equals(""))
            throw new ComentarioInvalidoException("O comentário não pode ser vazio, insira um comentário valido");

        Comentario preComentario = new Comentario(perfil, user, comentario.getText(), LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm")), LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), new ArrayList<Comentario>());

        perfil.addComentario(preComentario);

        Comentario novoComentario = comentarioService.create(preComentario);

        return new ResponseEntity<Comentario>(novoComentario, HttpStatus.CREATED);
    }

    @PostMapping(value = "/comentario/resposta")
    @ResponseBody
    public ResponseEntity<Comentario> createResposta(@RequestParam(name = "id", required = false, defaultValue = "0") long comentarioId, @RequestHeader("Authorization") String token, @RequestBody Comentario resposta) throws ServletException {
        Comentario comentario = comentarioService.findById(comentarioId);
        User user = userService.findByEmail(tokenFilter.getLogin(token));
        if (user == null) {
            throw new UserNotFoundException("Perfil não encontrado!");
        }
        if (resposta == null) {
            throw new ComentarioNullException("Comentário não é valido");
        }
        if (resposta.getText() == null) throw new ComentarioNullException("O comentário não pode ser Null");
        if (resposta.getText().trim().equals(""))
            throw new ComentarioInvalidoException("O comentário não pode ser vazio, insira um comentário valido");

        Perfil perfil = comentario.getPerfil();
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Comentario preComentario = new Comentario(perfil, user, resposta.getText(), hora, data, new ArrayList<Comentario>());
        comentario.addComentario(preComentario);

        Comentario novoComentario = comentarioService.create(preComentario);

        return new ResponseEntity<Comentario>(novoComentario, HttpStatus.CREATED);
    }

    @GetMapping(value = "/comentario/all")
    public ResponseEntity<List<Comentario>> comentarioALL() {
        List<Comentario> comentarios = comentarioService.findAll();

        if (comentarios.isEmpty()) {
            throw new PerfilNotFoundException("Não há disciplinas cadastradas");
        }

        return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
    }


}
