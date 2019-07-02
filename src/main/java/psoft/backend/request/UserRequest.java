package psoft.backend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApiModel(value = "Usuário Request", description = "Modelo personalizado para fazer o request de um usuário do sistema")
public class UserRequest {

    @ApiModelProperty(value = "e-mail do usuário. Este atributo é um identificador único de usuario.", example = "eduardo.henrique.silva@ccc.ufcg.edu.br", position = 0)
    private String email;

    @ApiModelProperty(value = "senha do usuário. A senha deve conter entre 8 e 15 caracteres,", example = "teste123", position = 1)
    private String senha;

    public UserRequest() {
    }

    public UserRequest(String email, String senha) {
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

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
