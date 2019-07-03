package psoft.backend.service;

import org.springframework.stereotype.Service;
import psoft.backend.dao.DisciplinaDAO;
import psoft.backend.exception.disciplina.DisciplinaNotFoundException;
import psoft.backend.model.Disciplina;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaDAO disciplinaDAO;


    public DisciplinaService(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    public List<Disciplina> createAll(List<Disciplina> list) {
        List result = disciplinaDAO.saveAll(list);
        if (result.isEmpty()) {
            throw new InternalError("Algo deu errado!!");
        }
        return result;
    }

    public List<Disciplina> findAll() {
        List disciplinas = disciplinaDAO.findAll();
        if (disciplinas.isEmpty()) {
            throw new DisciplinaNotFoundException("Não há disciplinas cadastradas");
        }
        return disciplinas;
    }

    public Disciplina findById(long id) {
        Disciplina dis = disciplinaDAO.findById(id);
        if (dis == null) {
            throw new DisciplinaNotFoundException("Disciplina não encontrada!");
        }
        return dis;
    }

    public List<Disciplina> searchForString(String substring) {
        List<Disciplina> search = disciplinaDAO.findAll();
        List<Disciplina> result = new ArrayList<Disciplina>();
        String substringSemAcento = Normalizer.normalize(substring, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        if (search.isEmpty()) {
            throw new InternalError("Não há disciplinas cadastradas!");
        }
        for (Disciplina d : search) {
            String name = Normalizer.normalize(d.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            if (name.contains(substringSemAcento)) {
                result.add(d);
            }
        }
        if (result.isEmpty()) {
            throw new DisciplinaNotFoundException("Disciplina não encontrada!");
        }
        return result;
    }
}
