/**
 * Author: Amit Kumar
 * amitkumar647
 * 6:23:16 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.exception;

/**
 * @author amitkumar647
 *
 */
import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
         super();
         this.timestamp = timestamp;
         this.message = message;
         this.details = details;
    }

    public Date getTimestamp() {
         return timestamp;
    }

    public String getMessage() {
         return message;
    }

    public String getDetails() {
         return details;
    }
}
