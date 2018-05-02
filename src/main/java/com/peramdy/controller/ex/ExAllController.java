package com.peramdy.controller.ex;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * @author peramdy
 */
@ControllerAdvice
public class ExAllController {

    private static Logger logger = LoggerFactory.getLogger(ExAllController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void explainException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        logger.error(e.getMessage(),e);
    }
}
