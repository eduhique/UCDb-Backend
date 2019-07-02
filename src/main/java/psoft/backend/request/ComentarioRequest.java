package psoft.backend.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Comentário Request", description = "Modelo Pernalizado de um Comentário para receber uma request." +
        " Este modelo possui os atributos nécessarios para a request.")
public class ComentarioRequest {

    @ApiModelProperty(value = "Texto do comentário Request", example = "Essa disciplina é muito boa. Ótimos professores!!!")
    private String text;

    public ComentarioRequest() {
    }

    public ComentarioRequest(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }
}
