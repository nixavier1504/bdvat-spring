/**
 * Author: Amit Kumar
 * amitkumar647
 * 6:22:06 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.exception;

/**
 * @author amitkumar647
 *
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message){
        super(message);
    }
}