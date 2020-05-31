package com.minseon.restApi.handler;


import com.minseon.restApi.dto.RestApiResponse;
import com.minseon.restApi.exception.InvalidFileDataException;
import com.sun.media.sound.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApiResponse<String>> handleAllExceptions(Exception ex, WebRequest request) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestApiResponse<String>> handleValidationException(Exception ex, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<RestApiResponse<String>> handleInvalidDataException(Exception ex, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<RestApiResponse<String>> handleEmptyFileException(Exception ex, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<RestApiResponse<String>> handleInvalidFormatFileException(Exception ex, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidFileDataException.class)
    public ResponseEntity<RestApiResponse<String>> handleInvalidFileDataException(Exception ex, WebRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<RestApiResponse<String>> errorResponse(HttpStatus httpStatus, String msg) {
        RestApiResponse<String> restApiResponse = new RestApiResponse<>(httpStatus.value(), msg, null);
        return new ResponseEntity<>(restApiResponse, httpStatus);
    }

//    @ExceptionHandler(QuizNotFoundException.class)
//    public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex,WebRequest request){
//        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
//        return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);
//        }
//    }
}