package com.peramdy.controller.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peramdy
 */

@RestController
@RequestMapping("/ex")
public class ExController {

    private static Logger logger = LoggerFactory.getLogger(ExController.class);

    @GetMapping("/throwEx")
    public String throwException() throws Exception {
        throw new Exception();
    }


    @ExceptionHandler(Exception.class)
    public String explainException(Exception e) {
        logger.error(e.getLocalizedMessage());
        return "500";
    }


}
