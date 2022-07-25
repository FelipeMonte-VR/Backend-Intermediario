package br.com.vr.avaliacaointermediario.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.vr.avaliacaointermediario.controller.exceptionHandler.CartaoFormErrorDto;

@RestControllerAdvice
public class ValidationErrorHandler {
    
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<CartaoFormErrorDto> handle(MethodArgumentNotValidException exception) {

        List<CartaoFormErrorDto> errorsDto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach((e) -> {
            String field = e.getField();
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());

            CartaoFormErrorDto error = new CartaoFormErrorDto(field, message);

            errorsDto.add(error);
        });

        return errorsDto;
    }

}
