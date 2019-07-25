package psoft.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import psoft.backend.dao.UserDAO;
import psoft.backend.exception.user.*;
import psoft.backend.model.TokenFilter;
import psoft.backend.model.User;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Properties;

@Service
public class UserService {

    private final UserDAO userDAO;
    private TokenFilter tokenFilter;

    private final JavaMailSenderImpl javaMailSender;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.javaMailSender = new JavaMailSenderImpl();
        this.tokenFilter = new TokenFilter();
    }


    public User create(User user) throws UserExistsException {
        if (!user.validarEmail()) throw new UserEmailInvalidoException("Insira um e-mail válido");
        user.setEmail(user.getEmail().toLowerCase());
        if (user.getPrimeiroNome() == null) throw new UserNullException("O primeiro nome não pode ser Null");
        if (user.getPrimeiroNome().trim().equals("")) throw new UserInvalidoException("O primeiro nome não pode ser vazio, insira um nome valido");
        if(user.getSenha().length() < 8 || user.getSenha().length() > 20) throw new UserInvalidoException("Deve inserir uma senha válida. Uma senha válida possui ente 8 e 20 caracteres");

        User userVerify = userDAO.findByEmail(user.getEmail());
        if (!(userVerify == null)) {
            throw new UserExistsException("Email já Cadastrado");
        }
        
        //try{
        //enviaEmail(user);
        //}catch (Exception ignored){
        //}

        return userDAO.save(user);
    }

    public User findByEmail(String userLogin) {
        User result = userDAO.findByEmail(userLogin);
        if (result == null) {
            throw new UserNotFoundException("Usuário não encontrado!");
        }
        return result;
    }

    public List<User> findAll() {
        List<User> users = userDAO.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Não existe usuarios");
        }
        return users;
    }

    public void deleteAll() {
        userDAO.deleteAll();
    }

    public String getLogin(String auth) throws ServletException {
        return tokenFilter.getLogin(auth);
    }

    // função criada com a finalidade de apresentar uma função adicional do backend. 
    //após a apresentação o mesmo o email foi excluido
    private void enviaEmail(User user){
        Properties props = new Properties();

        //Parâmetros de conexão com servidor Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        javaMailSender.setUsername("api.ucdb@gmail.com");
        javaMailSender.setPassword("eduardo_matheusUCDb");
        javaMailSender.setJavaMailProperties(props);

        //criando email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Seja Bem Vindo!!!");
        message.setText("Olá, " + user.getPrimeiroNome() + System.lineSeparator()
                + System.lineSeparator() + "Estamos muito felizes por se cadastrar na nossa plataforma colaborativa para avaliações" +
                " e informações sobre disciplinas de cursos da UFCG. Acesse o nosso site pelo link:" + System.lineSeparator()
                + System.lineSeparator() + "http://ucdb-client.herokuapp.com"+ System.lineSeparator() + System.lineSeparator() +
                "Equipe UCDb.");
        message.setTo(user.getEmail());
        message.setFrom("UCDb <api.ucdb@gmail.com>");

        javaMailSender.send(message);
    }
}
