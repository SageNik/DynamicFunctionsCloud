package com.chisw.dynamicFunctions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Allow the web to return a 404 if an node is not found by simply
 * throwing this exception. The @ResponseStatus causes Spring MVC to return a
 * 404 instead of the usual 500.
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FunctionNotFoundException extends Exception {

        private static final long serialVersionUID = 1L;

        public FunctionNotFoundException(String functionName) {
            super("There is no function with name = " + functionName);
        }
}
