package psoft.backend.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.backend.model.Comentario;
import psoft.backend.model.Comparators.ComparaComentario;
import psoft.backend.model.Comparators.ComparaLike;
import psoft.backend.model.Disciplina;
import psoft.backend.model.Perfil;
import psoft.backend.model.User;
import psoft.backend.request.ComentarioRequest;
import psoft.backend.service.ComentarioService;
import psoft.backend.service.DisciplinaService;
import psoft.backend.service.PerfilService;
import psoft.backend.service.UserService;

import javax.servlet.ServletException;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping({"/v1/perfil"})
public class PerfilController {

    private final DisciplinaService disciplinaService;

    private final PerfilService perfilService;
    private final UserService userService;
    private final ComentarioService comentarioService;
    private Comparator<Perfil> comparador;

    public PerfilController(DisciplinaService disciplinaService, PerfilService perfilService, UserService userService, ComentarioService comentarioService) {
        this.disciplinaService = disciplinaService;
        this.perfilService = perfilService;
        this.userService = userService;
        this.comentarioService = comentarioService;
    }

    @ApiOperation(value = "Cria Disciplinas e Perfis", notes = "Essa Operação cria diciplinas e perfis a partir um" +
            " lista de diciplinas passada. Para criar uma disciplica é nescessário passar um apenas o parametro 'nome'" +
            " do objeto", response = Perfil.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Retorna uma lista com as Perfil criadas",
                    response = Perfil.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar uma Exception"
            )
    })
    @PostMapping(value = "/disciplina") // /create/all
    @ResponseBody
    public ResponseEntity<List<Perfil>> createAll(@ApiParam(value = "Lista de disciplinas contendo apenas o nome." +
            " Para acesso a essa rota precisa estar autenticado.") @RequestBody List<Disciplina> disciplina) {
        List<Disciplina> disciplinas = disciplinaService.createAll(disciplina);
        List<Perfil> perfil = perfilService.createPerfilAll(disciplinas);
        return new ResponseEntity<List<Perfil>>(perfil, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Buscar uma Disciplina pelo Id", notes = "Essa Operação da acesso uma disciplina através" +
            " do seu id. Para acesso a essa rota não precisa estar autenticado.", response = Disciplina.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma Disciplina",
                    response = Disciplina.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            )

    })
    @GetMapping(value = "/disciplina/{id}") // /disciplina/{id}
    @ResponseBody
    public ResponseEntity<Disciplina> buscaDisciplina(@ApiParam(value = "Identificador numérico único de uma disciplina.") @PathVariable long id) {
        Disciplina dis = disciplinaService.findById(id);
        return new ResponseEntity<Disciplina>(dis, HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar uma Disciplina a partir de uma substring", notes = "Essa Operação pesquisa uma" +
            " disciplina a partir de uma substring. Para acesso a essa rota não precisa estar autenticado.", response = Disciplina.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma Disciplina",
                    response = Disciplina.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            )
    })
    @GetMapping(value = "/disciplina/search") // /disciplina/search
    public ResponseEntity<List<Disciplina>> buscaPorSubString(@ApiParam("Nome ou parte do nome de uma disciplina.") @RequestParam(name = "substring") String substring) {
        List<Disciplina> searchForString = disciplinaService.searchForString(substring.toUpperCase());
        return new ResponseEntity<List<Disciplina>>(searchForString, HttpStatus.OK);
    }

    @ApiOperation(value = "Listar todas as disciplinas.", notes = "Esssa operção lista todas as disciplinas cadastradas" +
            " no banco de dados. Para acesso a essa rota não precisa estar autenticado.", response = Disciplina.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma lista com todas as Disciplinas",
                    response = Disciplina.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            )
    })
    @GetMapping(value = "/disciplina/all")
    public ResponseEntity<List<Disciplina>> listaTudo() {
        List<Disciplina> disciplinas = disciplinaService.findAll();
        return new ResponseEntity<List<Disciplina>>(disciplinas, HttpStatus.OK);
    }

    //perfil

    @ApiOperation(value = "Buscar um Perfil a partir id da Disciplina", notes = "Essa Operação dá acesso a um perfil" +
            " através do id da Disciplina. Para acesso a essa rota precisa estar autenticado.",
            response = Perfil.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna um Perfil",
                    response = Perfil.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com a Exception"
            )
    })
    @GetMapping(value = "/disciplina/id")  // "/"
    @ResponseBody
    public ResponseEntity<Perfil> buscaPerfilPorDisciplina(@ApiParam(value = "Identificador numérico único de uma disciplina.") @RequestParam(name = "disciplina-id") long id,
                                                           @ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header") @RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        Perfil perfil = perfilService.findByDisciplinaId(id, user);
        return new ResponseEntity<Perfil>(perfil, HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar um Perfil", notes = "Essa Operação dá acesso a um perfil pelo seu id. Para acesso a" +
            " essa rota precisa estar autenticado. Para acesso a essa rota precisa estar autenticado.",
            response = Perfil.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna um Perfil",
                    response = Perfil.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            )

    })
    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Perfil> buscaPerfil(@ApiParam(value = "Identificador numérico único de um perfil.") @RequestParam(name = "perfil-id") long id,
                                              @ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header") @RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        Perfil perfil = perfilService.findById(id, user);
        return new ResponseEntity<Perfil>(perfil, HttpStatus.OK);
    }

    @ApiOperation(value = "Listar todos os perfis.", notes = "Essa Operação dá acesso a uma lista com todos os perfis" +
            " cadastrados no banco de dados. Para acesso a essa rota precisa estar autenticado." +
            " de dados.", response = Perfil.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma lista com todos os perfis.",
                    response = Perfil.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            )

    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<Perfil>> listaPerfilAll(@ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header") @RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        List<Perfil> perfil = perfilService.findAll(user);
        return new ResponseEntity<List<Perfil>>(perfil, HttpStatus.OK);
    }

    // Comentario

    @ApiOperation(value = "Criar um comentário", notes = "Essa Operação cria um comentario que pode ser ou um" +
            " comentário a um perfil ou uma resposta a um comentário. Para acesso a essa rota precisa estar autenticado", response = Comentario.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Retorna o comentário criado.",
                    response = Comentario.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Retorna uma mensagem de erro com uma Exception."
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            )

    })
    @PostMapping(value = "/comentario") // /comentario/
    @ResponseBody
    public ResponseEntity<Comentario> createComentario(@ApiParam(value = "Identificador numérico único de um perfil.") @RequestParam(name = "perfil-id") long perfilId,
                                                       @ApiParam(value = "Idendificador numérico unico de um comentário.", defaultValue = "0")@RequestParam(name = "comentario-id", defaultValue = "0") long comentarioId,
                                                       @ApiParam(value = "Modelo de uma request de um comentário. Este de JSON tem apenas 'text'.")@RequestBody ComentarioRequest comentario,
                                                       @ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header.") @RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        Perfil perfil = perfilService.findById(perfilId, user);
        Comentario novoComentario = comentarioService.create(perfil, user, comentario.getText(), comentarioId);
        return new ResponseEntity<Comentario>(novoComentario, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Listar todos os comentários", notes = "Essa Operação dá acesso a uma lista com todos os" +
            " comentários. Para acesso a essa rota precisa estar autenticado.", response = Comentario.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma lista de comentários",
                    response = Comentario.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com a Exception"
            )

    })
    @GetMapping(value = "/comentario/all")
    public ResponseEntity<List<Comentario>> comentarioALL() {
        List<Comentario> comentarios = comentarioService.findAll();
        return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar um comentário", notes = "Essa Operação deleta um comentário. Para acesso a essa rota precisa estar autenticado.", response = Comentario.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna um comentário atualizado",
                    response = Comentario.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com a Exception"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Retorna uma mensagem de erro com uma Exception."
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar uma Exception."
            )

    })
    @DeleteMapping(value = "/comentario") // /comentario/delete
    public ResponseEntity<Comentario> ComentarioDelete(@ApiParam(value = "Identificador numérico único de um comentário.")@RequestParam(name = "comentario-id") long comentarioId,
                                                       @ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header") @RequestHeader("Authorization") String token) throws ServletException {
        Comentario comentario = comentarioService.deleteUpdate(comentarioId, userService.getLogin(token));
        return new ResponseEntity<Comentario>(comentario, HttpStatus.OK);
    }

    @ApiOperation(value = "Curtir um Perfil", notes = "Essa operação possibilita dar ou retirar like de um perfil. Para acesso a essa rota precisa estar autenticado.", response = Perfil.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna o perfil atualizado.",
                    response = Perfil.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar uma Exception."
            )
    })
    @PutMapping(value = "/like")
    public ResponseEntity<Perfil> curtirPerfil(@ApiParam(value = "Identificador numérico único de um perfil.")@RequestParam(name = "perfil-id") long id,
                                               @ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header") @RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        Perfil perfil = perfilService.curtirPerfil(id, user);
        return new ResponseEntity<Perfil>(perfil, HttpStatus.OK);
    }

    // ranking

    @ApiOperation(value = "Ranking das disciplinas(por número de likes) ", notes = "Essa operação possibilita visulizar" +
            " o ranking das disciplinas de ordenados de acordo com a quantidade de likes. Para acesso a essa rota precisa estar autenticado.", response = Perfil.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma lista de perfis de disciplinas ordenando",
                    response = Perfil.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar uma Exception."
            )
    })
    @GetMapping(value = "/ranking/like")
    public ResponseEntity<List<Perfil>> rankigLike(@ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header") @RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        List<Perfil> perfil = perfilService.findAll(user);
        comparador = new ComparaLike();
        perfil.sort(comparador);
        return new ResponseEntity<List<Perfil>>(perfil, HttpStatus.OK);
    }

    @ApiOperation(value = "Ranking das disciplinas(por número de comentários) ", notes = "Essa operação possibilita visulizar" +
            " o ranking das disciplinas de ordenados de acordo com a quantidade geral(comentário + respostas a" +
            " comentários) de cometários. Para acesso a essa rota precisa estar autenticado.", response = Perfil.class)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorna uma lista de perfis de disciplinas ordenando",
                    response = Perfil.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "Retorna uma mensagem de erro com uma Exception"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Caso tenhamos algum erro vamos retornar uma Exception."
            )
    })
    @GetMapping(value = "/ranking/comentario")
    public ResponseEntity<List<Perfil>> rankigComentario(@ApiParam(hidden = true, value = "token do usuário. Não precisa se passado, pois será retirado do header") @RequestHeader("Authorization") String token) throws ServletException {
        User user = userService.findByEmail(userService.getLogin(token));
        List<Perfil> perfil = perfilService.findAll(user);
        comparador = new ComparaComentario();
        perfil.sort(comparador);
        return new ResponseEntity<List<Perfil>>(perfil, HttpStatus.OK);
    }

