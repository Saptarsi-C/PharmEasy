/**
 * 
 */
package com.saptarsi.assignement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author saptarsichaurashy
 *
 */
@ControllerAdvice
@RestController
public class CustomRuntimeExceptionHandler {
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    @ExceptionHandler(value = CustomRuntimeException.class)  
    public String handleBaseException(CustomRuntimeException e){  
        return e.getMessage(); 
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = RequestInvalidException.class)
    public String handleRequestException(RequestInvalidException e){  
        return e.getMessage(); 
    }
}
