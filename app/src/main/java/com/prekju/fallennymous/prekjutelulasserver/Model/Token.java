package com.prekju.fallennymous.prekjutelulasserver.Model;

/**
 * Created by fallennymous on 14/02/2019.
 */

public class Token {
    private String Token;
    private boolean serverToken;

    public Token(){}

    public Token(String token, boolean serverToken) {
        Token = token;
        this.serverToken = serverToken;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isServerToken() {
        return serverToken;
    }

    public void setServerToken(boolean serverToken) {
        this.serverToken = serverToken;
    }
}
