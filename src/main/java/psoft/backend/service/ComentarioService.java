package psoft.backend.service;

import org.springframework.stereotype.Service;
import psoft.backend.dao.ComentarioDAO;
import psoft.backend.exception.comentario.ComentarioInvalidoException;
import psoft.backend.exception.comentario.ComentarioNullException;
import psoft.backend.model.Comentario;

import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioDAO comentarioDAO;

    public ComentarioService(ComentarioDAO comentarioDAO) {
        this.comentarioDAO = comentarioDAO;
    }

    public Comentario create(Comentario comentario) {
        if (comentario.getUser() == null) throw new ComentarioNullException("O usuário não pode ser Null");
        if (comentario.getPerfil() == null) throw new ComentarioNullException("O perfil não pode ser Null");

        return comentarioDAO.save(comentario);
    }

    public Comentario findById (long id){
        return comentarioDAO.findById(id);
    }

    public List<Comentario> findAll() {
        return comentarioDAO.findAll();
    }
}
