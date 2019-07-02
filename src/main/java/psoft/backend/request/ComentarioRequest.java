package psoft.backend.request;

public class ComentarioRequest {

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
