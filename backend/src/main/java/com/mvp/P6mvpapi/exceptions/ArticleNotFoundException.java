package com.mvp.P6mvpapi.exceptions;

public class ArticleNotFoundException extends RuntimeException{
    private static final long serialVersionVID =1;

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
