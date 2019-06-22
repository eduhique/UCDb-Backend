package psoft.backend.service;

import org.springframework.stereotype.Service;
import psoft.backend.dao.ComentarioDAO;
import psoft.backend.exception.comentario.ComentarioDeleteException;
import psoft.backend.exception.comentario.ComentarioInvalidoException;
import psoft.backend.exception.comentario.ComentarioNotFoundException;
import psoft.backend.exception.comentario.ComentarioNullException;
import psoft.backend.model.Comentario;
import psoft.backend.model.User;

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

    public Comentario deleteUpdate(long comentarioId, String userMail) {
        Comentario comentario = comentarioDAO.findById(comentarioId);
        if(comentario == null){
            throw new ComentarioNotFoundException("Comentário não encontrado!!");
        }if (!(comentario.getUser().getEmail().equals(userMail))){
            throw new ComentarioDeleteException("Esse comentário não pertece ao usuário passado no token");
        }
        comentario.setApagado(true);
        return comentarioDAO.save(comentario);
    }
}
