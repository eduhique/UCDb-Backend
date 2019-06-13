package psoft.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Entity
public class User {

    private String primeiroNome;
    private String ultimoNome;

    @Id
    private String email;
    private String senha;

    public User() {
    }

    public User(String primeiroNome, String ultimoNome, String email, String senha) {

        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
        this.senha = senha;
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
