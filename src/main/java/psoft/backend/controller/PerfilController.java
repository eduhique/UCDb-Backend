package psoft.backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.backend.exception.disciplina.DisciplinaNotFoundException;
import psoft.backend.exception.perfil.PerfilNotFoundException;
import psoft.backend.model.Disciplina;
import psoft.backend.model.Perfil;
import psoft.backend.model.TokenFilter;
import psoft.backend.service.DisciplinaService;
import psoft.backend.service.PerfilService;
import psoft.backend.service.UserService;

import javax.servlet.ServletException;
import java.util.List;

@RestController
@RequestMapping({"/v1/perfil"})
public class PerfilController {

    private final DisciplinaService disciplinaService;

    private final PerfilService perfilService;
    private final UserService userService;
    private TokenFilter tokenFilter;

    public PerfilController(DisciplinaService disciplinaService, PerfilService perfilService, UserService userService) {
        this.disciplinaService = disciplinaService;
        this.perfilService = perfilService;
        this.userService = userService;
        this.tokenFilter = new TokenFilter();
    }

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


    @PostMapping(value = "/disciplina/createall")
    @ResponseBody
    public ResponseEntity<List<Disciplina>> createAll(@RequestBody List<Disciplina> disciplina) {
        List<Disciplina> disciplinas = disciplinaService.createAll(disciplina);

        if (disciplinas.isEmpty()) {
            throw new InternalError("Something went wrong");
        }

        return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.CREATED);
    }

    @GetMapping(value = "/disciplina/all")
    public ResponseEntity<List<Disciplina>> findAll() {
        List<Disciplina> disciplinas = disciplinaService.findAll();

        if (disciplinas.isEmpty()) {
            throw new DisciplinaNotFoundException("Não há disciplinas cadastradas");
        }

        return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.OK);
    }

    /*
     -- perfil
     */

    @PostMapping(value = "/createall")
    @ResponseBody
    public ResponseEntity<List<Perfil>> createPerfilAll(@RequestBody List<Disciplina> disciplina) {

        List<Perfil> perfil = perfilService.createPerfilAll(disciplina);

        if (perfil.isEmpty()) {
            throw new InternalError("Something went wrong");
        }

        return new ResponseEntity<List<Perfil>>(perfil, HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Perfil> findPerfil(@RequestParam(name = "id", required = false, defaultValue = "0") long id, @RequestHeader("Authorization") String token) throws ServletException {
        Disciplina dis = disciplinaService.findById(id);

        if (dis == null) {
            throw new PerfilNotFoundException("Perfil não encontrada!");
        }
        Perfil perfil = perfilService.findById(dis.getPerfil().getId());
        perfil.setUserAtual(userService.findByEmail(tokenFilter.getLogin(token)));

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

}
