package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERRO_GENERICO_SISTEMA = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException invalidFormatException)
            return handleInvalidFormatException(invalidFormatException, headers, status, request);
        else if (rootCause instanceof PropertyBindingException propertyBindingException)
            return handleIgnoredPropertyException(propertyBindingException, headers, status, request);

        Problem problem = createProblemBuilder(
                status.value(),
                ProblemType.MENSAGEM_IMCOMPREENSIVEL,
                "Corpo da requisição inválido. Verifique erro de sintaxe")
                .userMessage(ERRO_GENERICO_SISTEMA).
                build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Problem problem = createProblemBuilder(
                status.value(),
                ProblemType.RECURSO_NAO_ENCONTRADO,
                String.format("O recurso '%s' que você tentou acessar é inexistente", ex.getRequestURL()))
                .userMessage(ERRO_GENERICO_SISTEMA)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        Problem problem = createProblemBuilder(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ProblemType.ERRO_DE_SISTEMA,
                ERRO_GENERICO_SISTEMA)
                .userMessage(ERRO_GENERICO_SISTEMA)
                .build();

        ex.printStackTrace();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private ResponseEntity<Object> handleIgnoredPropertyException(PropertyBindingException rootCause, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = joinPath(rootCause.getPath());
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problem problem = createProblemBuilder(
                HttpStatus.BAD_REQUEST.value(),
                ProblemType.PROPRIEDADE_IGNORADA,
                detail)
                .userMessage(ERRO_GENERICO_SISTEMA)
                .build();

        return handleExceptionInternal(rootCause, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private static String joinPath(List<JsonMappingException.Reference> rootCause) {
        return rootCause.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException rootCause, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = joinPath(rootCause.getPath());
        String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é um tipo inválido." +
                "Corrija e informe um tipo compativel com '%s'", path, rootCause.getValue(), rootCause.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(
                status.value(),
                ProblemType.MENSAGEM_IMCOMPREENSIVEL,
                detail)
                .userMessage(ERRO_GENERICO_SISTEMA)
                .build();

        return handleExceptionInternal(rootCause, problem, headers, HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        BindingResult bindingResult = ex.getBindingResult();

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

            String name = objectError.getObjectName();

            if (objectError instanceof FieldError fieldError)
                name = fieldError.getField();

            return Problem.Object.builder()
                    .nome(name)
                    .userMessage(message)
                    .build();
        }).collect(Collectors.toList());

        Problem problem = createProblemBuilder(
                status.value(),
                ProblemType.PARAMETRO_INVALIDO,
                detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException)
            return handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, headers, status, request);

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Parameter parameter = ex.getParameter().getParameter();
        String detail = String.format("O parâmetro de url '%s', recebeu o valor '%s', que é de um tipo inválido." +
                "Corrija e informe um valor compatível com '%s", parameter.getName(), ex.getValue(), parameter.getType().getSimpleName());

        Problem problem = createProblemBuilder(
                status.value(),
                ProblemType.PARAMETRO_INVALIDO,
                detail)
                .userMessage(ERRO_GENERICO_SISTEMA)
                .build();

        return handleExceptionInternal(ex, problem, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        Problem problem = createProblemBuilder(
                HttpStatus.NOT_FOUND.value(),
                ProblemType.RECURSO_NAO_ENCONTRADO,
                ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        Problem problem = createProblemBuilder(
                HttpStatus.CONFLICT.value(),
                ProblemType.ENTIDADE_EM_USO,
                ex.getMessage())
                .userMessage(ERRO_GENERICO_SISTEMA)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
        Problem problem = createProblemBuilder(
                HttpStatus.BAD_REQUEST.value(),
                ProblemType.ERRO_NEGOCIO,
                ex.getMessage())
                .userMessage(ERRO_GENERICO_SISTEMA)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .title(ex.getMessage())
                    .status(status.value())
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(
            int status,
            ProblemType problemType,
            String detail
    ) {
        return Problem.builder()
                .status(status)
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(detail);
    }
}
