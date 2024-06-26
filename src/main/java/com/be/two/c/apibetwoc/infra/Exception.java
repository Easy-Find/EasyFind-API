package com.be.two.c.apibetwoc.infra;

import com.be.two.c.apibetwoc.infra.security.ErrosValidacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class Exception {
    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail produtoNaoEncontrado(NoSuchElementException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrosValidacao>> tratarErrosValidacao(MethodArgumentNotValidException ex){
        List<FieldError> erros =ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(ErrosValidacao::new).toList());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Void> tratarErroImagem(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EntidadeNaoExisteException.class)
    public ResponseEntity<String> entidadeNotFound(EntidadeNaoExisteException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
