package br.com.vr.avaliacaointermediario.controller.exceptionHandler;

public class CartaoFormErrorDto {

    private String field;
    private String error;

    public CartaoFormErrorDto(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
    
}
