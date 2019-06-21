package psoft.backend.service;

import org.springframework.stereotype.Service;
import psoft.backend.dao.DisciplinaDAO;
import psoft.backend.dao.PerfilDAO;
import psoft.backend.model.Disciplina;

import java.util.*;

@Service
public class DisciplinaService {

    private final DisciplinaDAO disciplinaDAO;



    public DisciplinaService(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    public List<Disciplina> createAll(List<Disciplina> list){
        return disciplinaDAO.saveAll(list);
    }

    public List<Disciplina> findAll() {
        return disciplinaDAO.findAll();
    }

    public Disciplina findById(long id) {
        return disciplinaDAO.findById(id);
    }

    public List<Disciplina> searchForString (String substring){
        List<Disciplina> search = disciplinaDAO.findAll();
        List<Disciplina> result = new ArrayList<Disciplina>();

        if(search.isEmpty()){
            throw new InternalError("Não há disciplinas cadastradas!");
        }

        for ( Disciplina d : search ) {
            String name = d.getNome();
            if(name.contains(substring)){
                result.add(d);
            }
        }
        return result;
    }


}
