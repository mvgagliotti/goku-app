package com.goku.gokubackend.application.jwt;

public class JwtParseException extends RuntimeException {

    JwtParseException(Throwable cause) {
        super(cause);
    }

    public JwtParseException(String message) {
        super(message);
    }

    public String getRealCauseMessage() {
        return this.getCause() == null ? this.getMessage() : this.getCause().getMessage();
    }
}
