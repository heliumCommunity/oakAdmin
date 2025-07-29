package com.helium.oakcollectionsadmin.exceptions;


    public class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super("Invalid Login credentials");
        }
    }


