package br.com.vr.avaliacaointermediario.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.vr.avaliacaointermediario.exceptions.CartaoNaoExisteException;
import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaCartaoNaoExisteException;
import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaSaldoInsuficiente;
import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaSenhaInvalidaException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartaoNaoExisteException.class)
    public ResponseEntity<?> handleCatNotFound(CartaoNaoExisteException e, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
    }
    
    @ExceptionHandler(TransacaoNegadaCartaoNaoExisteException.class)
    public ResponseEntity<?> handleCatNotFound(TransacaoNegadaCartaoNaoExisteException e, WebRequest request) {
        return ResponseEntity.status(422).body("CARTAO_INEXISTENTE");
    }
    
    @ExceptionHandler(TransacaoNegadaSenhaInvalidaException.class)
    public ResponseEntity<?> handleCatNotFound(TransacaoNegadaSenhaInvalidaException e, WebRequest request) {
        return ResponseEntity.status(422).body("SENHA_INVALIDA");
    }
    
    @ExceptionHandler(TransacaoNegadaSaldoInsuficiente.class)
    public ResponseEntity<?> handleCatNotFound(TransacaoNegadaSaldoInsuficiente e, WebRequest request) {
        return ResponseEntity.status(422).body("SALDO_INSUFICIENTE");
    }

}
