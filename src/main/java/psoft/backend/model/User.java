package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Entity
@Table(name="users")
public class User {

    private String primeiroNome;
    private String ultimoNome;

    @Id
    private String email;
    private String senha;

    @OneToMany
    @JsonBackReference(value = "perfil")
    private List<User> curtidas;


    public User() {
    }

    public User(String primeiroNome, String ultimoNome, String email, String senha, List<User> curtidas) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
        this.senha = senha;
        this.curtidas = curtidas;
    }

    public boolean validarEmail() {
        boolean isEmailIdValid = false;
        if (this.email != null && this.email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(this.email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }


}
