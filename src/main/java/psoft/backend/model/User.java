package psoft.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApiModel(value = "Usuário", description = "Modelo de um usuário do sistema")
@Data
@Entity
@Table(name="users")
public class User {

    @ApiModelProperty(value = "Primeiro nome do usuário.", example = "Eduardo", position = 0)
    private String primeiroNome;
    @ApiModelProperty(value = "Último nome do usuário.", example = "Pontes", position = 1)
    private String ultimoNome;

    @ApiModelProperty(value = "e-mail do usuário. Este atributo é um identificador único de usuario.", example = "eduardo.henrique.silva@ccc.ufcg.edu.br", position = 2)
    @Id
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "senha do usuário. A senha deve conter entre 8 e 15 caracteres,", example = "teste123", position = 3)
    private String senha;

    @OneToMany
    @JsonBackReference(value = "perfil")
    @ApiModelProperty(value = "lista de curtidas que o usuário fez nos perfis das disciplinas.", hidden = true)
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
