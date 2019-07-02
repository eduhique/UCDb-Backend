package psoft.backend.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRequest {

    private String email;
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
