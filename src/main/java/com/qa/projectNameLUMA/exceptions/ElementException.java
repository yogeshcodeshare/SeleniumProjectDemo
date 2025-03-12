package com.qa.projectNameLUMA.exceptions;


public class ElementException extends RuntimeException {
        // constructor for exceptions that are thrown by the Element.
        public ElementException(String message) {
            super(message);
        }

        public ElementException(String message, Throwable cause) {
            super(message, cause);
        }
    }
