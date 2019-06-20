package psoft.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import psoft.backend.model.Comentario;

import java.io.Serializable;
import java.util.List;

public interface ComentarioDAO<T, ID extends Serializable> extends JpaRepository<Comentario, Long> {

    Comentario save(Comentario comentario);

    Comentario findById(long id);

    List<Comentario> findAll();

}
