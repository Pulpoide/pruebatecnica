package com.example.demo.exceptions;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.dto.ErrorResponseDTO;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorDTO error = ErrorDTO.builder().timestamp(new Date())
        .status(status.value()).path(request.getRequestURI())
        .message(ex.getMessage()).build();
        ErrorResponseDTO response = ErrorResponseDTO.builder().error(error).build();

        return ResponseEntity.status(status).body(response);
    }

    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorDTO error = ErrorDTO.builder().timestamp(new Date())
        .status(status.value()).path(request.getRequestURI())
        .message(ex.getMessage()).build();
        ErrorResponseDTO response = ErrorResponseDTO.builder().error(error).build();

        return ResponseEntity.status(status).body(response);
    }




}
