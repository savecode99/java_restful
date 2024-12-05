package com.vung.restful.Config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vung.restful.domain.RestResponse;
import com.vung.restful.util.CustomException.EmailExistException;
import com.vung.restful.util.CustomException.IdInvalidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IdInvalidException.class)
     public ResponseEntity<RestResponse<Object>> handleIdException(IdInvalidException idException) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        restResponse.setError(idException.getMessage());
        restResponse.setMessage("ID invalid");

        //return ResponseEntity.badRequest().body(idException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }

    @ExceptionHandler(value = EmailExistException.class)
    public ResponseEntity<RestResponse<Object>> handleIdException(EmailExistException ex) {
       RestResponse<Object> restResponse = new RestResponse<Object>();
       restResponse.setStatusCode(HttpStatus.CONFLICT.value());
       restResponse.setError(ex.getMessage());
       restResponse.setMessage("Choose other email");

       //return ResponseEntity.badRequest().body(idException.getMessage());
       return ResponseEntity.status(HttpStatus.CONFLICT).body(restResponse);
   }


   

    @ExceptionHandler(value = MethodArgumentNotValidException.class )
    public ResponseEntity<RestResponse<Object>> handleIdException(MethodArgumentNotValidException ex) {
        
        BindingResult rs = ex.getBindingResult();
        List<FieldError> FieldErrors = rs.getFieldErrors();
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        restResponse.setError(ex.getBody().getDetail());
        List<String> errors = new ArrayList<>();
        for( FieldError FieldError : FieldErrors)
        {
            errors.add(FieldError.getDefaultMessage());
        }
        restResponse.setMessage(errors.size() > 1 ? errors : errors.get(0));
        //return ResponseEntity.badRequest().body(idException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }

    @ExceptionHandler(value = {BadCredentialsException.class , UsernameNotFoundException.class})
    public ResponseEntity<RestResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        restResponse.setError(ex.getMessage());
        restResponse.setMessage("Sai username hoặc password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restResponse);
    }
    


}