// lixo


    //    @ApiOperation(value = "Criar um comentário", notes = "Essa Operação cria um comentario a partir do id do perfil.", response = Comentario.class, position = 4)
//    @ApiResponses(value = {
//            @ApiResponse(
//                    code = 201,
//                    message = "Retorna o comentário criado.",
//                    response = Comentario.class
//            ),
//            @ApiResponse(
//                    code = 400,
//                    message = "Retorna uma mensagem de erro com uma Exception."
//            ),
//            @ApiResponse(
//                    code = 404,
//                    message = "Retorna uma mensagem de erro com uma Exception"
//            )
//
//    })
//    @PostMapping(value = "/comentario") // /comentario/
//    @ResponseBody
//    public ResponseEntity<Comentario> createComentario(@RequestParam(name = "perfil-id") long id, @RequestHeader("Authorization") String token, @RequestBody Comentario comentario) throws ServletException {
//        User user = userService.findByEmail(userService.getLogin(token));
//        Perfil perfil = perfilService.findById(id, user);
//        if (comentario == null) {
//            throw new ComentarioNullException("Comentário não é valido");
//        }
//        if (comentario.getText() == null) throw new ComentarioNullException("O comentário não pode ser Null");
//        if (comentario.getText().trim().equals(""))
//            throw new ComentarioInvalidoException("O comentário não pode ser vazio, insira um comentário valido");
//        String hora = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("HH:mm"));
//        String data = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        Comentario preComentario = new Comentario(perfil, user, comentario.getText(), hora, data, new ArrayList<Comentario>());
//        perfil.addComentario(preComentario);
//        Comentario novoComentario = comentarioService.create(preComentario);
//        return new ResponseEntity<Comentario>(novoComentario, HttpStatus.CREATED);
//    }
//
//    @ApiOperation(value = "Criar Resposta para um Comentário", notes = "Essa Operação cria uma resposta para um" +
//            " comentário a parti do id do comentário a ser respondido", response = Comentario.class)
//    @ApiResponses(value = {
//            @ApiResponse(
//                    code = 201,
//                    message = "Retorna o comentário(resposta) criado.",
//                    response = Comentario.class
//            ),
//            @ApiResponse(
//                    code = 400,
//                    message = "Retorna uma mensagem de erro com uma Exception."
//            ),
//            @ApiResponse(
//                    code = 404,
//                    message = "Retorna uma mensagem de erro com uma Exception"
//            )
//
//    })
//    @PostMapping(value = "/comentario/resposta")
//                    @ResponseBody
//                    public ResponseEntity<Comentario> createResposta(@RequestParam(name = "comentario-id") long comentarioId, @RequestHeader("Authorization") String token, @RequestBody Comentario resposta) throws ServletException {
//            Comentario comentario = comentarioService.findById(comentarioId);
//            User user = userService.findByEmail(userService.getLogin(token));
//            if (resposta == null) {
//            throw new ComentarioNullException("Comentário não é valido");
//            }
//            if (resposta.getText() == null) throw new ComentarioNullException("O comentário não pode ser Null");
//            if (resposta.getText().trim().equals(""))
//            throw new ComentarioInvalidoException("O comentário não pode ser vazio, insira um comentário valido");
//
//            Perfil perfil = comentario.getPerfil();
//            String hora = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("HH:mm"));
//            String data = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//            Comentario preComentario = new Comentario(perfil, user, resposta.getText(), hora, data, new ArrayList<Comentario>());
//            comentario.addResposta(preComentario);
//            Comentario novoComentario = comentarioService.create(preComentario);
//            return new ResponseEntity<Comentario>(novoComentario, HttpStatus.CREATED);
//            }
}
