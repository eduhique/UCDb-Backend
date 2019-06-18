package psoft.backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.backend.exception.disciplina.DisciplinaNotFoundException;
import psoft.backend.model.Disciplina;
import psoft.backend.service.DisciplinaService;

import java.util.List;

@RestController
@RequestMapping({"/v1/disciplina"})
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Disciplina> findById(@PathVariable long id) {
        Disciplina dis = disciplinaService.findById(id);

        if (dis == null) {
            throw new DisciplinaNotFoundException("Disciplina não encontrada!");
        }

        return new ResponseEntity<Disciplina>(dis, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Disciplina>> searchString(@RequestParam(name = "substring", required = false, defaultValue = "não existe") String substring) {
        List<Disciplina> searchForString = disciplinaService.searchForString(substring.toUpperCase());
        if (searchForString.isEmpty()) {
            throw new DisciplinaNotFoundException("Disciplina não encontrada!");
        }
        return new ResponseEntity<List<Disciplina>>(searchForString, HttpStatus.OK);
    }


    @PostMapping(value = "/crudall")
    @ResponseBody
    public ResponseEntity<List<Disciplina>> createAll(@RequestBody List<Disciplina> disciplina) {
        List<Disciplina> disciplinas = disciplinaService.createAll(disciplina);

        if (disciplinas == null) {
            throw new InternalError("Something went wrong");
        }

        return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Disciplina>> findAll() {
        List<Disciplina> disciplinas = disciplinaService.findAll();

        if (disciplinas == null) {
            throw new DisciplinaNotFoundException("Não há disciplinas cadastradas");
        }

        return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.OK);
    }

}
