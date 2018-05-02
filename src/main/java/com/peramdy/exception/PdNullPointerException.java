package com.peramdy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author permady
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "nullPointer")
public class PdNullPointerException extends RuntimeException {

}
