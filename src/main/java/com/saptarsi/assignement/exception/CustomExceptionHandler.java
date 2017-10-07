package com.saptarsi.assignement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.saptarsi.assignement.errorcodes.ErrorCodes;
import com.saptarsi.assignement.model.response.GenericErrorResponse;
import com.saptarsi.assignement.model.response.ModelAPIResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${com.hotstar.logSeparator}")
    private String logSeparator;
    private final static Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ModelAPIResponse> umspExceptionHandler(Throwable ex) {

        String stackTrace = null;
        for (StackTraceElement t : ex.getStackTrace()) {
            stackTrace = stackTrace + t.toString() + logSeparator;
        }

        // final String sstackTrace = stackTrace;
        if (ex instanceof MethodArgumentNotValidException) {

            ModelAPIResponse error = new GenericErrorResponse("Bad Request : " + ex.getMessage(),
                    ErrorCodes.REQUEST_INVALID_EXCEPTION);
            log.error(error.toString());
            return new ResponseEntity<ModelAPIResponse>(error, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof RequestInvalidException) {

            ModelAPIResponse error = new GenericErrorResponse("Bad Request : " + ex.getMessage(),
                    ErrorCodes.REQUEST_INVALID_EXCEPTION);
            log.error(error.toString());
            return new ResponseEntity<ModelAPIResponse>(error, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof CustomAsyncException) {
            ModelAPIResponse error = new GenericErrorResponse("Something Went Wrong! Please try after sometime!",
                    ErrorCodes.INTERNAL_SERVER_ERROR);
            log.error(error.toString());
            return new ResponseEntity<ModelAPIResponse>(error, HttpStatus.INSUFFICIENT_STORAGE);
        } else if (ex instanceof CustomRuntimeException) {
            ModelAPIResponse error = new GenericErrorResponse(
                    "Bad Request or Request not Processible : " + ex.getMessage(), ErrorCodes.UNHANDLED_EXCEPTION);
            log.error(error.toString());
            return new ResponseEntity<ModelAPIResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            ModelAPIResponse response = new GenericErrorResponse("Unhandled Exceptions",
                    ErrorCodes.UNHANDLED_EXCEPTION);
            log.error(ex.getMessage());
            return new ResponseEntity<ModelAPIResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
